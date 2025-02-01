package com.schlock.website.components.blog.layout;

import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class FooterLinks
{
    @Parameter(required = true)
    @Property
    private List<String[]> postDetails;

    @Property
    private String[] currentDetails;

    @Inject
    private PostManagement postManagement;


    public String getPostTitle()
    {
        String title = currentDetails[0];
        return title;
    }

    public String getPostTitleHTML()
    {
        String title = getPostTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);
        return html;
    }

    public String getPostLink()
    {
        String uuid = currentDetails[2];
        return "/" + uuid;
    }

    public boolean isHasDate()
    {
        return StringUtils.isNotBlank(getPostDate());
    }

    public String getPostDate()
    {
        String date = currentDetails[1];
        return date;
    }
}
