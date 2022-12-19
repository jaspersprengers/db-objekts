package com.dbobjekts.api

import java.io.Serializable

data class Tuple2<out T1, out T2>(
    val v1: T1,
    val v2: T2
) : Serializable {

    override fun toString(): String = "($v1, $v2)"
}

data class Tuple3<out T1, out T2, out T3>(
    val v1: T1,
    val v2: T2,
    val v3: T3
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3)"
}

data class Tuple4<out T1, out T2, out T3, out T4>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4)"
}

data class Tuple5<out T1, out T2, out T3, out T4, out T5>(
    val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5)"
}


data class Tuple6<out T1, out T2, out T3, out T4, out T5, out T6>(
    val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6)"
}


data class Tuple7<out T1, out T2, out T3, out T4, out T5, out T6, out T7>(
    val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7)"
}


data class Tuple8<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8>(
    val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7, val v8: T8
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8)"
}


data class Tuple9<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9>(
    val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7, val v8: T8, val v9: T9
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9)"
}


data class Tuple10<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10)"
}


data class Tuple11<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11)"
}


data class Tuple12<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12)"
}


data class Tuple13<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13)"
}


data class Tuple14<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14
) : Serializable {

    override fun toString(): String = "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14)"
}


data class Tuple15<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15)"
}


data class Tuple16<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16)"
}


data class Tuple17<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16, out T17>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16,
    val v17: T17
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16, $v17)"
}


data class Tuple18<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16, out T17, out T18>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16,
    val v17: T17,
    val v18: T18
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16, $v17, $v18)"
}


data class Tuple19<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16, out T17, out T18, out T19>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16,
    val v17: T17,
    val v18: T18,
    val v19: T19
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16, $v17, $v18, $v19)"
}


data class Tuple20<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16, out T17, out T18, out T19, out T20>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16,
    val v17: T17,
    val v18: T18,
    val v19: T19,
    val v20: T20
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16, $v17, $v18, $v19, $v20)"
}


data class Tuple21<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16, out T17, out T18, out T19, out T20, out T21>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16,
    val v17: T17,
    val v18: T18,
    val v19: T19,
    val v20: T20,
    val v21: T21
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16, $v17, $v18, $v19, $v20, $v21)"
}


data class Tuple22<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10, out T11, out T12, out T13, out T14, out T15, out T16, out T17, out T18, out T19, out T20, out T21, out T22>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val v10: T10,
    val v11: T11,
    val v12: T12,
    val v13: T13,
    val v14: T14,
    val v15: T15,
    val v16: T16,
    val v17: T17,
    val v18: T18,
    val v19: T19,
    val v20: T20,
    val v21: T21,
    val v22: T22
) : Serializable {

    override fun toString(): String =
        "($v1, $v2, $v3, $v4, $v5, $v6, $v7, $v8, $v9, $v10, $v11, $v12, $v13, $v14, $v15, $v16, $v17, $v18, $v19, $v20, $v21, $v22)"
}
