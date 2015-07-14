package com.schlock.website.components.apps.notfibbage.content.player;

import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PlayerAnswerPhase
{
    @Inject
    private NotFibbageManagement management;

    @Parameter(required = true)
    private String playerName;

    @Property
    private String currentResponse;


    public String getCurrentQuestion()
    {
        return management.getQuestionText();
    }

    public boolean isPlayerAnswered()
    {
        return management.hasPlayerAnswered(playerName);
    }

    public List<String> getQuestionResponses()
    {
        return management.getQuestionResponses();
    }

    void onSelectResponse(String response)
    {
        management.registerAnswer(playerName, response);
    }
}
