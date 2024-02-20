package no.ralun.portreg.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PortRepository: JpaRepository<Port, String> {

    @Query(nativeQuery = true,
            value = """SELECT *, earth_distance(
                        ll_to_earth(lat, lon),
                        ll_to_earth(?1, ?2)
                    ) as distance
                    FROM portreg.port
                    ORDER BY distance ASC
                    LIMIT 1
            """)
    fun findClosest(lat: Double, lon: Double): Port
}