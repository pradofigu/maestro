package br.com.pradofigu.maestro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["br.com.pradofigu.maestro"])
@SpringBootApplication
class MaestroApplication

fun main(args: Array<String>) {
    runApplication<MaestroApplication>(*args)
}
