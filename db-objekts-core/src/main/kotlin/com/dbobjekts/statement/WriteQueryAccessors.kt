package com.dbobjekts.statement

import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

class WriteQueryAccessors<U : UpdateBuilderBase, I : InsertBuilderBase>(
    val updater: U,
    val inserter: I){
}
