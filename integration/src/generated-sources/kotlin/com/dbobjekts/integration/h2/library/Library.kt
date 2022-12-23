package com.dbobjekts.integration.h2.library
import com.dbobjekts.metadata.Schema
object Library : Schema("LIBRARY", listOf(Author, Book, Item, Loan, Member))