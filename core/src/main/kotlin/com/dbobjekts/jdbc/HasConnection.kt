package com.dbobjekts.jdbc

 interface HasConnection {
    fun connection(): ConnectionAdapterImpl
}
