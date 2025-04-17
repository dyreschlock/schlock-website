const map = L.map('map').setView([33.116862, 135.562120], 6);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

%s

%s

const layerControl = L.control.layers(null, overlays).addTo(map);