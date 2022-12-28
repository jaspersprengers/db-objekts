package com.dbobjekts.testdb

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.*
import java.lang.IllegalStateException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.regex.Pattern


class DutchPostCodeColumn(table: Table, name: String) : VarcharColumn(table, name) {
    override val nullable: NullableColumn<String?> = NullableDutchPostCodeColumn(table, name)

    override fun getValue(position: Int, resultSet: ResultSet): String? {
        return super.getValue(position, resultSet)?.also { validate(it) }
    }

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

class NullableDutchPostCodeColumn(table: Table, name: String) : NullableVarcharColumn(table, name) {
    override fun getValue(position: Int, resultSet: ResultSet): String? {
        return super.getValue(position, resultSet)?.also { DutchPostCodeColumn.validate(it) }
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: String?) {
        if (value != null)
            DutchPostCodeColumn.validate(value)
        super.setValue(position, statement, value)
    }
}
