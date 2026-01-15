package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;

import java.util.*;

public class VideoGamePlatform extends Persisted
{
    public static final String PLATFORM_CO_ALL = "All";
    public static final String PLATFORM_CO_SONY = "Sony";
    public static final String PLATFORM_CO_NINTENDO = "Nintendo";
    public static final String PLATFORM_CO_SEGA = "Sega";
    public static final String PLATFORM_CO_MICROSOFT = "Microsoft";
    public static final String PLATFORM_CO_OTHER = "Other";

    public static final String PLATFORM_BLANK = "blank";

    private String name;
    private String code;

    private Date releaseDate;
    private String company;

    private Set<VideoGame> games;
    private Set<VideoGameHardware> hardware;

    public VideoGamePlatform()
    {
    }

    public int getConsoleCount()
    {
        int count = 0;
        for(VideoGameHardware hard : hardware)
        {
            if (HardwareType.CONSOLE.equals(hard.getHardwareType()))
            {
                count++;
            }
        }
        return count;
    }

    public List<VideoGameHardware> getSortedAvailableHardware()
    {
        List<VideoGameHardware> output = new ArrayList<VideoGameHardware>();
        for(VideoGameHardware hard : getHardware())
        {
            if (!hard.isSold())
            {
                output.add(hard);
            }
        }

        Collections.sort(output, new Comparator<VideoGameHardware>()
        {
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

        return output;
    }

    public List<VideoGame> getAvailableGames()
    {
        List<VideoGame> available = new ArrayList<>();
        for(VideoGame game : getGames())
        {
            if(!game.isSold())
            {
                available.add(game);
            }
        }
        return available;
    }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date release)
    {
        this.releaseDate = release;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public Set<VideoGame> getGames()
    {
        return games;
    }

    public void setGames(Set<VideoGame> games)
    {
        this.games = games;
    }

    public Set<VideoGameHardware> getHardware()
    {
        return hardware;
    }

    public void setHardware(Set<VideoGameHardware> hardware)
    {
        this.hardware = hardware;
    }
}
