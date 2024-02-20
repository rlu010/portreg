package no.ralun.portreg.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PortRepository: JpaRepository<Port, String> {

}