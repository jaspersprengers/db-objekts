@file:Suppress("UNCHECKED_CAST")

package com.dbobjekts.api

import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.ColumnInResultRow
import java.sql.ResultSet


class ResultRow1<A> : ResultRow<A>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): A = extractValue(cols[0], resultSet) as A
}

/*class ResultRow2<T1, T2> : ResultRow<Tuple2<T1, T2>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple2<T1, T2> =
        Tuple2(extractValue(cols[0], resultSet) as T1, extractValue(cols[1], resultSet) as T2)
}*/

class ResultRow2<T1, T2> : ResultRow<Tuple2<T1, T2>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple2<T1, T2> {
        val values = extractRow_2(cols, resultSet)
        return Tuple2(values[0] as T1, values[1] as T2)
    }
}


class ResultRow3<T1, T2, T3> : ResultRow<Tuple3<T1, T2, T3>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple3<T1, T2, T3> =
        Tuple3(extractValue(cols[0], resultSet) as T1, extractValue(cols[1], resultSet) as T2, extractValue(cols[2], resultSet) as T3)
}

class ResultRow4<T1, T2, T3, T4> : ResultRow<Tuple4<T1, T2, T3, T4>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple4<T1, T2, T3, T4> =
        Tuple4(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4
        )
}

class ResultRow5<T1, T2, T3, T4, T5> : ResultRow<Tuple5<T1, T2, T3, T4, T5>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple5<T1, T2, T3, T4, T5> =
        Tuple5(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5
        )
}

class ResultRow6<T1, T2, T3, T4, T5, T6> : ResultRow<Tuple6<T1, T2, T3, T4, T5, T6>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple6<T1, T2, T3, T4, T5, T6> =
        Tuple6(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6
        )
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

class ResultRow10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> : ResultRow<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> =
        Tuple10(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10
        )
}

class ResultRow11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> : ResultRow<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>() {
    override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> =
        Tuple11(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11
        )
}

class ResultRow12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> :
    ResultRow<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> =
        Tuple12(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12
        )
}

class ResultRow13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> :
    ResultRow<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> =
        Tuple13(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13
        )
}

class ResultRow14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> :
    ResultRow<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> =
        Tuple14(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14
        )
}

class ResultRow15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> :
    ResultRow<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> =
        Tuple15(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15
        )
}

class ResultRow16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> :
    ResultRow<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> =
        Tuple16(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16
        )
}

class ResultRow17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> :
    ResultRow<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> =
        Tuple17(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16,
            extractValue(cols[16], resultSet) as T17
        )
}

class ResultRow18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> :
    ResultRow<Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> =
        Tuple18(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16,
            extractValue(cols[16], resultSet) as T17,
            extractValue(cols[17], resultSet) as T18
        )
}

class ResultRow19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> :
    ResultRow<Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> =
        Tuple19(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16,
            extractValue(cols[16], resultSet) as T17,
            extractValue(cols[17], resultSet) as T18,
            extractValue(cols[18], resultSet) as T19
        )
}

class ResultRow20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> :
    ResultRow<Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> =
        Tuple20(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16,
            extractValue(cols[16], resultSet) as T17,
            extractValue(cols[17], resultSet) as T18,
            extractValue(cols[18], resultSet) as T19,
            extractValue(cols[19], resultSet) as T20
        )
}

class ResultRow21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> :
    ResultRow<Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> =
        Tuple21(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16,
            extractValue(cols[16], resultSet) as T17,
            extractValue(cols[17], resultSet) as T18,
            extractValue(cols[18], resultSet) as T19,
            extractValue(cols[19], resultSet) as T20,
            extractValue(cols[20], resultSet) as T21
        )
}

class ResultRow22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> :
    ResultRow<Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>() {
    override fun extractRow(
        cols: List<ColumnInResultRow>,
        resultSet: ResultSet
    ): Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> =
        Tuple22(
            extractValue(cols[0], resultSet) as T1,
            extractValue(cols[1], resultSet) as T2,
            extractValue(cols[2], resultSet) as T3,
            extractValue(cols[3], resultSet) as T4,
            extractValue(cols[4], resultSet) as T5,
            extractValue(cols[5], resultSet) as T6,
            extractValue(cols[6], resultSet) as T7,
            extractValue(cols[7], resultSet) as T8,
            extractValue(cols[8], resultSet) as T9,
            extractValue(cols[9], resultSet) as T10,
            extractValue(cols[10], resultSet) as T11,
            extractValue(cols[11], resultSet) as T12,
            extractValue(cols[12], resultSet) as T13,
            extractValue(cols[13], resultSet) as T14,
            extractValue(cols[14], resultSet) as T15,
            extractValue(cols[15], resultSet) as T16,
            extractValue(cols[16], resultSet) as T17,
            extractValue(cols[17], resultSet) as T18,
            extractValue(cols[18], resultSet) as T19,
            extractValue(cols[19], resultSet) as T20,
            extractValue(cols[20], resultSet) as T21,
            extractValue(cols[21], resultSet) as T22
        )
}
