package com.dbobjekts.result

import com.dbobjekts.*
import java.sql.ResultSet


class ResultRow1<A> : ResultRow<A>() {
    override fun extractRow(cols: List<ColumnInResultRow>,resultSet: ResultSet): A = extractValue(cols[0], resultSet) as A
}

class ResultRow2<A, B> : ResultRow<Pair<A, B>>() {
    override fun extractRow(cols: List<ColumnInResultRow>,resultSet: ResultSet): Pair<A, B> = Pair(extractValue(cols[0], resultSet) as A, extractValue(cols[1], resultSet) as B)
}

class ResultRow3<A, B, C> : ResultRow<Triple<A, B, C>>() {
    override fun extractRow(cols: List<ColumnInResultRow>,resultSet: ResultSet): Triple<A, B, C> = Triple(extractValue(cols[0], resultSet) as A, extractValue(cols[1], resultSet) as B, extractValue(cols[2], resultSet) as C)
}

class ResultRow4<A, B, C, D> : ResultRow<Tuple4<A, B, C, D>>() {
    override fun extractRow(cols: List<ColumnInResultRow>,resultSet: ResultSet): Tuple4<A, B, C, D> =
        Tuple4(extractValue(cols[0], resultSet) as A,
            extractValue(cols[1], resultSet) as B,
            extractValue(cols[2], resultSet) as C,
            extractValue(cols[3], resultSet) as D)
}

class ResultRow5<T1, T2, T3, T4, T5> : ResultRow<Tuple5<T1, T2, T3, T4, T5>>() {
    override fun extractRow(cols: List<ColumnInResultRow>,resultSet: ResultSet): Tuple5<T1, T2, T3, T4, T5> =
        Tuple5(extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5)
}


class ResultRow6<T1, T2, T3, T4, T5, T6> : ResultRow<Tuple6<T1, T2, T3, T4, T5, T6>>() {
    override fun extractRow(cols: List<ColumnInResultRow>,resultSet: ResultSet): Tuple6<T1, T2, T3, T4, T5, T6> =
        Tuple6(extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6)
}

class ResultRow7<T1, T2, T3, T4, T5, T6, T7> : ResultRow<Tuple7<T1, T2, T3, T4, T5, T6, T7>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple7<T1, T2, T3, T4, T5, T6, T7> =
        Tuple7(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7
        )
}
class ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8> : ResultRow<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> =
        Tuple8(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8
        )
}
class ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9> : ResultRow<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> =
        Tuple9(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9
        )
}
