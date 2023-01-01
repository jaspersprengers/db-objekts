package com.dbobjekts.api

import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


abstract class TableRowData<U : UpdateBuilderBase, I : InsertBuilderBase>(
    internal val writeAccessors: WriteQueryAccessors<U, I>
) {

}
