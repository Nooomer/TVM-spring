package com.nooomer.tvmspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableCaching
@SpringBootApplication(scanBasePackages = ["com.nooomer.tvmspring"])
class TvmSpringApplication

fun main(args: Array<String>) {
    runApplication<TvmSpringApplication>(*args)
}
