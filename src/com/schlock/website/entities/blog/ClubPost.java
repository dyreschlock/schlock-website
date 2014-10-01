package com.schlock.website.entities.blog;

import java.util.Date;

public class ClubPost extends Post
{
    private Date eventDate;


    public boolean isClubPost()
    {
        return true;
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
