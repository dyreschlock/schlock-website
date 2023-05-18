package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.VideoGameHardware;
import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.services.database.apps.games.VideoGamePlatformDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlatformHardwarePanel
{
    @Inject
    private VideoGamePlatformDAO platformDAO;

    @Inject
    private Messages messages;

    @Parameter
    @Property
    private VideoGamePlatform platform;

    @Property
    private VideoGameHardware currentHardware;

    @Property
    private Integer currentIndex;

    public boolean isPlatformSelected()
    {
        return platform != null;
    }

    public List<VideoGameHardware> getHardware()
    {
        if (isPlatformSelected())
        {
            return platform.getSortedHardware();
        }

        List<VideoGameHardware> hardware = new ArrayList<VideoGameHardware>();

        for(VideoGamePlatform platform : platformDAO.getAll())
        {
            hardware.addAll(platform.getSortedHardware());
        }
        return hardware;
    }

    public String getPlatformCode()
    {
        if (platform != null)
        {
            return platform.getCode();
        }
        return "blank";
    }

    public String getCurrentTitle()
    {
        String title = currentHardware.getTitle();
        if (StringUtils.isNotBlank(currentHardware.getPostUUID()))
        {
            String span = "<a href=\"%s\">%s</a>";

            title = String.format(span, "/" + currentHardware.getPostUUID(), title);
        }
        return title;
    }

    public String getCurrentHardwarePlatformCodeText()
    {
        String code = getCurrentHardwarePlatformCode();
        return messages.get(code);
    }

    public String getCurrentHardwarePlatformCode()
    {
        for(VideoGamePlatform platform : platformDAO.getAll())
        {
            if (platform.getHardware().contains(currentHardware))
            {
                return platform.getCode();
            }
        }
        return "blank";
    }

    public String getCurrentReleaseDate()
    {
        Date date = currentHardware.getReleaseDate();
        if (date != null)
        {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            return simpleDateFormat.format(date);
        }
        return "";
    }

    public String getCurrentHardwareType()
    {
        String key = currentHardware.getHardwareType().name().toLowerCase();
        return messages.get(key);
    }

    public String getCurrentRegion()
    {
        String key = currentHardware.getRegion().name().toLowerCase();
        return messages.get(key);
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return PlatformPanel.EVEN;
        }
        return PlatformPanel.ODD;
    }
}
