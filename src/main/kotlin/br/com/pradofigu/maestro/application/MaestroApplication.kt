package br.com.pradofigu.maestro.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan(basePackages = ["br.com.pradofigu.maestro"])
@EntityScan(basePackages = ["br.com.pradofigu.maestro.persistence"])
@EnableJpaRepositories(basePackages = ["br.com.pradofigu.maestro.persistence"])
class MaestroApplication

fun main(args: Array<String>) {
    runApplication<MaestroApplication>(*args)
}
