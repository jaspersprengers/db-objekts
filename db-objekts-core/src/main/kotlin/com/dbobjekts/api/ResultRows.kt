@file:Suppress("UNCHECKED_CAST")

package com.dbobjekts.api

import com.dbobjekts.api.exception.StatementExecutionException
import com.dbobjekts.jdbc.JDBCResultSetAdapter
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.ColumnInResultRow
import java.sql.ResultSet

/**
 * Is returned upon successful execution of a query and allows access to the query results.
 */
abstract class ResultRow<out O> {

    protected lateinit var jdbcResultSetAdapter: JDBCResultSetAdapter
    private lateinit var rows: List<O>
    internal lateinit var selectables: List<Selectable<*>>

    internal fun initialize(jdbcResultSetAdapter: JDBCResultSetAdapter) {
        this.jdbcResultSetAdapter = jdbcResultSetAdapter
    }

    internal fun retrieveAll(slice: Slice? = null){
        rows = jdbcResultSetAdapter.retrieveAll(this, slice)
    }

    internal fun extractValue(column: ColumnInResultRow, resultSet: ResultSet): Any? {
        return column.column.retrieveValue(column.position, resultSet)
    }

    internal fun first(): O =
        if (rows.isEmpty())
            throw StatementExecutionException("Expected exactly one row, but result set was empty.")
        else rows.get(0)

    internal fun firstOrNull(): O? = if (rows.isEmpty()) null else rows.get(0)

    internal fun asList(): List<O> = rows

    internal fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): O{
        var index = 0
        val values = mutableListOf<Any?>()
        for (s in selectables){
            val retrieved: List<Any?> = s.columns.mapIndexed() { idx, _ ->
                val curr = index + idx
                extractValue(cols[curr], resultSet)
            }
            values += s.toValue(retrieved)
            index += s.columns.size
        }
        return castToRow(values)
    }

    abstract fun castToRow(values: List<Any?>): O

    internal fun columns(): List<AnyColumn> = jdbcResultSetAdapter.resultSetColumns().map { it.column }

}

class ResultRow1<A> : ResultRow<A>() {
    override fun castToRow(values: List<Any?>): A = values[0] as A
}

class ResultRow2<T1, T2> : ResultRow<Tuple2<T1, T2>>() {
    override fun castToRow(values: List<Any?>): Tuple2<T1, T2> {
        return Tuple2(values[0] as T1, values[1] as T2)
    }
}


class ResultRow3<T1, T2, T3> : ResultRow<Tuple3<T1, T2, T3>>() {
    override fun castToRow(values: List<Any?>): Tuple3<T1, T2, T3> =
        Tuple3(values[0] as T1, values[1] as T2, values[2] as T3)
}

class ResultRow4<T1, T2, T3, T4> : ResultRow<Tuple4<T1, T2, T3, T4>>() {
    override fun castToRow(values: List<Any?>): Tuple4<T1, T2, T3, T4> =
        Tuple4(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4
        )
}

class ResultRow5<T1, T2, T3, T4, T5> : ResultRow<Tuple5<T1, T2, T3, T4, T5>>() {
    override fun castToRow(values: List<Any?>): Tuple5<T1, T2, T3, T4, T5> =
        Tuple5(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5
        )
}

class ResultRow6<T1, T2, T3, T4, T5, T6> : ResultRow<Tuple6<T1, T2, T3, T4, T5, T6>>() {
    override fun castToRow(values: List<Any?>): Tuple6<T1, T2, T3, T4, T5, T6> =
        Tuple6(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6
        )
}

class ResultRow7<T1, T2, T3, T4, T5, T6, T7> : ResultRow<Tuple7<T1, T2, T3, T4, T5, T6, T7>>() {
    override fun castToRow(values: List<Any?>): Tuple7<T1, T2, T3, T4, T5, T6, T7> =
        Tuple7(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7
        )
}

class ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8> : ResultRow<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>>() {
    override fun castToRow(values: List<Any?>): Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> =
        Tuple8(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8
        )
}

class ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9> : ResultRow<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>() {
    override fun castToRow(values: List<Any?>): Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> =
        Tuple9(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9
        )
}

class ResultRow10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> : ResultRow<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>() {
    override fun castToRow(values: List<Any?>): Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> =
        Tuple10(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10
        )
}

class ResultRow11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> : ResultRow<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>() {
    override fun castToRow(values: List<Any?>): Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> =
        Tuple11(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11
        )
}

class ResultRow12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> :
    ResultRow<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> =
        Tuple12(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12
        )
}

class ResultRow13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> :
    ResultRow<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> =
        Tuple13(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13
        )
}

class ResultRow14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> :
    ResultRow<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>() {
    override fun castToRow(values: List<Any?>): Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> =
        Tuple14(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14
        )
}

class ResultRow15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> :
    ResultRow<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> =
        Tuple15(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15
        )
}

class ResultRow16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> :
    ResultRow<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> =
        Tuple16(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16
        )
}

class ResultRow17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> :
    ResultRow<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> =
        Tuple17(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16,
            values[16] as T17
        )
}

class ResultRow18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> :
    ResultRow<Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> =
        Tuple18(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16,
            values[16] as T17,
            values[17] as T18
        )
}

class ResultRow19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> :
    ResultRow<Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>() {
    override fun castToRow(values: List<Any?>): Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> =
        Tuple19(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16,
            values[16] as T17,
            values[17] as T18,
            values[18] as T19
        )
}

class ResultRow20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> :
    ResultRow<Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> =
        Tuple20(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16,
            values[16] as T17,
            values[17] as T18,
            values[18] as T19,
            values[19] as T20
        )
}

class ResultRow21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> :
    ResultRow<Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> =
        Tuple21(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16,
            values[16] as T17,
            values[17] as T18,
            values[18] as T19,
            values[19] as T20,
            values[20] as T21
        )
}

class ResultRow22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> :
    ResultRow<Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>() {
    override fun castToRow(values: List<Any?>
    ): Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> =
        Tuple22(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
            values[10] as T11,
            values[11] as T12,
            values[12] as T13,
            values[13] as T14,
            values[14] as T15,
            values[15] as T16,
            values[16] as T17,
            values[17] as T18,
            values[18] as T19,
            values[19] as T20,
            values[20] as T21,
            values[21] as T22
        )
}
