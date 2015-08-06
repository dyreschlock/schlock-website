package com.schlock.website.pages.apps.notfibbage;

import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "context:js/apps/notfibbage.js")
public class Player
{
    private static final int REFRESH_RATE_SECONDS = 2;

    @Inject
    private NotFibbageManagement management;

    @Inject
    private NotFibbageController controller;

    @Inject
    private JavaScriptSupport javascript;

    @Inject
    private ComponentResources resources;

    @Inject
    private Messages messages;


    @InjectComponent
    private Zone contentZone;


    @Property
    @Persist
    private String playerName;



    void afterRender()
    {
        if (StringUtils.isNotBlank(this.playerName))
        {
            JSONObject spec = new JSONObject();
            spec.put("currentState", getCurrentState());
            spec.put("refreshRate", REFRESH_RATE_SECONDS);

            spec.put("contentZoneId", contentZone.getClientId());
            spec.put("refreshUrl", resources.createEventLink("refreshZone").toURI());
            spec.put("updateUrl", resources.createEventLink("activateContentZone").toURI());
            javascript.addInitializerCall("Refresher", spec);
        }
    }

    Object onActivate()
    {
        if (this.playerName != null)
        {
            boolean registered = management.isRegisteredPlayer(this.playerName);
            if (!registered)
            {
                this.playerName = null;
            }
        }
        return true;
    }

    Object onActivate(String parameter)
    {
        boolean registered = management.isRegisteredPlayer(parameter);
        if (!registered)
        {
            return onActivate();
        }

        this.playerName = parameter;
        return true;
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

    Object onActivateContentZone()
    {
        return contentZone.getBody();
    }

    public String getTitle()
    {
        return messages.get("title");
    }

    public boolean hasPlayerName()
    {
        return this.playerName != null;
    }

    public String getCurrentState()
    {
        return controller.getPlayerState(playerName);
    }

    public String getPhaseTitle()
    {
        String key = controller.getGameState() + "-phase";
        return messages.get(key);
    }


    public boolean isRegisterPhase()
    {
        return controller.isRegisterPhase();
    }

    public boolean isQuestionPhase()
    {
        return controller.isQuestionPhase();
    }

    public boolean isAnswerPhase()
    {
        return controller.isAnswerPhase();
    }

    public boolean isResultsPhase()
    {
        return controller.isResultsPhase();
    }

    public boolean isStandingsPhase()
    {
        return controller.isStandingsPhase();
    }

    public boolean isFinalPhase()
    {
        return controller.isFinalPhase();
    }
}
