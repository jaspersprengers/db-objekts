package com.dbobjekts.jdbc

import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache
import com.google.common.util.concurrent.UncheckedExecutionException
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit

class TransactionCache(factory: TransactionFactory,
                       minimumExpiryMillis: Long = 1000) {

    private val guava: LoadingCache<Long, Transaction> = CacheBuilder.newBuilder()
        .expireAfterWrite(Math.max(minimumExpiryMillis, factory.cacheExpiryMillis),
            TimeUnit.MILLISECONDS)
        .build(factory)

    fun get(): Transaction {
        val id = idForCurrentThread()
        try {
            val tr = guava.get(id)
            return if (!tr.isValid()) {
                evict(id)
                guava.get(id).also {
                    if (!it.isValid())
                        throw IllegalStateException("Could not obtain valid Transaction")
                }
            } else
                tr
        } catch (e: UncheckedExecutionException) {
            evict(id)
            throw e.cause ?: e
        } catch (e: Throwable) {
            evict(id)
            throw e
        }
    }

    fun getIfExists(): Transaction? {
        val id = idForCurrentThread()
        val tr = guava.getIfPresent(id)
        return if (tr == null || !tr.isValid()) {
            evict(id)
            null
        } else
            tr
    }

     fun evict(id: Long = idForCurrentThread()) {
        guava.invalidate(id)
    }

    private fun idForCurrentThread(): Long = Thread.currentThread().getId()

}


