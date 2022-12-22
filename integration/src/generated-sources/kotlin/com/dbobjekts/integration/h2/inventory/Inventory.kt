package com.dbobjekts.integration.h2.inventory
import com.dbobjekts.metadata.Schema
object Inventory : Schema("INVENTORY", listOf(Author, Book, Item))