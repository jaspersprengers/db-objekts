package com.dbobjekts.integration

import com.dbobjekts.jdbc.Transaction
import com.dbobjekts.jdbc.TransactionCache
import com.dbobjekts.jdbc.TransactionFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.kotlin.*
import java.lang.IllegalStateException

class TransactionCacheTest {

    val factory = mock<TransactionFactory>()
    val tr1 = mock<Transaction>()
    val tr2 = mock<Transaction>()

    @BeforeEach
    fun beforeEachTest() {
        Mockito.reset(factory, tr1, tr2)
    }

    @Test
    fun `Consecutive get requests on valid transaction in same thread return same transaction object`() {
        whenever(tr1.isValid()).thenReturn(true)
        whenever(factory.load(any())).thenReturn(tr1)
        val cache = TransactionCache(factory)
        val cached1 = cache.get()
        val cached2 = cache.get()
        assertEquals(cached1, cached2)
        verify(factory).load(any())
        assertNotNull(cache.getIfExists())
    }

    @Test
    fun `Consecutive gets in same thread with over expiry time returns different objects`() {
        whenever(factory.cacheExpiryMillis).thenReturn(200)
        whenever(tr1.isValid()).thenReturn(true)
        whenever(tr2.isValid()).thenReturn(true)
        whenever(factory.load(any())).thenReturn(tr1, tr2)
        val cache = TransactionCache(factory, 200)
        val cached1 = cache.get()
        Thread.sleep(300)
        val cached2 = cache.get()
        assertNotEquals(cached1, cached2)
        verify(factory, times(2)).load(any())
        assertNotNull(cache.getIfExists())
    }

    @Test
    fun `Single get when first transaction is invalid`() {
        whenever(tr1.isValid()).thenReturn(false)
        whenever(tr2.isValid()).thenReturn(true)
        whenever(factory.load(any())).thenReturn(tr1, tr2)
        val cache = TransactionCache(factory)
        val cached1 = cache.get()
        assertNotNull(cached1)
        verify(factory, times(2)).load(any())
        assertNotNull(cache.getIfExists())
    }

    @Test
    fun `Single get on invalid transaction clears cache`() {
        whenever(tr1.isValid()).thenReturn(false)
        whenever(factory.load(any())).thenReturn(tr1)
        val cache = TransactionCache(factory)
        Assertions.assertThrows(IllegalStateException::class.java) { cache.get() }
        assertNull(cache.getIfExists())
    }

}

