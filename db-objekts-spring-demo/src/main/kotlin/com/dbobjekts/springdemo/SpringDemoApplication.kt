package com.dbobjekts.springdemo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringDemoApplication {

    fun main(args: Array<String>) {
        runApplication<SpringDemoApplication>(*args) {
            setBannerMode(Banner.Mode.OFF)
        }
    }
}
