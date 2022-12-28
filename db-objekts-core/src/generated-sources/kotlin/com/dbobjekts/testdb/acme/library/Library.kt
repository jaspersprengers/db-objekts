package com.dbobjekts.testdb.acme.library
import com.dbobjekts.metadata.Schema
object Library : Schema("LIBRARY", listOf(Author, Book, Item, Loan, Member))