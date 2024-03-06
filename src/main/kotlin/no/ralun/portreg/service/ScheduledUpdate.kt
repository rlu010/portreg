package no.ralun.portreg.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledUpdate(private val portService: PortService) {

    @Scheduled(cron = "0 0 10 * * ?") // Every day at 10 AM
    fun synchronizePorts(){
        portService.findAllNorwegianPorts()
    }
}