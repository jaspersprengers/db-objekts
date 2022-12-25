package com.dbobjekts.util

import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.vendors.h2.H2DataTypeMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringUtilTest {

    // non empty
    @Test
    fun `null returns null`() {
        assert(StringUtil.nonEmpty(null) == null)
    }

    @Test
    fun `five returns five`() {
        assert(StringUtil.nonEmpty("five") == "five")
    }

    @Test
    fun `blank returns null`() {
        assert(StringUtil.nonEmpty("") == null)
    }


    // init lower casing"
    @Test
    fun `Hello returns hello`() {
        assert(StringUtil.initLowerCase("Hello") == "hello")
    }

    @Test
    fun `hello returns hello`() {
        assert(StringUtil.initLowerCase("hello") == "hello")
    }

    @Test
    fun `init lower case with empty string returns blank`() {
        assert(StringUtil.initLowerCase("") == "")
    }


    //init upper casing") {
    @Test
    fun `hello returns Hello`() {
        assert(StringUtil.initUpperCase("hello") == "Hello")
    }

    @Test
    fun `Hello returns Hello`() {
        assert(StringUtil.initUpperCase("Hello") == "Hello")
    }

    @Test
    fun `blank returns blank`() {
        assert(StringUtil.initLowerCase("") == "")
    }


    //leading space") {
    @Test
    fun `blank return one space`() {
        assert(StringUtil.ensureLeadingSpace("") == " ")
    }

    @Test
    fun `hello return space-hello`() {
        assert(StringUtil.ensureLeadingSpace("hello") == " hello")
    }

    @Test
    fun `space-hello return space-hello`() {
        assert(StringUtil.ensureLeadingSpace(" hello") == " hello")
    }


    //concatenate with spaces") {
    @Test
    fun `empty list return blank`() {
        assert(StringUtil.concat(listOf()) == "")
    }

    @Test
    fun `list of one return element as is`() {
        assert(StringUtil.concat(listOf("one")) == "one")
    }

    @Test
    fun `list of one-two returns 'one two'`() {
        assert(StringUtil.concat(listOf("one", "two")) == "one two")
    }

    @Test
    fun `list of one-two with leading spaces returns 'one two'`() {
        assert(StringUtil.concat(listOf(" one ", " two ")) == "one two")
    }


    //non empty optional"){
    @Test
    fun `to empty optional with null returns null`() {
        assert(StringUtil.toNonEmptyOptional(null) == null)
    }

    @Test
    fun `to nonempty optional with blank returns null`() {
        assert(StringUtil.toNonEmptyOptional("") == null)
    }

    @Test
    fun `Int 5 returns Int 5`() {
        assert(StringUtil.toNonEmptyOptional(5) == 5)
    }


    //Class to string"){
    @Test
    fun `String returns String`() {
        assert(StringUtil.classToString(String::class) == "String")
    }

    @Test
    fun `Kotlin Int returns Int`() {
        assert(StringUtil.classToString(Int::class) == "Int")
    }

    @Test
    fun `java lang Long returns Long`() {
        assert(StringUtil.classToString(java.lang.Long::class) == "Long")
    }

    @Test
    fun `java primitive int returns Int`() {
        assertEquals("Int", StringUtil.classToString(java.lang.Integer.TYPE))
    }

    @Test
    fun `capitalize camel case`() {
        assertEquals("Kameel", StringUtil.capitalCamel("kameel"))
    }

    @Test
    fun `test value classes`(){

        assertEquals("Int", StringUtil.classToString(ColumnFactory.INTEGER.valueClass))
        assertEquals("java.time.LocalDate", StringUtil.classToString(ColumnFactory.DATE.valueClass))

        assertEquals("Array<Any>", StringUtil.classToString(H2DataTypeMapper.OBJECT_ARRAY.valueClass))

        assertEquals("ByteArray", StringUtil.classToString(ColumnFactory.BYTE_ARRAY.valueClass))

    }

}
