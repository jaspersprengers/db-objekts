package com.dbobjekts.vendors.postgresql

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import org.postgresql.util.PGmoney


class MoneyColumn(table: AnyTable, name: String) : ObjectColumn<PGmoney>(table, name, PGmoney::class.java)

class NullableMoneyColumn(table: AnyTable, name: String) : NullableObjectColumn<PGmoney?>(table, name, PGmoney::class.java)
