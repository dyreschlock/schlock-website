package com.schlock.website.entities.apps.pokemon;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnownEvent
{
    private Date startDate;
    private Date endDate;

    private String eventName;
    private String unownAvailable;
    private String shinyAvailable;

    private String location;
    private WorldRegion region;

    private boolean attended;

    public UnownEvent()
    {
    }

    public String getStartDateOutput()
    {
        return new SimpleDateFormat("yyyy MMMM d").format(startDate);
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getUnownAvailable()
    {
        return unownAvailable;
    }

    public void setUnownAvailable(String unownAvailable)
    {
        this.unownAvailable = unownAvailable;
    }

    public String getShinyAvailable()
    {
        return shinyAvailable;
    }

    public void setShinyAvailable(String shinyAvailable)
    {
        this.shinyAvailable = shinyAvailable;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public WorldRegion getRegion()
    {
        return region;
    }

    public void setRegion(WorldRegion region)
    {
        this.region = region;
    }

    public boolean isAttended()
    {
        return attended;
    }

    public void setAttended(boolean attended)
    {
        this.attended = attended;
    }
}
