package com.dbobjekts.springdemo

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.AggregateType
import com.dbobjekts.metadata.column.EnumAsStringColumn
import com.dbobjekts.metadata.column.NullableEnumAsStringColumn

enum class OrderStatus() {
    Cancelled,
    Resolved,
    `In Process`,
    `On Hold`,
    Shipped,
    Disputed;

}

class OrderStatusColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) : EnumAsStringColumn<OrderStatus>(table, name, OrderStatus::class.java, aggregateType) {
    override val nullable = NullableOrderStatusColumn(table, name, aggregateType)
    //override //override fun distinct(): OrderStatusColumn = OrderStatusColumn(table, nameInTable, AggregateType.DISTINCT)
    override fun toEnum(value: String): OrderStatus = OrderStatus.valueOf(value)
}


class NullableOrderStatusColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) :
    NullableEnumAsStringColumn<OrderStatus>(table, name, OrderStatus::class.java, aggregateType) {
    //override //override fun distinct(): NullableOrderStatusColumn = NullableOrderStatusColumn(table, nameInTable, AggregateType.DISTINCT)
    override fun toEnum(value: String): OrderStatus = OrderStatus.valueOf(value)
}
