
let isDarkMode = false;

document.addEventListener('DOMContentLoaded', function () {
    const lightTileLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    });

    const darkTileLayer = L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png', {
        attribution: '&copy; <a href="https://www.carto.com/">CARTO</a>'
    });

    const map = L.map('map', {
        center: [ 65.115, 10.239],
        zoom: 5,
        layers: [lightTileLayer]
    });

    document.getElementById('modeToggle').addEventListener('click', function () {
        if (isDarkMode) {
            map.removeLayer(darkTileLayer);
            map.addLayer(lightTileLayer);
        } else {
            map.removeLayer(lightTileLayer);
            map.addLayer(darkTileLayer);
        }
        isDarkMode = !isDarkMode;
    });

    map.on('click', function (e) {
        const lat = e.latlng.lat;
        const lng = e.latlng.lng;
        console.log("Latitude: " + lat + ", Longitude: " + lng);

        $.ajax({
            url: `/portreg/closestPort?lat=${lat}&lon=${lng}`,
            type: 'GET',
            success: function (response) {
                console.log(response);

                // Remove any existing markers
                if (window.closestPortMarker) {
                    map.removeLayer(window.closestPortMarker);
                }

                // Add a marker for the closest port
                window.closestPortMarker = L.marker([response.position.lat, response.position.lon]).addTo(map)
                    .bindPopup(`<b>${response.name}</b><br>Distance: ${response.distance} km<br>Code: ${response.loCode}`)
                    .openPopup();

                // Center the map on the closest port
                map.setView([response.position.lat, response.position.lon], map.getZoom());
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });
});
