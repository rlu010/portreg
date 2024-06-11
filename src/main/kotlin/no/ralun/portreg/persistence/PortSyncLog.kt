package no.ralun.portreg.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "port_sync_log", schema = "portreg")
open class PortSyncLog() {

    constructor(alpha2CountryCode: String?, outcome: String?, synchronizationDate: Instant?) : this() {
        this.alpha2CountryCode = alpha2CountryCode
        this.outcome = outcome
        this.synchronizationDate = synchronizationDate
    }

    @Id
    @Size(max = 2)
    @Column(name = "alpha2_country_code", nullable = false, length = 2)
    open var alpha2CountryCode: String? = null

    @Size(max = 7)
    @NotNull
    @Column(name = "outcome", nullable = false, length = 7)
    open var outcome: String? = null

    @NotNull
    @Column(name = "synchronization_date", nullable = false)
    open var synchronizationDate: Instant? = null
}