package com.dbobjekts.fixture

import com.dbobjekts.metadata.Schema
import com.dbobjekts.metadata.Table


class TestSchema(tables: List<Table>) : Schema("test", tables)
