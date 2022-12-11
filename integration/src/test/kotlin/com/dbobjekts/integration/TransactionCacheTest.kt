package com.dbobjekts.integration

import com.dbobjekts.jdbc.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.kotlin.*
import java.lang.IllegalStateException

class TransactionCacheTest {

    val factory = mock<TransactionManager>()
    val tr1 = mock<Transaction>()
    val tr2 = mock<Transaction>()
    val settings = TransactionSettings()


    @BeforeEach
    fun beforeEachTest() {
        Mockito.reset(factory, tr1, tr2)
    }

    @Test
    fun `Consecutive get requests on valid transaction in same thread return same transaction object`() {
        whenever(tr1.isValid()).thenReturn(true)
        whenever(factory.load(any())).thenReturn(tr1)
        val cache = TransactionCache(factory, settings)
        val cached1 = cache.get()
        val cached2 = cache.get()
        assertThat(cached1).isSameAs(cached2)
        verify(factory).load(any())
        assertThat(cache.getIfExists()).isNotNull()
    }

    @Test
    fun `Consecutive gets in same thread with over expiry time returns different objects`() {
        val settings = TransactionSettings(transactionCacheExpireMillis = 200)

        whenever(tr1.isValid()).thenReturn(true)
        whenever(tr2.isValid()).thenReturn(true)
        whenever(factory.load(any())).thenReturn(tr1, tr2)
        val cache = TransactionCache(factory, settings)
        val cached1 = cache.get()
        Thread.sleep(1300)
        val cached2 = cache.get()
        assertThat(cached1).isNotSameAs(cached2)
        verify(factory, times(2)).load(any())
        assertThat(cache.getIfExists()).isNotNull
    }

    @Test
    fun `Single get when first transaction is invalid`() {
        whenever(tr1.isValid()).thenReturn(false)
        whenever(tr2.isValid()).thenReturn(true)
        whenever(factory.load(any())).thenReturn(tr1, tr2)
        val cache = TransactionCache(factory, settings)
        val cached1 = cache.get()
        assertThat(cached1).isNotNull
        verify(factory, times(2)).load(any())
        assertThat(cache.getIfExists()).isNotNull
    }

    @Test
    fun `Single get on invalid transaction clears cache`() {
        whenever(tr1.isValid()).thenReturn(false)
        whenever(factory.load(any())).thenReturn(tr1)
        val cache = TransactionCache(factory, settings)
        Assertions.assertThrows(IllegalStateException::class.java) { cache.get() }
        assertThat(cache.getIfExists()).isNull()
    }

}

