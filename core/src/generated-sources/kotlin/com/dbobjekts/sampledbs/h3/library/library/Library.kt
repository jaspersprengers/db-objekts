package com.dbobjekts.sampledbs.h3.library.library
import com.dbobjekts.metadata.Schema
object Library : Schema("LIBRARY", listOf(Author, Book, Item, Loan, Member))