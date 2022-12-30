package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable

interface AutoKeyColumn<I> : IsPrimaryKey

class AutoKeyIntegerColumn(table: AnyTable, name: String) : IntegerColumn(table, name), AutoKeyColumn<Int>

class AutoKeyLongColumn(table: AnyTable, name: String):LongColumn(table, name), AutoKeyColumn<Long>





