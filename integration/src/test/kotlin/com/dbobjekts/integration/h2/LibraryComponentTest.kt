package com.dbobjekts.integration.h2

import com.dbobjekts.api.Tuple3
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.integration.h2.inventory.Author
import com.dbobjekts.integration.h2.inventory.Book
import com.dbobjekts.integration.h2.inventory.Item
import com.dbobjekts.integration.h2.operations.Loan
import com.dbobjekts.integration.h2.operations.Member
import com.dbobjekts.util.PathUtil
import com.dbobjekts.util.TestSourceWriter
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime


class LibraryComponentTest {

    @Test
    fun `create schemas`() {

        H2DB.setupDatabaseObjects()
        H2DB.newTransaction { tr->
            val orwell: Long = tr.insert(Author).mandatoryColumns("George", "Orwell", dateOfBirth = LocalDate.of(1903, 6, 25)).execute()
            val rowling: Long = tr.insert(Author).mandatoryColumns("Joanne", "Rowling", dateOfBirth = LocalDate.of(1965, 7, 31)).execute()
            val nineteen = tr.insert(Book).mandatoryColumns("ISBN-1984", "Nineteen-eighty Four", orwell, LocalDate.of(1948,1,1)).execute()
            val philosopher = tr.insert(Book).mandatoryColumns("ISBN-PHILOSOPHER", "Harry Potter and the Philsopher's Stone", rowling, LocalDate.of(1999,1,1)).execute()
            val item19 = tr.insert(Item).mandatoryColumns(nineteen, LocalDate.of(1980,5,5)).execute()
            tr.insert(Item).mandatoryColumns(nineteen, LocalDate.of(1990,5,5)).execute()
            tr.insert(Item).mandatoryColumns(philosopher, LocalDate.of(2005,5,5)).execute()
            val phil1 = tr.insert(Item).mandatoryColumns(philosopher, LocalDate.of(2005,5,5)).execute()
            val phil2 = tr.insert(Item).mandatoryColumns(philosopher, LocalDate.of(2005,5,5)).execute()

            val john = tr.insert(Member).mandatoryColumns("John", "Wayne", LocalDate.of(1980,1,1)).execute()
            val sally = tr.insert(Member).mandatoryColumns("Sally", "Hawkins", LocalDate.of(1977,1,1)).execute()

            val rows: List<Tuple3<String, String, Long>> = tr.select(Book.title, Author.name, Item.id).where(Book.published.lt(LocalDate.of(1980,1,1))).asList()
            rows.forEach {(title, name, id) ->
                println("$title by $name item $id")
            }

            tr.insert(Loan).mandatoryColumns(memberId = sally, itemId = item19, dateLoaned = ZonedDateTime.now().toInstant()).execute()
            tr.insert(Loan).mandatoryColumns(memberId = sally, itemId = phil1, dateLoaned = ZonedDateTime.now().toInstant()).execute()
            tr.insert(Loan).mandatoryColumns(memberId = john, itemId = phil2, dateLoaned = ZonedDateTime.now().toInstant()).execute()

            tr.select(Loan.id, Item.id, Book.title, Author.name, Member.name).asList().forEach {
                (loan,item,book,author,member) ->
                println("$loan $book $author borrowed by $member")
            }

            tr.select(Loan.id, Loan.itemId).asList().forEach {
                    (loan,item) ->
                println("$loan $item")
            }

            val loans = tr.sql("""
                select i.id, b.title, a.name , m.name , l.date_loaned from 
                inventory.book b 
                join inventory.author a on a.id = b.author_id 
                join inventory.item i on i.book_id = b.id 
                left join operations.loan l on l.item_id = i.id 
                left join operations.member m on m.id = l.member_id
            """.trimIndent()).withResultTypes().long().string().string().stringNil().dateNil().asList()

            loans.forEach {(id, title,author, member, date) ->
                println("$id, $title by $author was loaned by $member on $date")
            }
            tr.select(Book.isbn)
        }

    }

}




