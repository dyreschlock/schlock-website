const map = L.map('map');

const baseMapSet = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    minZoom: 4,
    maxZoom: 18,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
});
baseMapSet.addTo(map);

const japanModernset = L.tileLayer("https://cyberjapandata.gsi.go.jp/xyz/seamlessphoto/{z}/{x}/{y}.jpg", {
    minZoom: 4,
    maxZoom: 18,
    attribution: "<a href='https://www.gsi.go.jp/kikakuchousei/kikakuchousei40182.html' target='_blank'>国土地理院</a>"
});

const japan2007set = L.tileLayer("https://cyberjapandata.gsi.go.jp/xyz/ort/{z}/{x}/{y}.jpg", {
    minZoom: 10,
    maxZoom: 17,
    attribution: "<a href='https://www.gsi.go.jp/kikakuchousei/kikakuchousei40182.html' target='_blank'>国土地理院</a>"
});

const japan1974set = L.tileLayer("https://cyberjapandata.gsi.go.jp/xyz/gazo1/{z}/{x}/{y}.jpg", {
    minZoom: 10,
    maxZoom: 17,
    attribution: "<a href='https://www.gsi.go.jp/kikakuchousei/kikakuchousei40182.html' target='_blank'>国土地理院</a>"
});


const mapsets = {
    'Standard': baseMapSet,
    'Modern Japan Imagery' : japanModernset,
    'Japan Imagery (2007)' : japan2007set,
    'Japan Imagery (1974)' : japan1974set
};


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

const markers = new Map();

%s

%s

const layerControl = L.control.layers(mapsets, overlays).addTo(map);


function focusJapan(e)    { map.setView([33.116862, 135.562120], 6); }
function focusHonshu(e)   { map.setView([34.651848,134.8393581], 7); }
function focusGifu(e)     { map.setView([36.2013562,137.3277502], 10); }
function focusTokyo(e)    { map.setView([35.6929124,139.6799312], 12); }
function focusNagasaki(e) { map.setView([32.7834387,129.8885672], 13); }
function focusOkinawa(e)  { map.setView([26.458202,127.9956607], 10); }

document.getElementById("buttonJapan").addEventListener("click", focusJapan);
document.getElementById("buttonHonshu").addEventListener("click", focusHonshu);
document.getElementById("buttonGifu").addEventListener("click", focusGifu);
document.getElementById("buttonTokyo").addEventListener("click", focusTokyo);
document.getElementById("buttonNagasaki").addEventListener("click", focusNagasaki);
document.getElementById("buttonOkinawa").addEventListener("click", focusOkinawa);


const urlParams = window.location.search;
if(urlParams == "")
{
    map.setView([34.651848,134.8393581], 7);
}
else
{
    const mark = markers.get(urlParams.substring(1));

    map.setView(mark.getLatLng(), 12);
    
    mark.openPopup();
}


