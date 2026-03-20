package com.haeni.carrot.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.haeni.carrot"])
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = ["com.haeni.carrot.core"]
)
@EntityScan(
    basePackages = ["com.haeni.carrot.core"]
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}