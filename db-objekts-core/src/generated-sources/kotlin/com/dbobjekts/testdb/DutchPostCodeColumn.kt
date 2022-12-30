package com.dbobjekts.testdb

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import java.sql.PreparedStatement
import java.util.regex.Pattern


class DutchPostCodeColumn(table: AnyTable, name: String) : VarcharColumn(table, name) {
    override val nullable: NullableColumn<String?> = NullableDutchPostCodeColumn(table, name)


    override fun setValue(position: Int, statement: PreparedStatement, value: String) {
        validate(value)
        super.setValue(position, statement, value)
    }

    companion object {
        val pattern = Pattern.compile("^\\d{4}[A-Z]{2}$")
        fun validate(postcode: String) {
            if (!pattern.matcher(postcode).matches())
                throw IllegalStateException("$postcode is not a valid Dutch postcode.")
        }
    }
}

class NullableDutchPostCodeColumn(table: AnyTable, name: String) : NullableVarcharColumn(table, name) {

    override fun setValue(position: Int, statement: PreparedStatement, value: String?) {
        value?.let { DutchPostCodeColumn.validate(it) }
        super.setValue(position, statement, value)
    }
}
