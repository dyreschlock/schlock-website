var Refresher = function (){
    var spec;

    function update() {

        Tapestry.ajaxRequest(spec.refreshUrl, {

            parameters : {
                state : spec.currentState
            },
            onSuccess : function(e) {

                var update = e.responseJSON.update;
                if(update)
                {
                    spec.currentState = e.responseJSON.currentState;

                    var zoneId = spec.contentZoneId;
                    var updateUrl = spec.updateUrl;

                    Tapestry.findZoneManagerForZone(zoneId).updateFromURL(updateUrl);
                }
            }
        });
    }

    return {

        init : function(specification) {

            spec = specification;

            var rate = spec.refreshRate;
            $T(spec.contentZoneId).pe = new PeriodicalExecuter(update, rate);
        }
    }
}();



Tapestry.Initializer.Refresher = function (spec) {

    Refresher.init(spec);
}