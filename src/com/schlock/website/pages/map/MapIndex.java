package com.schlock.website.pages.map;

import com.schlock.website.entities.blog.LocationType;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.MapLocationManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class MapIndex
{
    @Inject
    private Messages messages;

    @Inject
    private MapLocationManagement mapManagement;

    @Inject
    private PostDAO postDAO;

    @Property
    private LocationType currentMarker;

    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.MAP_UUID);
        }
        return cachedPage;
    }

    public String getPageDescription()
    {
        return getPage().getBlurb();
    }

    public String getJavascript()
    {
        return mapManagement.generateMapJavascript();
    }

    public LocationType[] getMarkers()
    {
        return LocationType.values();
    }

    public String getMarkerPath()
    {
        return currentMarker.getIconPath();
    }

    public String getMarkerName()
    {
        return messages.get(currentMarker.name());
    }

    public String getMarkerDescription()
    {
        String name = messages.get(currentMarker.name());
        String desc = messages.get(currentMarker.name() + "Desc");

        String DESC = "<b>%s</b>: %s";

        return String.format(DESC, name, desc);
    }
}
