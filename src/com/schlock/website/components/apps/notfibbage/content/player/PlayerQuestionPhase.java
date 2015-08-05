package com.schlock.website.components.apps.notfibbage.content.player;

import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PlayerQuestionPhase
{
    @Inject
    private NotFibbageManagement management;

    @Inject
    private Messages messages;


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
        boolean correct = management.isResponseCorrect(playerResponse);

        if (correct)
        {
            String message = messages.get("error-answer-correct");
            throw new ValidationException(message);
        }
    }

    void onSuccessFromResponseForm()
    {
        management.registerResponse(playerName, playerResponse);

        playerResponse = null;
    }
}
