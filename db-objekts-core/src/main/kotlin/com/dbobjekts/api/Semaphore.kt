package com.dbobjekts.api


class Semaphore {

    private var statement: String? = null

    fun claim(newStatement: String) {
        if (this.statement != null) {
            throw IllegalStateException("You are starting a new $newStatement statement, but there is still a $statement statement in progress that has not been completed yet.")
        }
        this.statement = newStatement
    }

    fun clear() {
        statement = null
    }

    fun assertEmpty() {
        if (this.statement != null) {
            throw IllegalStateException("The last $statement statement was not completed with a call to execute() or select. This is most likely an oversight in your application code.")
        }
    }
}
