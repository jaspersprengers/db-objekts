package com.dbobjekts.testdb

import java.io.Serializable


data class Address(val street: String, val town: String) : Serializable{
    override fun toString(): String = "$street|$town"

    companion object {
        fun parse(str: String): Address {
            val parts = str.split("|")
            if (parts.size != 2)
                throw IllegalStateException("$str cannot be parsed to valid address. Must be 'street|town'")
            return Address(parts[0], parts[1])
        }
    }
}
