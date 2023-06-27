package com.schlock.website.pages.apps.pocket;

import org.apache.tapestry5.ioc.Messages;

import javax.inject.Inject;

public class Index
{
    private static final String POST_UUID = "curated-games-list-analogue-pocket";

    private static final String TITLE_KEY = "title";

    @Inject
    private Messages messages;


    public String getPostUuid()
    {
        return POST_UUID;
    }

    public String getPlainTitle()
    {
        return messages.get(TITLE_KEY);
    }

    public String getTitle()
    {
        return messages.get(TITLE_KEY);
    }
}
