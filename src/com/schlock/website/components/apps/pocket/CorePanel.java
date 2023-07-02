package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.pages.apps.pocket.Index;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class CorePanel
{
    protected static final String EVEN = "even";
    protected static final String ODD = "odd";

    @Inject
    private PocketDataService pocketDataService;

    @Parameter(required = true)
    private String category;


    @Property
    private PocketCore currentCore;

    @Property
    private Integer currentIndex;


    public String getPanelTitle()
    {
        //TODO: NLS
        return category;
    }

    public List<PocketCore> getCores()
    {
        return pocketDataService.getCoresByCategory(category);
    }

    public String getCurrentCoreNameHTML()
    {
        String html = "<a href=\"%s\">%s</a>";
        String name = currentCore.getName();
        String link = Index.getPageLink(currentCore);

        return String.format(html, link, name);
    }

    public String getCurrentCoreGameCount()
    {
        List<PocketGame> games = pocketDataService.getGamesByCore(currentCore);
        return Integer.toString(games.size());
    }

    public String getTotalCount()
    {
        int count = 0;
        for(PocketCore core : getCores())
        {
            List<PocketGame> games = pocketDataService.getGamesByCore(core);
            count += games.size();
        }

        return Integer.toString(count);
    }


    public String getOuterLeftCss()
    {
        String css = "outerLeft ";

        if (currentIndex == getCores().size() - 1)
        {
            css += "outerBottom";
        }
        return css;
    }

    public String getOuterRightCss()
    {
        String css = "outerRight ";

        if (currentIndex == 0)
        {
            css += "outerTop";
        }
        return css;
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return EVEN;
        }
        return ODD;
    }
}
