package com.dbobjekts.api


class Semaphore {

    private var statement: String? = null

    init {
        println("Created sempahore " + this.hashCode())
    }

    fun claim(newStatement: String) {
        if (this.statement != null) {
            throw IllegalStateException("You are starting a new $newStatement statement, but there is still a $statement in progress that has not been completed yet.")
        }
        this.statement = newStatement
    }

    fun clear() {
        statement = null
    }

    fun assertEmpty() {
        if (this.statement != null) {
            throw IllegalStateException("There is still a $statement in progress that has not been completed yet.")
        }
    }
}
