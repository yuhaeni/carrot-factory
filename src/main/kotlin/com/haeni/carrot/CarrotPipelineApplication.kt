package com.haeni.carrot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarrotPipelineApplication

fun main(args: Array<String>) {
	runApplication<CarrotPipelineApplication>(*args)
}
