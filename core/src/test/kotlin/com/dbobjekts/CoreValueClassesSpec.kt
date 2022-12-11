package com.dbobjekts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class CoreValueClassesSpec {


    @Test
    fun `Create three-part package`() {
        val pkg = PackageName("com.acme.tools")
        assertEquals("com.acme.tools", pkg.toString())
    }

    @Test
    fun `Create package without dot`() {
        val pkg = PackageName("com")
        assertEquals("com", pkg.toString())
    }

    @Test
    fun `Create sub package`() {
        val pkg = PackageName("com.acme")
        assertEquals("com.acme.tools", pkg.createSubPackageForSchema(SchemaName("TOOLS")).toString())
    }

    @Test
    fun concat() {
        val pkg = PackageName("com.acme")
        assertEquals("com.acme.tools", pkg.concat("tools").toString())
    }

    @Test
    fun `illegal characters`() {
        Assertions.assertThatThrownBy { PackageName("hello world.again") }.hasMessage("hello world is not a valid package element.")
        Assertions.assertThatThrownBy { PackageName("a.!.b^") }.hasMessage("! is not a valid package element.")
    }

    @Test
    fun `to file path`() {
        assertEquals("com/acme/tools", PackageName("com.acme.tools").asFilePath())
    }
}

