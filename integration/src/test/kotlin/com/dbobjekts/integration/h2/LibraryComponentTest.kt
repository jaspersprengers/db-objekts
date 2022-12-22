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
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime


class LibraryComponentTest {

    @Test
    fun `integration test`() {

        H2DB.setupDatabaseObjects()
        H2DB.newTransaction { tr->
            val orwell: Long = tr.insert(Author).mandatoryColumns("George Orwell").execute()
            val rowling: Long = tr.insert(Author).mandatoryColumns("Joanne Rowling").execute()

            tr.update(Author).bio("(1903-1950) Pseudonym of Eric Blair. One of the most influential English authors and journalist of the early 20th century.")
                .where(Author.id.eq(orwell))

            tr.insert(Book).mandatoryColumns("ISBN-1984", "Nineteen-eighty Four", orwell, LocalDate.of(1948,1,1)).execute()
            tr.insert(Book).mandatoryColumns("ISBN-WIGAN", "The Road to Wigan Pier", orwell, LocalDate.of(1940,1,1)).execute()

            val item19 = tr.insert(Item).mandatoryColumns("ISBN-1984", LocalDate.of(1980,5,5)).execute()
            tr.insert(Item).mandatoryColumns("ISBN-1984", LocalDate.of(1990,5,5)).execute()

            val ret = tr.insert(Book).mandatoryColumns("ISBN-PHILOSOPHER", "Harry Potter and the Philsopher's Stone", rowling, LocalDate.of(1999,1,1)).execute()
            tr.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005,5,5)).execute()
            val phil1 = tr.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005,5,5)).execute()
            val phil2 = tr.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005,5,5)).execute()

            val john = tr.insert(Member).mandatoryColumns("John Wayne").execute()
            val sally = tr.insert(Member).mandatoryColumns("Sally Hawkins").execute()

            val rows: List<Tuple3<String, String, Long>> = tr.select(Book.title, Author.name, Item.id).where(Book.published.lt(LocalDate.of(1980,1,1))).asList()
            rows.forEach {(title, name, id) ->
                println("$title by $name item $id")
            }

            tr.insert(Loan).mandatoryColumns(memberId = sally, itemId = item19, dateLoaned = LocalDate.now()).execute()
            tr.insert(Loan).mandatoryColumns(memberId = sally, itemId = phil1, dateLoaned = LocalDate.now()).execute()
            tr.insert(Loan).mandatoryColumns(memberId = john, itemId = phil2, dateLoaned = LocalDate.now()).execute()

            assertThat(tr.select(Book.title, Item.id, Item.dateAcquired).where(Book.isbn.eq("ISBN-WIGAN")).firstOrNull()).isNull()

            val itemsWigan = tr.select(Book.title, Item.id.nullable).where(Book.isbn.eq("ISBN-WIGAN")).useOuterJoins().first()
            assertThat(itemsWigan.v2).isNull()


            tr.select(Loan.dateLoaned, Item.id, Book.title, Author.name, Member.name).asList().forEach {
                (loan,item,book,author,member) ->
                println("$book by $author loaned to$member on $loan")
            }

            tr.select(Item.id, Item.isbn, Member.name, Loan.dateLoaned.nullable, Loan.dateReturned)
                .from(Loan.rightJoin(Item).innerJoin(Book).innerJoin(Member)).forEachRow {(item,isbn,member,loaned,returned)->
                    println("$item $isbn $loaned by $member returned $returned")
                    true
                }


        }

    }

}




