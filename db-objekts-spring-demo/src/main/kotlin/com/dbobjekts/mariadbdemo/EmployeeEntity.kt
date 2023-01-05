package com.dbobjekts.mariadbdemo

import com.dbobjekts.api.Tuple2

data class EmployeeEntity(val id: Long, val name: String) {
    constructor(tuple: Tuple2<Long, String>) : this(tuple.v1, tuple.v2)
}
