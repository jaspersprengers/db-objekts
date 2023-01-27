package com.dbobjekts.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
class ObjectNameValidatorTest {

    @Test
    fun `test permissible words`() {
        assertTrue(ObjectNameValidator.validate("name"))
        assertTrue(ObjectNameValidator.validate("name1"))
    }

    @Test
    fun `test reserved words`() {
        assertFalse(ObjectNameValidator.validate("fun"))
        assertFalse(ObjectNameValidator.validate("package"))
        val t: Number = 5
    }

}
