package no.ralun.portreg.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "port", schema = "portreg")
class Port {

    @Id
    @Column(name = "LOCODE")
    lateinit var locode: String;

    @Column(name = "NAME")
    lateinit var name: String;

    @Column(name = "LAT")
    var latitude: Double = 0.0

    @Column(name = "LON")
    var longitude: Double = 0.0
}