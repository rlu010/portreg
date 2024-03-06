package no.ralun.portreg.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "synchronizationState", schema = "portreg")
class SynchronizationState {
    @Column(name = "NAME")
    lateinit var outcome: String;

    @Column(name = "SYNCHRONIZATION_TIME")
    lateinit var syncTime: LocalDate;
}