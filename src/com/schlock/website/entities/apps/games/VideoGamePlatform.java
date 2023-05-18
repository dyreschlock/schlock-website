package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;

import java.util.Date;
import java.util.Set;

public class VideoGamePlatform extends Persisted
{
    public static final String PLATFORM_CO_ALL = "All";
    public static final String PLATFORM_CO_SONY = "Sony";
    public static final String PLATFORM_CO_NINTENDO = "Nintendo";
    public static final String PLATFORM_CO_SEGA = "Sega";
    public static final String PLATFORM_CO_MICROSOFT = "Microsoft";
    public static final String PLATFORM_CO_OTHER = "Other";

    private String name;
    private String code;

    private Date releaseDate;
    private String company;

    private Set<VideoGame> games;
    private Set<VideoGame> hardware;

    public VideoGamePlatform()
    {
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

    public Set<VideoGame> getHardware()
    {
        return hardware;
    }

    public void setHardware(Set<VideoGame> hardware)
    {
        this.hardware = hardware;
    }
}
