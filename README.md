# Introduction #

DbObjekts is a Kotlin library aimed to facilitate working with relational databases in Kotlin application code.
It is a port of a project I started in Scala, but I found it challenging enough to also do it in Kotlin, making the best use of what each language has to offer.

The library uses regular JDBC functionality and can be configured with either an existing `javax.sql.DataSource`, or with the built-in Hikari connection pool.
 ```kotlin
val datasource =
    HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")
val transactionManager = TransactionManager.newBuilder()
    .dataSource(datasource)
    .catalog(Catalogdefinition) //Catalogdefinition is a Kotlin object with schema and table metadata.
    .autoCommit(true)
    .build()
```
DbObjekts lets you run native SQL queries where you can parameterize the types to be returned and provide query parameters as a vararg of values.
The framework takes care of transaction management: opening, closing and committing the underlying connection.

```kotlin
  val results: List<Pair<Long, String>> = manager.newTransaction { it.select2<Long, String>("select count(1), name from employee where age > ? and married = ?", 21, true).asList() }
```

The second major feature is working with generated database metadata. The code generator utility creates Kotlin source code from a liquibase changelog file.
and these objects represent the tables, columns and integrity constraints of your database schemas.

```kotlin
      CodeGenerator()
        .vendor("h2")
        .outputDirForGeneratedSources(PathUtil.getGeneratedSourceDir())
        .basePackageForSources("com.dbobjekts.example")
        .sequenceForTableResolver(H2SequenceNameResolver)
        .addLiquibaseChangelogFile("core", PathUtil.getFileInResourcesDir("core-changelog.xml"))
        .addLiquibaseChangelogFile("hr", PathUtil.getFileInResourcesDir("hr-changelog.xml"))
        .addLiquibaseChangelogFile("custom", PathUtil.getFileInResourcesDir("all_types.xml"))
        .addCustomSqlMapper(AddressTypeMapper)
        .addCustomSqlMapper(IntToBooleanMapper)
        .addCustomSqlMapper(AddressTypeMapper)
        .generate()
``` 

Table objects are a little like ORM (object relational mapping) entities, but they are singleton (Kotlin) objects and do not contain data. Instead you use them to construct type-safe CRUD queries.  
Added benefits:
- secure: queries are constructed with placeholder syntax and any arguments are sanity-checked.
- type-safe: all values for update and selection are tied to column metadata, which are parameterized for type.
- null-safe: Databases allow null values for numeric data, but Kotlin AnyVal (Int, Long, Boolean, etc) cannot. They are wrapped in Options.

DbObjekts is certainly not the first library built on top of JDBC that tries to take the pain in working with databases.
It does however take an opinionated view on the topic of object-relational mapping, which is that a one-to-one mapping

# Tutorial #

## Installation ##

Since the library is still in beta, you need to build it from source.

## Setting up the TransactionManager ##

Your application code deals with DbObjekts through an instance of `TransactionManager`. This corresponds to (and wraps) a single DataSource, comparable to a `SessionFactory` in Hibernate.  
The `javax.sql.DataSource` contains all relevant configuration: connection string, username, password and other settings. Usually it also manages a pool of connections.

### Configuration using an existing DataSource ###
If you have no existing DataSource, you can use `HikariDataSourceFactory`, which creates a new HikariDataSource. If offers the bare minimum of necessary configuration,
so feel free to create your own `HikariDataSource` directly for maximum expressiveness.

```kotlin
val dataSource: DataSource = DataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa")// bare-bones H2 memory database as used for integration tests.
val transactionManager = DbObjekts.newTransactionManagerBuilder(dataSource).create()
```

### Global configuration ###

You only need one instance of the `TransactionManager` per `DataSource` in a single JVM. So if you only have one type of connection in your application - a common case -,
you might as well access the TransactionManager in a singleton fashion, i.e. without passing around a reference. The following flavour does just that:

```kotlin
  // Nothing is returned, but you can now acces the methods on the TransactionManager object directly 
SingletonTransactionManager.configurer()
    .dataSource(someDataSource)
    .configure()
```  

`SingletonTransactionManager.isConfigured()` tells you if the system has been set up. Note that you cannot call `configure()` twice. You must call `SingletonTransactionManager.invalidate()`. This closes the current datasource.

### My first query ###

Now the system is set up to run queries, and you run them against a `Transaction` instance. This is a short-lived instance that wraps a single `java.sql.Connection`
obtained from the pool and returned once you are done.

```kotlin
  import SingletonTransactionManager.*
  newTransaction {
    transaction -> val rows = transaction.select3<String, Double, Boolean>("select name, salary, married from employee where date_of_birth > ?", LocalDate.of(1980,1,1)).asList()
  }             
``` 

This snippet packs quite a lot of information, so let's take it apart.
- `TransactionManager.newTransaction` takes a function body and supplies it with a `Transaction` argument after creating a new Connection. Once the body has been executed it commits the transaction.
- The `select3(String, Double, Boolean)` method is intended for native SQL queries that return results. It has to be parameterized with the types you intend to retrieve, in this case String, Double, Boolean.
- You can provide optional query parameters as a vararg array. The size must match the number of question marks in the query.
- Results are returned as a list of `Triple<String?, Double?, Boolean?>`. All values are nullable, because the corresponding rows may return null values.

SQL that does not expect a result is a lot simpler. Use the `execute(..)` method for this.

```kotlin
   transaction.execute("DELETE FROM admissions WHERE id = ?", 1234)
```

### New or re-used transactions ###

The `newTransaction` method runs all queries in a single transaction, but this is not what you want if a set of disparate write operations in different classes has to be treated atomically.

In that case you should use `joinTransaction(..)`. The `TransactionManager` saves the last non-committed `Transaction` instance for each JVM thread and uses that when you call `joinTransaction` instead of creating a new one.
If there is no previous `Transaction` it will create one for you.
Some words of warning though:

- DbObjekts will not perform the commit for you. You have to do it manually when you're ready: `transaction.commit()`.
- JDBC runs with autocommit=true by default. You have to set it to false during setup if you want to use `joinTransaction()` effectively: `DbObjekts.configureforSingleDataSource(dataSource).autoCommit(false)`
- Transactions are only re-used within the same JVM thread, for obvious reasons.

## Working with generated Table metadata ##

### Generating the code ###
TBD

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

* Jasper Sprengers at jaspersprengers@outlook.com


import com.dbobjekts.Tuple4
import com.dbobjekts.example.Aliases.c
import com.dbobjekts.example.Aliases.ea
import com.dbobjekts.example.Catalogdefinition
import com.dbobjekts.example.core.Address
import com.dbobjekts.example.core.Country
import com.dbobjekts.example.core.Employee
import com.dbobjekts.example.core.EmployeeAddress
import com.dbobjekts.example.custom.AddressType
import com.dbobjekts.example.hr.Hobby
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.metadata.Columns.BOOLEAN_NIL
import com.dbobjekts.metadata.Columns.DOUBLE_NIL
import com.dbobjekts.metadata.Columns.VARCHAR_NIL
import com.dbobjekts.util.HikariDataSourceFactory
import java.time.LocalDate

val datasource =
HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")
val tm: TransactionManager = TransactionManager.newBuilder()
.dataSource(datasource)
.catalog(Catalogdefinition)
.autoCommit(true)
.build()

tm.newTransaction { tr ->
// CreateExampleCatalog(tr)


    val rows = tr.select(
        "select name, salary, married from employee where date_of_birth > ?",
        VARCHAR_NIL, DOUBLE_NIL, BOOLEAN_NIL,
        LocalDate.of(1980, 1, 1)
    ).asList()


    val id = tr.insert(Employee).mandatoryColumns("Jane Doe", 3050.3, LocalDate.of(1980, 5, 7)).execute()
    val e = Employee
    val a = Address
    val h = Hobby

    tr.update(Employee).salary(4000.0).where(e.id.eq(id))

    val withHobby: List<Pair<String, String>> = tr.select(e.name, h.name).where(e.salary.gt(3000.0)).asList()

    val optionalHobby: List<Pair<String, String?>> = tr.select(e.name, h.name)
        .from(e.leftJoin(h))
        .where(e.salary.gt(3000.0)).asList()

    // The mandatoryColumns method helps you to provide all non-nullable fields for the insert.
    tr.insert(Country).mandatoryColumns("nl", "Netherlands").execute()
    tr.insert(Country).mandatoryColumns("be", "Belgium").execute()
    tr.insert(Country).mandatoryColumns("de", "Germany").execute()

    // We have Pete, Jane and Bob. If an insert resulted in a generated primary key value, it is returned as a Long from the execute() method.
    val petesId: Long = tr.insert(Employee).mandatoryColumns("Pete", 5020.34, LocalDate.of(1980, 5, 7)).married(true).execute()
    val janesId: Long = tr.insert(Employee).mandatoryColumns("Jane", 6020.0, LocalDate.of(1978, 5, 7)).married(true).execute()
    tr.insert(Employee).mandatoryColumns("Bob", 3020.34, LocalDate.of(1980, 5, 7)).execute()

    // Jane works in Belgium, Pete works in Germany, and they both live in the Netherlands.
    val janesWorkAddress: Long = tr.insert(Address).mandatoryColumns("Rue d'Eglise", "be").execute()
    val petesWorkAddress: Long = tr.insert(Address).street("Kirchstrasse").countryId("de").execute()
    val janeAndPetesHomeAddress: Long = tr.insert(Address).mandatoryColumns("Kerkstraat", "nl").execute()

    tr.insert(EmployeeAddress).mandatoryColumns(janesId, janesWorkAddress).kind(AddressType.WORK).execute()
    tr.insert(EmployeeAddress).mandatoryColumns(petesId, petesWorkAddress).kind(AddressType.WORK).execute()
    tr.insert(EmployeeAddress).mandatoryColumns(petesId, janeAndPetesHomeAddress).kind(AddressType.HOME).execute()
    tr.insert(EmployeeAddress).mandatoryColumns(janesId, janeAndPetesHomeAddress).kind(AddressType.HOME).execute()

    // This query selects name and salary for all rows in the employee table. Notice we have imported the 'e' alias from the Aliases object. This is a handy shortcut that refers to the exact same Employee object.
    // Consider the different methods to retrieve results
    val asList: List<Pair<String, Double>> = tr.select(e.name, e.salary).noWhereClause().asList() // empty when no match
    val asNullable: Pair<String, Double>? = tr.select(e.name, e.salary).noWhereClause().firstOrNull()// Null when no match
    val singleResult: Pair<String, Double> = tr.select(e.name, e.salary).noWhereClause().first() // throws when no match

    //Nested conditions in the where-clause are possible:
    val listOfIds = tr.select(e.id).where(e.salary.gt(400.0).or(e.married.eq(true).and(e.salary).isNotNull())).asList()

    //This select from the employee table, with a condition on the address table, using the employee_address many-to-many join table
    // DBObjekts takes care of joining the necessary tables.
    val results: List<Tuple4<String, LocalDate, Int?, Boolean?>> =
        tr.select(e.name, e.dateOfBirth, e.children, e.married).where(Address.street.eq("Pete Street")).asList()

    //The automatic join-chain resolution is handy, but comes with limitations:
    // 1) There must be a direct foreign-key relationship between the tables used in your statement, and
    // 2) all joins are left outer joins.
    // If your join chain contains tables that are not in the table, and for which there is no direct many-to-many relationship, you have to build it yourself.
    // Call the from() method with the driving table of your selection, and add the tables to joined as follows:
    // it resolves to the following SQL: ... FROM EMPLOYEE e JOIN EMPLOYEE_ADDRESS ae on e.id = ae.employee_id JOIN ADDRESS a on a.id = ae.address_id join COUNTRY c on c.id = a.country_id
    // Since the FK relationships between employee/employee-address and employee-address/address are known, DbObjekts can resolve the columns involved.
    tr.select(e.name, c.name)
        .from(Employee.innerJoin(ea).innerJoin(Address).innerJoin(Country))
        .where(ea.kind.eq(AddressType.WORK)).asList()

    //Another way to retrieve results is as a map, rather than tuples. Every table has a .* method, which returns all its column values in a ResultMap

/*

            //Select with range
            tr.select(Employee.*).where(e.salary gt 1500 and e.salary lt 6000).asResultMaps()

            //IN and NOT IN are supported as well
            tr.select(Employee.*).where(e.id in(petesId, janesId) and e.name notIn("Pete", "Bob")).asList()

            //String matching methods startsWith, endsWith and contains produce SQL with LIKE '%xxx' syntax
            tr.select(e.name).where(e.name startsWith "Arthur" or e.name endsWith "Dent" or e.name contains "Philip").asList()

            //tr.select(Employee.*).noWhereClause.orderDesc(e.salary).orderAsc(e.name).limit(10).as

            //Update a single row:
            tr.update(Employee).salary(peteFields(e.salary).get + 300.0).where(e.id eq petesId)

            //Delete a row:
            tr.deleteFrom(EmployeeAddress).where(ea.employeeId eq petesId)
     */


}
