package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn


class LeftJoin(leftPart: AnyColumn, rightPart: AnyColumn) : JoinBase(leftPart, rightPart) {
    override val keyWord = "left join"
}

class RightJoin(leftPart: AnyColumn, rightPart: AnyColumn) : JoinBase(leftPart, rightPart) {
    override val keyWord = "right join"
}

class InnerJoin(leftPart: AnyColumn, rightPart: AnyColumn) : JoinBase(leftPart, rightPart) {
    override val keyWord = "join"
}
