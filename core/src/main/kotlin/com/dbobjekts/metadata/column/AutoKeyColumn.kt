package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table

interface AutoKeyColumn<I> : IsPrimaryKey

class AutoKeyIntegerColumn(table: Table, name: String) : IntegerColumn(table, name), AutoKeyColumn<Int>

class AutoKeyLongColumn(table: Table, name: String):LongColumn(table, name), AutoKeyColumn<Long>





