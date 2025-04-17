const map = L.map('map').setView([33.116862, 135.562120], 6);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const event =  L.layerGroup();
const museum = L.layerGroup();
const urban =   L.layerGroup();
const tower =  L.layerGroup();
const history = L.layerGroup();
const ruins =   L.layerGroup();
const nature =  L.layerGroup();

%s

event.addTo(map);
museum.addTo(map);
urban.addTo(map);
tower.addTo(map);
history.addTo(map);
ruins.addTo(map);
nature.addTo(map);

const overlays = {
    'Events / Festivals': event,
    "Museums / Exhibits": museum,
    "Urban Areas / Stores": urban,
    "Towers": tower,
    "Castles / Historic Sites": history,
    "Ruins / Abandoned Places": ruins,
    "Nature / Hiking / Mountains": nature
};

const layerControl = L.control.layers(null, overlays).addTo(map);