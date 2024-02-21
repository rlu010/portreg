package no.ralun.portreg.util

import no.ralun.portreg.api.PortRegResponse
import no.ralun.portreg.api.Position
import no.ralun.portreg.persistence.Port
import java.util.*
import kotlin.math.*

fun mapToEntities(mapList: List<Map<String,String>>): List<Port>{
        return mapList.map { e -> mapToEntity(e) }
    }

    fun mapToEntity(portMap: Map<String,String>): Port{
        val loCode = portMap["LOCODE"] ?: "NOCODE"
        val name = portMap["Name"] ?: "NONAME"
        val coords = portMap["Coordinates"]?.split(" ")
        val lat = convertToDecimal(coords?.get(0) ?: "0000N")
        val lon = convertToDecimal(coords?.get(1) ?: "0000N")

        val tull = Port()
        tull.name = name
        tull.locode = loCode.replace("\\s+".toRegex(), "")
        tull.latitude = lat
        tull.longitude = lon

        return tull
    }

fun mapToPortRegResponse(portEntites: List<Port>, lat: Double, lon: Double): List<PortRegResponse>{
    val portResponses = portEntites.map { port -> mapToPortRegResponse(port,lat, lon) }

    return portResponses
}

fun mapToPortRegResponse(portEntity: Port, lat: Double, lon: Double): PortRegResponse{
    val distance = calculateDistance(portEntity.latitude, portEntity.longitude, lat, lon)
    val position = Position(portEntity.latitude, portEntity.longitude)

    return PortRegResponse(portEntity.locode, portEntity.name, distance, position)
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

private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 3440.0 // Earth radius in nautical miles

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    val distance = earthRadius * c // Distance in nautical miles
    return String.format(Locale.US,"%.1f", distance).toDouble()
}

