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

    @Property
    private Integer currentIndex;


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

    public String getResponseColumnCssClass()
    {
        String css = " column filled response ";

        if ((currentIndex + 1) % 2 == 0)
        {
            css += " twoColumnLast ";
        }
        return css;
    }


    void onSelectResponse(String response)
    {
        management.registerAnswer(playerName, response);
    }
}
