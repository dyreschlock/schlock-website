package com.schlock.website.entities.blog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClubPost extends Post
{
    private Date eventDate;


    public String getEventDayFormat()
    {
        if(eventDate == null)
        {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(DAY_FORMAT);
        return format.format(eventDate);
    }

    public Date getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
    }
}
