package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldPostListingDisplay;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version3ReviewsListingDisplay extends AbstractOldPostListingDisplay
{
    @Parameter(required = true)
    @Property
    private String image4;

    @Parameter(required = true)
    @Property
    private String image2;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }

    public String getCurrentCategoryName()
    {
        return super.getCurrentCategoryName().toUpperCase();
    }

    public String getNewLineHTML()
    {
        int size = getPosts().size();
        int index = getCurrentIndex();

        String newline = "";
        if (index == size / 2)
        {
            newline += "</table>";
            newline += "</td>";
            newline += "<td width=\"4%\" bgcolor=\"#222222\">&nbsp;</td>";
            newline += "<td width=\"35%\" align=\"center\" valign=\"top\">";
            newline += "<table width=\"90%\" border=\"0\">";
        }
        return newline;
    }
}
