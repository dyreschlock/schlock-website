package com.schlock.website.components.apps.notfibbage.content.game;

import com.schlock.website.pages.apps.notfibbage.Game;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.List;

public class GameQuestionPhase
{
    @Inject
    private NotFibbageManagement management;

    @Inject
    private NotFibbageController controller;

    @Inject
    private JavaScriptSupport javascript;

    @Inject
    private ComponentResources resources;


    @InjectComponent
    private Zone playersZone;


    @Property
    private String currentPlayer;


    void afterRender()
    {
        JSONObject spec = new JSONObject();
        spec.put("currentState", getCurrentState());
        spec.put("refreshRate", Game.REFRESH_RATE_SECONDS);

        spec.put("contentZoneId", playersZone.getClientId());
        spec.put("refreshUrl", resources.createEventLink("refreshZone").toURI());
        spec.put("updateUrl", resources.createEventLink("activatePlayersZone").toURI());
        javascript.addInitializerCall("Refresher", spec);
    }

    public JSONObject onRefreshZone(@RequestParameter("state") String clientState)
    {
        String serverState = getCurrentState();
        boolean update = !StringUtils.equalsIgnoreCase(clientState, serverState);

        JSONObject reply = new JSONObject();
        reply.put("currentState", serverState);
        reply.put("update", update);
        return reply;
    }

    Object onActivatePlayersZone()
    {
        return playersZone.getBody();
    }

    public String getCurrentState()
    {
        List<String> players = getPlayersResponded();

        String state = "players" + StringUtils.join(players, ",");
        return state;
    }

    public List<String> getPlayersResponded()
    {
        return management.getPlayersResponded();
    }


    public String getCurrentQuestion()
    {
        return management.getQuestionText();
    }
}
