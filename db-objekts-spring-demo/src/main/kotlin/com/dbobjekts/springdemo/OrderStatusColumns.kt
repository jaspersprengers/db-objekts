package com.dbobjekts.springdemo

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.EnumAsStringColumn
import com.dbobjekts.metadata.column.NullableEnumAsStringColumn

enum class OrderStatus(val desc: String) {
    CANCELLED("Cancelled"),
    ON_HOLD("On Hold"),
    SHIPPED("Shipped"),
    DISPUT("Disputed");

    companion object {
        fun parse(desc: String): OrderStatus = OrderStatus.values().find { it.desc == desc }?:
        throw IllegalArgumentException("Not a valid order status: $desc")
    }
}

class OrderStatusColumn(table: AnyTable, name: String) : EnumAsStringColumn<OrderStatus>(table, name, OrderStatus::class.java) {
    override val nullable = NullableOrderStatusColumn(table, name)
    override fun toEnum(name: String): OrderStatus = OrderStatus.parse(name)
}


class NullableOrderStatusColumn(table: AnyTable, name: String) :
    NullableEnumAsStringColumn<OrderStatus>(table, name, OrderStatus::class.java) {
    override fun toEnum(name: String): OrderStatus = OrderStatus.parse(name)
}
