package com.dbobjekts.statement

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.metadata.column.ColumnAndValue
import com.dbobjekts.metadata.column.NullableColumnAndValue

class ColumnsForUpdate {

     val params: MutableList<AnySqlParameter> = mutableListOf<AnySqlParameter>()

    fun <C> addParam(param: ColumnAndValue<C>) {
        val size = numberOfParameters()
        params += SqlParameter.fromColumnValue(size, param)
    }

    fun <C> addParam(param: NullableColumnAndValue<C>) {
        val size = numberOfParameters()
        params += SqlParameter.fromColumnValue(size, param)
    }

    fun numberOfParameters(): Int = params.size

    fun getCommaSeparatedColumnList(): String = params.map { it.column.nameInTable }.joinToString(",")

    fun getCommaSeparatedQuestionMarks(): String = params.map { "?" }.joinToString(",")

    companion object {
        fun fromValues(values: List<AnyColumnAndValue>): ColumnsForUpdate {
            val obj = ColumnsForUpdate()
            values.forEach { obj.addParam(it) }
            return obj
        }
    }

}

