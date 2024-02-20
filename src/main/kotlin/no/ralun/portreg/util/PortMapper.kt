package no.ralun.portreg.util

import no.ralun.portreg.persistence.Port
import java.util.*

fun mapToEntities(maplist: List<Map<String,String>>): List<Port>{
        return maplist.map { e -> mapToEntity(e) }
    }

    fun mapToEntity(portMap: Map<String,String>): Port{
        val locode = portMap["LOCODE"] ?: "NOCODE"
        val name = portMap["Name"] ?: "NONAME"
        val coords = portMap["Coordinates"]?.split(" ")
        val lat = convertToDecimal(coords?.get(0) ?: "0000N")
        val lon = convertToDecimal(coords?.get(1)?: "0000N")

        var port = Port()
        port.name = name
        port.locode = locode
        port.latitude = lat
        port.longitude = lon

        return port
    }

private fun convertToDecimal(coordinate: String): Double {
    val isLongitude = coordinate.length == 6

    val degrees = if (isLongitude) coordinate.substring(0, 3).toDouble() else coordinate.substring(0, 2).toDouble()
    val minutesPosition = if (isLongitude) 3 else 2
    val minutes = coordinate.substring(minutesPosition, minutesPosition + 2).toDouble()
    val direction = coordinate.last()

    val decimal = degrees + (minutes / 60)
    val formatted = String.format(Locale.US,"%.4f", decimal)


    return when (direction) {
        'N', 'E' -> formatted.toDouble()
        'S', 'W' -> -formatted.toDouble()
        else -> throw IllegalArgumentException("Invalid direction indicator: $direction")
    }
}

