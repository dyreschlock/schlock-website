package com.schlock.website.pages;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Archive
{
    @Inject
    private Messages messages;



    public String getPageTitle()
    {
        return messages.get("page-title");
    }
}
