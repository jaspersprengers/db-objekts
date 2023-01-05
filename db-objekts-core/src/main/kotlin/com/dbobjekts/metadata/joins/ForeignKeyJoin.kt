package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.column.IsForeignKey

internal class ForeignKeyJoin(val parentColumn: AnyColumn,
                     val foreignKey: IsForeignKey<*, *>
) {

  override fun toString(): String = "joining ${parentColumn.aliasDotName()} on ${foreignKey}"

}
