package com.dbobjekts.codegen

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.configbuilders.ExclusionConfigurer
import com.dbobjekts.api.TableExclusionFilter
import com.dbobjekts.codegen.metadata.DBTableDefinition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class TableExclusionTest {

    val employees = DBTableDefinition(PackageName("hr"), SchemaName("hr"), TableName("employees"), "e", listOf(), listOf())
    val departments = employees.copy(tableName = TableName("departments"))
    val salaries = employees.copy(tableName = TableName("salaries"), schema = SchemaName("finance"))

    val set = ExclusionConfigurer(CodeGenerator())

    @Test
    fun `include all tables`() {
        val (exclude, include) = set.partition((listOf<DBTableDefinition>(employees, departments, salaries)))
        assertThat(include).hasSize(3)
        assertThat(exclude).isEmpty()
    }

    @Test
    fun `Exclude the employees table in hr`() {
        set.addTableFilter(TableExclusionFilter("employees", schema = "hr"))
        val (exclude, include) = set.partition(listOf<DBTableDefinition>(employees, departments, salaries))
        assertThat(include).hasSize(2)
        val names: List<String> = exclude.map { it.tableName.value }
        assertThat(names).containsExactlyInAnyOrder("employees")

    }

}
