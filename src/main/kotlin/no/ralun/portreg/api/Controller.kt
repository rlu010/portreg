package no.ralun.portreg.api

import no.ralun.portreg.persistence.Port
import no.ralun.portreg.service.PortService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController(value = "api/v1")
class Controller (private val portService: PortService) {


    @GetMapping("/allNorwegianPorts")
    fun getAllPorts():List<Port>? {
        return portService.findAllNorwegianPorts("https://service.unece.org/trade/locode/no.htm")
    }
    @GetMapping("/norwegianPort{locode}")
    fun getPort(@PathVariable locode : String):Port ? {
        return portService.findPort(locode)
    }

    @GetMapping("/closestPort")
    @ResponseBody()
    fun getClosestPort(
            @RequestParam(required = true, defaultValue = "60.3833") lat: Double,
            @RequestParam(required = true, defaultValue = "5.3167") lon: Double,
    ): PortRegResponse{
        return portService.findClosest(lat,lon)
    }

    @GetMapping("/closestPorts")
    @ResponseBody()
    fun getClosestPorts(
        @RequestParam(required = true, defaultValue = "60.3833") lat: Double,
        @RequestParam(required = true, defaultValue = "5.3167") lon: Double,
        @RequestParam(required = true, value = "Number of ports") n: Int
    ): List<PortRegResponse>{
        return portService.findClosest(lat,lon, n)
    }


}