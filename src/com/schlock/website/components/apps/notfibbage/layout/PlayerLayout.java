package com.schlock.website.components.apps.notfibbage.layout;

import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import com.schlock.website.services.blog.CssCache;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PlayerLayout
{
    @Parameter
    private String playerName;


    @Inject
    private NotFibbageManagement management;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;




    public String getPlayerName()
    {
        String name = this.playerName;
        if (StringUtils.isBlank(name))
        {
            name = messages.get("noName");
        }
        return name;
    }

    public String getPlayerScore()
    {
        String scoreText = "";

        Integer score = management.getScore(this.playerName);
        if (score == null)
        {
            scoreText = messages.get("noScore");
        }
        else
        {
            scoreText = Integer.toString(score);
        }
        return scoreText;
    }


    public String getTitle()
    {
        String name = getPlayerName();
        String score = getPlayerScore();

        return name + " : " + score;
    }

    public String getCss()
    {
        return cssCache.getAllCss();
    }
}
