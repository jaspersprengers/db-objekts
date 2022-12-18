package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyForeignKey

class ForeignKeyJoin(val parentColumn: AnyColumn,
                     val foreignKey: AnyForeignKey) {

  override fun toString(): String = "joining ${parentColumn.aliasDotName()} on ${foreignKey}"

}
