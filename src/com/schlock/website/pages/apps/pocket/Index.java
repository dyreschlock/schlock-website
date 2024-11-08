package com.schlock.website.pages.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.Device;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.services.apps.pocket.PocketDataService;
import com.schlock.website.services.apps.pocket.impl.PocketDataServiceImpl;
import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Index
{
    private static final String IMAGE_VIEW_ID = "img";

    private static final String POST_UUID = "digital-retro-game-library";

    private static final String TITLE_KEY = "title";

    public static final Integer STATS_FULL_MAX_RESULTS = PocketDataServiceImpl.MOST_COMMON_MAX_RESULTS;
    public static final Integer STATS_HALF_MAX_RESULTS = 5;

    @Inject
    private PocketDataService pocketDataService;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;

    @Property
    private PocketCore selectedCore;

    @Property
    private String selectedGenre;

    @Property
    private Device selectedDevice;

    @Property
    private Boolean imageView;

    Object onActivate()
    {
        return onActivate(null);
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

            selectedDevice = Device.value(parameter);
            if (selectedDevice == null)
            {
                selectedCore = pocketDataService.getCoreByPlatformId(parameter);
                if (selectedCore == null)
                {
                    selectedGenre = parameter;
                }
            }
        }
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        /**
         * Six scenarios:
         * /img/device
         * /img/core
         * /img/genre
         *
         * /device/core
         * /device/genre
         *
         * /core/genre
         */

        if (IMAGE_VIEW_ID.equalsIgnoreCase(p1))
        {
            onActivate(p2);
            imageView = true;
        }
        else
        {
            if(Device.value(p1) != null)
            {
                onActivate(p2);
                selectedDevice = Device.value(p1);
            }
            else
            {
                selectedDevice = null;
                selectedCore = pocketDataService.getCoreByPlatformId(p1);
                selectedGenre = p2;
            }
            imageView = false;
        }
        return true;
    }

    Object onActivate(String p1, String p2, String p3)
    {
        /**
         * Four scenarios:
         *
         * /img/device/core
         * /img/device/genre
         * /img/core/genre
         *
         * /device/core/genre
         */

        if (IMAGE_VIEW_ID.equalsIgnoreCase(p1))
        {
            onActivate(p2, p3);
            imageView = true;
        }
        else
        {
            selectedDevice = Device.value(p1);
            selectedCore = pocketDataService.getCoreByPlatformId(p2);
            selectedGenre = p3;
            imageView = false;
        }
        return true;
    }

    Object onActivate(String p1, String p2, String p3, String p4)
    {
        imageView = true;
        selectedDevice = Device.value(p2);
        selectedCore = pocketDataService.getCoreByPlatformId(p3);
        selectedGenre = p4;

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
        if (selectedCore != null)
        {
            title += " // " + selectedCore.getName();
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
        if (selectedCore != null || selectedGenre != null)
        {
            String link = Index.getPageLink(imageView, selectedDevice,null, null);

            title = String.format(LINK_HTML, link, title);
        }

        if (selectedGenre != null)
        {
            title += " // " + messages.get(selectedGenre);
        }
        return title;
    }

    public String getViewLinkText()
    {
        if (imageView)
        {
            return messages.get("view-data");
        }
        return messages.get("view-images");
    }

    public String getViewLinkLink()
    {
        return getPageLink(!imageView, selectedDevice, selectedCore, selectedGenre);
    }

    public boolean isNothingSelected()
    {
        return selectedCore == null && selectedGenre == null;
    }

    public boolean isCoreSelected()
    {
        return selectedCore != null;
    }

    public boolean isGenreNotCoreSelected()
    {
        return selectedCore == null && selectedGenre != null;
    }


    public List<DataPanelData> getDevData()
    {
        return pocketDataService.getCountByMostCommonDeveloper(selectedDevice, selectedCore, selectedGenre, getMaxResults());
    }

    public List<DataPanelData> getPubData()
    {
        return pocketDataService.getCountByMostCommonPublisher(selectedDevice, selectedCore, selectedGenre, getMaxResults());
    }

    public List<DataPanelData> getYearData()
    {
        return pocketDataService.getCountByMostCommonYear(selectedDevice, selectedCore, selectedGenre, getMaxResults());
    }

    private int getMaxResults()
    {
        if (selectedCore != null || selectedGenre != null)
        {
            return STATS_HALF_MAX_RESULTS;
        }
        return STATS_FULL_MAX_RESULTS;
    }


    public String getConsole()
    {
        return PocketCore.CAT_CONSOLE;
    }

    public String getHandheld()
    {
        return PocketCore.CAT_HANDHELD;
    }

    public String getComputer()
    {
        return PocketCore.CAT_COMPUTER;
    }

    public String getArcadeMulti()
    {
        return PocketCore.CAT_ARCADE_MULTI;
    }


    public static String getPageLink(boolean imageView, Device device, PocketCore core, String genre)
    {
        String link = "/apps/pocket";

        if (imageView)
        {
            link += "/" + IMAGE_VIEW_ID;
        }
        if (device != null)
        {
            link += "/" + device.name().toLowerCase();
        }
        if (core != null)
        {
            link += "/" + core.getPlatformId();
        }
        if (genre != null)
        {
            link += "/" + genre;
        }
        return link;
    }
}

