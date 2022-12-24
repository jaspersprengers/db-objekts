package com.dbobjekts.api

import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.*


enum class Columns(val instance: NonNullableColumn<*>) {
    VARCHAR(ColumnFactory.VARCHAR),
    BYTE(ColumnFactory.BYTE),
    SHORT(ColumnFactory.SHORT),
    INTEGER(ColumnFactory.INTEGER),
    LONG(ColumnFactory.LONG),
    FLOAT(ColumnFactory.FLOAT),
    DOUBLE(ColumnFactory.DOUBLE),
    BIGDECIMAL(ColumnFactory.BIGDECIMAL),
    BYTE_ARRAY(ColumnFactory.BYTE_ARRAY),
    BLOB(ColumnFactory.BLOB),
    CLOB(ColumnFactory.CLOB),
    BOOLEAN(ColumnFactory.BOOLEAN),
    NUMBER_AS_BOOLEAN(ColumnFactory.NUMBER_AS_BOOLEAN),
    DATE(ColumnFactory.DATE),
    DATETIME(ColumnFactory.DATETIME),
    TIME(ColumnFactory.TIME),
    TIMESTAMP(ColumnFactory.TIMESTAMP),
    OFFSET_DATETIME(ColumnFactory.OFFSET_DATETIME),
}
