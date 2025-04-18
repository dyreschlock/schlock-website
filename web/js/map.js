const map = L.map('map').setView([33.116862, 135.562120], 6);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    minZoom: 4,
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

var LeafIcon = L.Icon.extend({
    options: {
        shadowUrl: '%s',
        iconSize:     [25, 41],
        shadowSize:   [41, 41],
        iconAnchor:   [12, 41],
        shadowAnchor: [12, 41],
        popupAnchor:  [1, -39]
    }
});

%s

%s

const layerControl = L.control.layers(null, overlays).addTo(map);