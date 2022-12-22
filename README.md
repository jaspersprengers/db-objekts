[[_TOC_]]

# Introduction 

## DbObjekts In 100 words
DbObjekts is a Kotlin library to perform queries on a relational databases in application code.
It generates source code that acts as metadata  for all tables, columns and foreign keys. You then use these stateless Kotlin objects to build type-safe and fluent queries, comfortably using 
your IDE's autocomplete, while DbObjekts takes care of the SQL boilerplate and type conversions.

## The executive demo
Before we jump to installation, let's showcase a very concise demonstration to get a feel of what you can achieve with DbObjekts.

We have a simple database of a lending library with five tables
* `author` represents a person, with name, bio, and data of birth.
* `book` has the official isbn as its primary key, a title, year of publication and a reference to its author.
* `item` represents a physical copy of a book. It refers to the `book` table and stores the date of acquisition. There can be zero or more copies of a book title.
* `member` only stores the name of an invdividual member
* `loan` keeps a record of all the books (items) taken out by a member. It refers to `item` and `member` and keeps the date taken out and returned.

* First we need metadata objects for our database:
```kotlin

```

There is much more to fine-tune during the code generator phase, which you will learn in the relevant section. For now you will notice that it has written a bunch of files.  
The next step is to configure the `TransactionManager`. This wraps a `javax.sql.DataSource` and acts as a factory for transactions. You typically only need one instance per database. 

We provide the metadata object for the database (a.k.a. catalog in good JDBC jargon) which was just produced by the code generator. dbo needs this to produce SQL boilerplate specific to our schemas. 


Now we're ready to start working with the full power of auto-complete. Let's add some authors, titles and members. The `mandatoryColumns(..)` call is a convenience method to make sure you don't miss any of the non-null columns in your insert. When the table in question has an auto-generated id, it is returned as a `Long`. We need to store it for later.
```kotlin
val orwell: Long = tr.insert(Author).mandatoryColumns("George Orwell").execute()
val rowling: Long = tr.insert(Author).mandatoryColumns("Joanne (J.K.) Rowling").execute()

// the primary key of the book table is not auto-generated. In this case execute() returns 1.
tr.insert(Book).mandatoryColumns("ISBN-1984", "Nineteen-eighty Four", orwell, LocalDate.of(1948,1,1)).execute()
tr.insert(Book).mandatoryColumns("ISBN-WIGAN", "The Road to Wigan Pier", orwell, LocalDate.of(1940,1,1)).execute()
tr.insert(Book).mandatoryColumns("ISBN-PHILOSOPHER", "Harry Potter and the Philosopher's Stone", rowling, LocalDate.of(1999,1,1)).execute()

val john = tr.insert(Member).mandatoryColumns("John").execute()
val sally = tr.insert(Member).mandatoryColumns("Sally").execute()
```
We forgot to put a bio for George Orwell. Let's do that now. Notice the use of the where clause. Common sql operator symbols (=,<,>,!=) have textual counterparts.
And yes, you can do embedded and/or conditions. More on that later.
```kotlin
  tr.update(Author)
      .bio("(1903-1950) Pseudonym of Eric Blair. Influential writer of novels, essays and journalism.")
      .where(Author.id.eq(orwell))
```

Add some physical copies and loan data
```kotlin
//we have two copies of Harry Potter, one of 1984 and we misplaced the one of the Road to Wigan Pier.
val copy1_1984 = tr.insert(Item).mandatoryColumns("ISBN-1984", LocalDate.of(1980,5,5)).execute()
val copy1_phil = tr.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005,5,5)).execute()
val copy2_phil = tr.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005,5,5)).execute()
//Sally takes out 1984 and Harry Potter. John takes the other Harry Potter copy
tr.insert(Loan).mandatoryColumns(memberId = sally, itemId = copy1_1984, dateLoaned = LocalDate.now()).execute()
tr.insert(Loan).mandatoryColumns(memberId = sally, itemId = copy1_phil, dateLoaned = LocalDate.now()).execute()
tr.insert(Loan).mandatoryColumns(memberId = john, itemId = copy2_phil, dateLoaned = LocalDate.now()).execute()
```

Now we can start querying. Let's get a list of all titles and their author data. This is what a select query in dbo looks like. You will notice that there is no `from` clause. All the information is present in the column references that you provide in the call to `select(..`) and dbo is can figure out the necessary table joins. The terminating `asList()` call returns a list of type-safe tuples that correspond exactly to the number and types of the columns in the `select(..)` call. Notice that `Author.bio` is a nullable column. Hence, the corresponding value in the tuple is `String?`, not `String`. 
```kotlin
val bookAuthors: List<Tuple3<String, String, String?>> = tr.select(Book.title, Author.name, Author.bio).asList()
```
Let's take it up a notch! We're joining all five tables now. Since the tuple results are data classes, you can deconstruct them into the constituent values:
```kotlin
tr.select(Loan.dateLoaned, Item.id, Book.title, Author.name, Member.name).asList()
  .forEach { (dateLoaned, item, book, author, member) ->
    println("Item $item of $book by $author loaned to $member on $dateLoaned") 
  }
 // the type returned is List<Tuple5<LocalDate, Long, String, String, String>>
```
Let's peek under the hood and see what sql was just created. The transaction provides access to a log of queries made during its lifespan. 
```kotlin
println(transaction.transactionExecutionLog().last().sql)
```
Which looks like
```sql
select l.DATE_LOANED,i.ID,b.TITLE,a.NAME,m.NAME 
    from LIBRARY.LOAN l 
        join LIBRARY.ITEM i on l.ITEM_ID = i.ID 
        join LIBRARY.MEMBER m on l.MEMBER_ID = m.ID 
        join LIBRARY.BOOK b on i.ISBN = b.ISBN 
        join LIBRARY.AUTHOR a on b.AUTHOR_ID = a.ID
```

# In-depth

## Rationale
DBObjekts has superficial similarities to ORM frameworks like Hibernate, but there are no stateful entity objects that mimic database rows where changes are carried through transparently.
ORM works fine when the focus is retrieval and manipulation of single data entities. It is far less efficient at batch updates.


# Detailed discussion 

## Installation ##

Since the library is still in beta, you need to build it from source.

### Insert statements ###
Let's start with getting some data into the tables. The `insert(..)` method takes a (generated) `Table` implementation and returns a corresponding `*InsertBuilder` instance.
In the example below these would be `CountryInsertBuilder` and `EmployeeInsertBuilder`.

The insert builders contain setter methods for all columns. In addition it has a handy `mandatoryColumns(..)` shortcut (provided the table has at least one non-nullable column) to make sure you provide all the required values.

```kotlin
  transaction.insert(Country).mandatoryColumns("nl", "Netherlands").execute()
  val petesId: Long = transaction.insert(Employee).mandatoryColumns("Pete", 5020.34, LocalDate.of(1980, 5, 7)).married(true).execute()
```

- The `Country` object has two mandatory columns and no auto-generated key. The `execute()` method returns the value of the JDBC call `PreparedStatement.executeUpdate()`, which should be 1 for a successful insert.
- The `Employee` table has four mandatory columns. The optional `married` property is set in a setter method. The table has a generated primary key, which is returned by the `execute()` method.

### Update statements ###
Update statements have a similar syntax, so let's discuss them now before moving on the more elaborate select statements. The `update(..)` method also takes a table and returns a `*UpdateBuilder` object.

```kotlin
 transaction.update(Employee).salary(4500.30).married(null).where(e.id.eq(12345))
```

- There is no `mandatoryColumns()` method.
- You can provide a null to a setter method if the corresponding database column is nullable: `update(Employee).married(null)`.
- Note that you cannot do the same with `salary`, because that is non-nullable: `.salary(null)` will not compile
- A where-clause is optional. This will update all rows in the table, so watch out.
- You close the statement with an explicit `execute()`.

### Where-clauses ###

Update, select and delete are executed against a range of database rows that satisfy certain criteria. These criteria are expressed in the where-clause.
The possibilities for generated DbObjekts statements are not as flexible as what you can achieve in native SQL, but they are more convenient to use and still cover a lot of common scenarios.

The canonical form of the where clause is `statement.where(column .. operator .. [value, otherColumn] [and|or] ... )` which is quite analogous to normal SQL usage.

```kotlin
 where(Employee.name.eq("Janet"))
 where(Employee.dateOfBirth.gt(LocalDate.of(1980,1,1)))
```     
These are the operators you can use.

- `eq`: is equal to
- `ne`: is not equal to.
- `gt`: is greater than.
- `lt`: is less than.
- `gte`: is greater than or equal.
- `lte`: is less than or equal.
- `within`: is within a range of values.
- `notIn`: is not within a range of values.
- `startsWith`: (for character type only)
- `endsWith`: (for character type only)
- `contains`:  (for character type only)
- `isNull`
- `isNotNull`

You can chain conditions using `and` or `or` and you can even build nested conditions:

```kotlin
  where(e.married.eq(true).or(e.name.eq("John").or(e.name).eq("Bob"))) // all married people, plus John and Bob
```

- If you have no conditions to constrain your selection you omit the where clause in SQL. In DbObjekts you have to close the select/update/delete statement with `noWhereClause()`. Be very careful, especially with deleting!

** Select statements **

Let's move on to Select statements now. The pattern is `transaction.select( col1, col2, ... ).where( conditions ).[first()|asList()]`. You start with listing the columns you want to retrieve, the whereclause (or `noWhereClause`) and then retrieve a list of results

This query selects name and salary for all rows in the employee table. Notice we have imported the 'e' alias from the Aliases object. This is a handy shortcut that refers to the exact same Employee object.
```kotlin
 val asList: List<Pair<Long, String>> = it.select(e.id, e.name).noWhereClause().asList() // potentially empty
 val asOption: Pair<Long, String>? = it.select(e.id, e.name).noWhereClause().firstOrNull() // None if no row can be retrieved
 val singleResult: Pair<Long, String> = it.select(e.id, e.name).noWhereClause().first() //Will throw an exception if no row can be found
```

The result is always a Tuple object that corresponds in size and type to the colummns you specified in the `.select(..)` call. For null-safety's sake all values are returned as nullable types, because primitive instances (Int, Long, Boolean etc.) cannot be null in Kotlin, but *can* be null in the database.

The power of relational databases lies in combining results from multiple tables by laying the proper join conditions. The `Employee` and `Address` tables are linked via the `EmployeeAddress` table in a many-to-many fashion. Since the foreign key relations are explicit in the source code, DbObjekts can build the joins for you:

```kotlin
  transaction.select(e.name, e.dateOfBirth, e.children, e.married).where(Address.street.eq("Pete Street")).asList()
```

We can select from the `Employee` table with a constraint on the `Address` table, without specifying the join! This mechanism saves you a lot of typing, but comes with limitations:
1) There must be an explicit foreign-key relationship between the tables used in your statement, or a join table that links two tables referred in your query, like in the above example.
2) all joins are left outer joins.

If the framework cannot unambiguously resolve the join conditions, you have to provide them yourself. Call the `from(SomeTable)` method with the driving table of your selection, and add the tables to joined as follows:

```kotlin
 transaction.select(e.name, c.name)
      .from(Employee.innerJoin(ea).innerJoin(Address).innerJoin(Country))
      .where(ea.kind eq "WORK").asList()
```

This resolves to the following SQL:

```sql
 FROM EMPLOYEE e JOIN EMPLOYEE_ADDRESS ae on e.id = ae.employee_id JOIN ADDRESS a on a.id = ae.address_id join COUNTRY c on c.id = a.country_id
```

### List or single result ###
`asList` always return a (potentially empty) list of results. If one row is all you need, you can invoke `first()` or the safer option `firstOrNull()`, since the former will throw if the resultset was empty.

### with custom mapper ###
When you execute a select statement, DbObjekts pulls all results into a list structure, which add to the JVM heap. This may not be what you want.
The `forEachRow()` call lets you inspect the resultset row by row through a custom predicate so you can decide how to handle them and even abort further retrieval, which means reduced traffic from the RDBMS to your application.
```kotlin
  val buffer = mutableListOf<String?>()
    transaction.select(e.name).noWhereClause().orderAsc(e.name).forEachRow({ row ->
    buffer.add(row)
    //there are three rows in the resultset, but we stop fetching after two
    buffer.size != 2
})
```
### order and limit ###
You can further tweak select results with the `orderBy` and `limit(..)` methods. This orders all employees by salary (highest first), then by name (A-Z), and retrieves the first ten rows.
Note that these constraints are executed server-side, as they are part of the SQL. DbObjekts takes care of the proper syntax, because vendors handle the limit clause differently.

```kotlin
  tr.select(e.name).noWhereClause.orderDesc(e.salary).orderAsc(e.name).limit(10).asList()
```

### Contact ###
