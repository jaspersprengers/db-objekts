package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.ColumnFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ColumnReflectionTest {

    @Test
    fun `check presence of nullable counterparts`(){
        fun <T> runChecks(col: NonNullableColumn<T>){
            assertThat(col.nullable).isInstanceOf(NullableColumn::class.java)
            assertThat(col.distinct()).isInstanceOf(col::class.java)
            assertThat(col.count()).isInstanceOf(LongColumn::class.java)
        }
        runChecks(ColumnFactory.VARCHAR)
        runChecks(ColumnFactory.LONG)
        runChecks(ColumnFactory.INTEGER)
        runChecks(ColumnFactory.SHORT)
        runChecks(ColumnFactory.BYTE_ARRAY)
        runChecks(ColumnFactory.BLOB)
        runChecks(ColumnFactory.CLOB)
        runChecks(ColumnFactory.BYTE)
        runChecks(ColumnFactory.BOOLEAN)
        runChecks(ColumnFactory.NUMBER_AS_BOOLEAN)
        runChecks(ColumnFactory.DOUBLE)
        runChecks(ColumnFactory.FLOAT)
        runChecks(ColumnFactory.BIGDECIMAL)
        runChecks(ColumnFactory.DATE)
        runChecks(ColumnFactory.DATETIME)
        runChecks(ColumnFactory.TIME)
        runChecks(ColumnFactory.OFFSET_DATETIME)
        runChecks(ColumnFactory.SQLXML)
        runChecks(ColumnFactory.FOREIGN_KEY_LONG)
        runChecks(ColumnFactory.FOREIGN_KEY_INT)
        runChecks(ColumnFactory.FOREIGN_KEY_VARCHAR)
    }

    @Test
    fun `check aggregators for integer columns`(){
        fun runChecks(col: IntegerNumericColumn){
            assertThat(col.sum()).isInstanceOf(LongColumn::class.java)
            assertThat(col.avg()).isInstanceOf(DoubleColumn::class.java)
            assertThat(col.max()).isInstanceOf(LongColumn::class.java)
            assertThat(col.min()).isInstanceOf(LongColumn::class.java)
        }
        runChecks(ColumnFactory.LONG)
        runChecks(ColumnFactory.INTEGER)
        runChecks(ColumnFactory.SHORT)
        runChecks(ColumnFactory.BYTE)
        runChecks(ColumnFactory.AUTOKEY_LONG)
        runChecks(ColumnFactory.AUTOKEY_INTEGER)
        runChecks(ColumnFactory.SEQUENCE_INTEGER)
        runChecks(ColumnFactory.SEQUENCE_LONG)
        runChecks(ColumnFactory.FOREIGN_KEY_LONG)
        runChecks(ColumnFactory.FOREIGN_KEY_INT)

        runChecks(ColumnFactory.LONG_NIL)
        runChecks(ColumnFactory.INTEGER_NIL)
        runChecks(ColumnFactory.SHORT_NIL)
        runChecks(ColumnFactory.BYTE_NIL)
        runChecks(ColumnFactory.FOREIGN_KEY_LONG_NIL)
        runChecks(ColumnFactory.FOREIGN_KEY_INT_NIL)
    }

    @Test
    fun `check aggregators for floating point columns`(){
        fun runChecks(col: FloatingPointNumericColumn){
            assertThat(col.sum()).isInstanceOf(DoubleColumn::class.java)
            assertThat(col.avg()).isInstanceOf(DoubleColumn::class.java)
            assertThat(col.max()).isInstanceOf(DoubleColumn::class.java)
            assertThat(col.min()).isInstanceOf(DoubleColumn::class.java)
        }
        runChecks(ColumnFactory.DOUBLE)
        runChecks(ColumnFactory.FLOAT)
        runChecks(ColumnFactory.DOUBLE_NIL)
        runChecks(ColumnFactory.FLOAT_NIL)
    }

}
