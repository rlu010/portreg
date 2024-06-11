package no.ralun.portreg.service

import no.ralun.portreg.persistence.PortSyncLogRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Component
class ScheduledUpdate(private val portService: PortService,
                      private val portSyncLogRepository: PortSyncLogRepository) {

    //@Scheduled(cron = "0 0 10 * * ?") // Every day at 10 AM
    @Scheduled(cron = "*/10 * * * * *") // every 10 sec
    fun synchronizePorts(){
        val portsToSync = portSyncLogRepository.findAll()

        if (portsToSync.isNotEmpty() && portsToSync[0].synchronizationDate?.isBefore(getJuly31stInstant()) == true) {
            portService.deleteAllPorts()

            portsToSync.forEach{e -> resynchronizePortsForCountry(e.alpha2CountryCode)}
        }

    }

    private fun resynchronizePortsForCountry(alpha2Code: String?) {
        if (alpha2Code != null) {
            println("updating for $alpha2Code")
            portService.addPortsForCountry(alpha2Code.lowercase())
            val portSyncLog = portSyncLogRepository.findById(alpha2Code).get()
            portSyncLog.synchronizationDate = Instant.now()
            portSyncLogRepository.save(portSyncLog)
        }
    }

    fun getJuly31stInstant(): Instant {
        val currentYear = LocalDate.now().year
        val july31st = LocalDate.of(currentYear, 7, 31)
        return july31st.atStartOfDay(ZoneId.systemDefault()).toInstant()
    }
}