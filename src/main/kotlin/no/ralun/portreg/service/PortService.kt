package no.ralun.portreg.service

import no.ralun.portreg.api.PortRegResponse
import no.ralun.portreg.persistence.Port
import no.ralun.portreg.persistence.PortRepository
import no.ralun.portreg.util.mapToEntities
import no.ralun.portreg.util.mapToPortRegResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class PortService(private val portRepository: PortRepository) {
    private final val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) // 16 MB
            }.build()

    private val webClient: WebClient = WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build()

    fun findPort(loCode : String): Port {
        return portRepository.findById(loCode).get()
    }

    fun findClosest(lat: Double, lon: Double): PortRegResponse{
        val nearestPort =portRepository.findClosest(lat, lon, 1).first()
        return mapToPortRegResponse(nearestPort, lat, lon)
    }

    fun findClosest(lat: Double, lon: Double, n: Int): List<PortRegResponse>{
        val nearestPorts = portRepository.findClosest(lat, lon, n)
        return mapToPortRegResponse(nearestPorts,lat,lon)
    }

    fun findAllNorwegianPorts(url: String): List<Port> {
        val allStoredPorts = portRepository.findAll()

        if (allStoredPorts.isNotEmpty()){
            return allStoredPorts
        }

        val table = fetchDatatableContent(url).block()?.select("tbody")?.first()
                ?: throw IllegalStateException("No data retrieved for UNECE webpage!")

        val rows = table.select("tbody").first()?.select("tr")
        val headerValues = retrieveHeaders(rows)

        rows!!.drop(0)

        val tableData = rows.map { row ->
            val rowVals = row.select("td")

            headerValues.mapIndexed { index, header ->
                header to (rowVals.getOrNull(index)?.text() ?: "")
            }.toMap()
        }
                .filter {
                    row -> row["Function"]?.contains("1") == true && row["Coordinates"]?.isNotBlank() ?: false }

        val allPorts = mapToEntities(tableData)
        portRepository.saveAll(allPorts)

        return allPorts
    }

    fun retrieveHeaders(rows: Elements?): List<String> {
        val headers = rows?.first()?.select("td") ?: throw IllegalStateException("Headers could not be retrieved")
        return headers.map { it.text() }

    }

    fun fetchDatatableContent(url: String): Mono<Element> {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String::class.java)
                .map { responseBody ->
                    val document = Jsoup.parse(responseBody)
                    document.select("table")[2]
                }
    }
}