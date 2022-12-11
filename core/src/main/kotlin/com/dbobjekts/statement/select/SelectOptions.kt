package com.dbobjekts.statement.select

import com.dbobjekts.AnyColumn

interface SelectOptions

interface Sorting : SelectOptions

class Asc(val column: AnyColumn) : Sorting

class Desc(val column: AnyColumn) : Sorting

class Limit(val rows: Int) : SelectOptions
