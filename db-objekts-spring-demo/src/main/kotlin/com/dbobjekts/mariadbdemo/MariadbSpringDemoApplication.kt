package com.dbobjekts.mariadbdemo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MariadbSpringDemoApplication {

    fun main(args: Array<String>) {
        runApplication<MariadbSpringDemoApplication>(*args) {
            setBannerMode(Banner.Mode.OFF)
        }
    }
}
