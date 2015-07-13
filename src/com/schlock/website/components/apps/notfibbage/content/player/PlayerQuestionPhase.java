package com.schlock.website.components.apps.notfibbage.content.player;

import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PlayerQuestionPhase
{
    @Inject
    private NotFibbageManagement management;


    @Parameter(required = true)
    private String playerName;


    @Property
    @Persist
    private String playerResponse;


    public String getCurrentQuestion()
    {
        return management.getQuestionText();
    }

    public boolean isPlayerResponded()
    {
        return management.hasPlayerResponded(playerName);
    }


    void onValidateFromResponseForm() throws ValidationException
    {

    }

    void onSuccessFromResponseForm()
    {
        management.registerResponse(playerName, playerResponse);

        playerResponse = null;
    }
}
