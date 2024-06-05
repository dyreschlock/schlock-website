package com.schlock.website.pages.apps.ps2;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Index
{
    private static final String IMAGE_VIEW_ID = "img";

    private static final String POST_UUID = "playstation-2-game-inventory";

    private static final String TITLE_KEY = "title";

    private static final Integer STATS_FULL_MAX_RESULTS = 20;
    private static final Integer STATS_HALF_MAX_RESULTS = 5;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;

    @Inject
    private PlaystationGameDAO gameDAO;


    @Property
    private PlaystationPlatform selectedPlatform;

    @Property
    private String selectedGenre;

    @Property
    private Boolean imageView;

    Object onActivate()
    {
        imageView = false;
        selectedPlatform = null;
        selectedGenre = null;

        return true;
    }

    Object onActivate(String parameter)
    {
        if (IMAGE_VIEW_ID.equalsIgnoreCase(parameter))
        {
            imageView = true;
        }
        else
        {
            imageView = false;

            selectedPlatform = PlaystationPlatform.getFromText(parameter);
            if (selectedPlatform == null)
            {
                selectedGenre = messages.get(parameter);
            }
        }
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        /**
         * /img/platform
         * /img/genre
         * /platform/genre
         */

        if (IMAGE_VIEW_ID.equalsIgnoreCase(p1))
        {
            onActivate(p2);
            imageView = true;
        }
        else
        {
            selectedPlatform = PlaystationPlatform.getFromText(p1);
            selectedGenre = messages.get(p2);
            imageView = false;
        }
        return true;
    }

    Object onActivate(String p1, String p2, String p3)
    {
        imageView = IMAGE_VIEW_ID.equalsIgnoreCase(p1);
        selectedPlatform = PlaystationPlatform.getFromText(p2);
        selectedGenre = messages.get(p3);

        return true;
    }

    public String getCss()
    {
        return cssCache.getCssForGames();
    }

    public String getPostUuid()
    {
        return POST_UUID;
    }

    public String getPlainTitle()
    {
        String title = messages.get(TITLE_KEY);
        if (selectedPlatform != null)
        {
            String key = selectedPlatform.name().toLowerCase();
            title += " // " + messages.get(key);
        }
        if (selectedGenre != null)
        {
            title += " // " + selectedGenre;
        }
        return title;
    }

    public String getTitle()
    {
        final String LINK_HTML = "<a href=\"%s\">%s</a>";

        String title = messages.get(TITLE_KEY);
        if (selectedPlatform != null || selectedGenre != null)
        {
            String link = Index.getPageLink(imageView, null, null);

            title = String.format(LINK_HTML, link, title);
        }
        if (selectedGenre != null)
        {
            title += " // " + selectedGenre;
        }
        return title;
    }

    public String getViewLinkText()
    {
        if(imageView)
        {
            return messages.get("view-data");
        }
        return messages.get("view-images");
    }

    public String getViewLinkLink()
    {
        return getPageLink(!imageView, selectedPlatform, selectedGenre);
    }

    public boolean isNothingSelected()
    {
        return selectedPlatform == null && selectedGenre == null;
    }

    public boolean isPlatformSelected()
    {
        return selectedPlatform != null;
    }

    public boolean isGenreNotPlatformSelected()
    {
        return selectedPlatform == null && selectedGenre != null;
    }


    private int maxResults()
    {
        if (isNothingSelected())
        {
            return STATS_FULL_MAX_RESULTS;
        }
        return STATS_HALF_MAX_RESULTS;
    }

    public List<DataPanelData> getDevData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonDeveloper(selectedPlatform, selectedGenre, maxResults());
        return DataPanelData.createPanelData(devData);
    }

    public List<DataPanelData> getPubData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonPublisher(selectedPlatform, selectedGenre, maxResults());
        return DataPanelData.createPanelData(devData);
    }

    public List<DataPanelData> getYearData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonYear(selectedPlatform, selectedGenre, maxResults());
        return DataPanelData.createPanelData(devData);
    }



    public static String getPageLink(boolean imageView, PlaystationPlatform platform, String genre)
    {
        String link = "/apps/ps2";

        if (imageView)
        {
            link += "/" + IMAGE_VIEW_ID;
        }
        if (platform != null)
        {
            link += "/" + platform.name().toLowerCase();
        }
        if (genre != null)
        {
            String genreStub = genre.replaceAll(" ", "").toLowerCase();
            link += "/" + genreStub;
        }
        return link;
    }
}
