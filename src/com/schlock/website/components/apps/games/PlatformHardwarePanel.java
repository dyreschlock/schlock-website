package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.HardwareType;
import com.schlock.website.entities.apps.games.VideoGameHardware;
import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.services.database.apps.games.VideoGameHardwareDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.SimpleDateFormat;
import java.util.*;

public class PlatformHardwarePanel
{
    @Inject
    private VideoGameHardwareDAO hardwareDAO;

    @Inject
    private Messages messages;

    @Parameter
    @Property
    private VideoGamePlatform platform;

    @Property
    private VideoGameHardware currentHardware;

    @Property
    private Integer currentIndex;

    public List<VideoGameHardware> getHardware()
    {
        List<VideoGameHardware> hardware = new ArrayList<VideoGameHardware>();
        if (platform != null)
        {
            hardware.addAll(platform.getHardware());

            Collections.sort(hardware, new Comparator<VideoGameHardware>()
            {
                @Override
                public int compare(VideoGameHardware o1, VideoGameHardware o2)
                {
                    HardwareType CONSOLE = HardwareType.CONSOLE;

                    if (CONSOLE.equals(o1.getHardwareType()) && !CONSOLE.equals(o2.getHardwareType()))
                    {
                        return -1;
                    }
                    if (!CONSOLE.equals(o1.getHardwareType()) && CONSOLE.equals(o2.getHardwareType()))
                    {
                        return 1;
                    }
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        else
        {
            hardware.addAll(hardwareDAO.getAll());

            Collections.sort(hardware, new Comparator<VideoGameHardware>()
            {
                @Override
                public int compare(VideoGameHardware o1, VideoGameHardware o2)
                {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
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
