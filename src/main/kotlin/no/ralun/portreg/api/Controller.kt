package no.ralun.portreg.api

import no.ralun.portreg.persistence.Port
import no.ralun.portreg.service.PortService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller (private val portService: PortService) {


    @GetMapping("/allNorwegianPorts")
    fun test():List<Port>? {
        return portService.findAllNorwegianPorts("https://service.unece.org/trade/locode/no.htm")
    }
}