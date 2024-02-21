package no.ralun.portreg.api

data class PortRegResponse(
        val loCode: String,
        val name: String,
        val distance: Double,
        val position: Position
)
