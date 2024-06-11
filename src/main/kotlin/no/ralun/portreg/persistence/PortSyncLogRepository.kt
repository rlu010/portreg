package no.ralun.portreg.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PortSyncLogRepository: JpaRepository<PortSyncLog, String> {
}