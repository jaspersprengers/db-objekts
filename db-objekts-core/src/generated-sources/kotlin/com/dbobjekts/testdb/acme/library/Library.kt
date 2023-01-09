package com.dbobjekts.testdb.acme.library
import com.dbobjekts.metadata.Schema
object Library : Schema("LIBRARY", listOf(Author, Book, BookReview, Item, Loan, Member))