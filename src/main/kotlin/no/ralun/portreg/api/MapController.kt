package no.ralun.portreg.api
import no.ralun.portreg.service.PortService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class MapController(private val portService: PortService) {

    @GetMapping("/map")
    fun showMap(): String {
        return "map"
    }

    @PostMapping("/map/click")
    fun handleMapClick(@RequestParam lat: Double, @RequestParam lng: Double): PortRegResponse {
        // Process the coordinates as needed
        println("Latitude: $lat, Longitude: $lng")
        return portService.findClosest(lat,lng)
    }
}