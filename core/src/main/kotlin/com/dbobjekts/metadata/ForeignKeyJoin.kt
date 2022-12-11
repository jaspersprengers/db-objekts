package com.dbobjekts.metadata

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyForeignKey

class ForeignKeyJoin(val parentColumn: AnyColumn,
                     val foreignKey: AnyForeignKey) {

  override fun toString(): String = "joining ${parentColumn.aliasDotName()} on ${foreignKey}"

}
