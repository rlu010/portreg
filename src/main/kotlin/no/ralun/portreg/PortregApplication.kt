package no.ralun.portreg

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PortregApplication

fun main(args: Array<String>) {
    runApplication<PortregApplication>(*args)
}
