package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.pages.apps.pocket.Index;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CorePanel
{
    private static final String ARCADE_TITLE_KEY = "arcade";
    private static final String ARCADE_CORE_NAME_KEY = "arcade-cores";

    protected static final String EVEN = "even";
    protected static final String ODD = "odd";

    @Inject
    private PocketDataService pocketDataService;

    @Inject
    private Messages messages;

    @Parameter(required = true)
    private String category;


    @Property
    private PocketCore currentCore;

    @Property
    private Integer currentIndex;


    public String getPanelTitle()
    {
        if (isArcade())
        {
            return messages.get(ARCADE_TITLE_KEY);
        }

        //TODO: NLS
        return category;
    }

    public boolean isArcade()
    {
        return PocketCore.CAT_ARCADE_MULTI.equals(category);
    }

    public List<PocketCore> getCores()
    {
        List<PocketCore> cores = pocketDataService.getCoresByCategory(category);

        Collections.sort(cores, new Comparator<PocketCore>()
        {
            public int compare(PocketCore o1, PocketCore o2)
            {
                if(o1.isFakeArcadeCore())
                {
                    return 1;
                }
                if (o2.isFakeArcadeCore())
                {
                    return -1;
                }

                Integer count1 = pocketDataService.getGamesByCore(o1).size();
                Integer count2 = pocketDataService.getGamesByCore(o2).size();

                return count2.compareTo(count1);
            }
        });

        return cores;
    }

    public String getCurrentCoreNameHTML()
    {
        String html = "<a href=\"%s\">%s</a>";
        String name = currentCore.getName();
        String link = Index.getPageLink(currentCore, null);

        return String.format(html, link, name);
    }

    public String getCurrentCoreGameCount()
    {
        List<PocketGame> games = pocketDataService.getGamesByCore(currentCore);
        return Integer.toString(games.size());
    }

    public String getArcadeGameCount()
    {
        List<PocketCore> arcadeCores = pocketDataService.getCoresByCategory(PocketCore.CAT_ARCADE);
        int count = 0;
        for(PocketCore core : arcadeCores)
        {
            count += pocketDataService.getGamesByCore(core).size();
        }
        return Integer.toString(count);
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
