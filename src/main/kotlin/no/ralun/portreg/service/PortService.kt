package no.ralun.portreg.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class PortService {
    private final val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024) // 16 MB
            }.build()

    private val webClient: WebClient = WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build()

    fun findAllNorwegianPorts(url: String): List<Map<String, String>> {
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
                .filter { row -> row["Function"]?.contains("1") == true }

        return tableData
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

                    //firstTable?.outerHtml() ?: "No table found in the document"
                }
    }
}