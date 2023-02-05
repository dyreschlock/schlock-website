package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.ClubPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.LayoutManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Calendar;
import java.util.List;

public class Club
{
    @Inject
    private PostDAO postDAO;

    @Inject
    private LayoutManagement layoutManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private DateFormatter dateFormat;

    @Inject
    private PageRenderLinkSource linkSource;


    @Property
    private ClubPost currentPost;

    private Integer currentIndex;
    private Integer actualIndex;


    Object onActivate(String parameter)
    {
        if (StringUtils.isNotBlank(parameter))
        {
            AbstractPost post = postDAO.getByGalleryName(parameter);
            if (post != null)
            {
                return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
            }
        }
        return true;
    }


    public Integer getCurrentIndex()
    {
        return currentIndex;
    }

    public void setCurrentIndex(Integer index)
    {
        this.currentIndex = index;
        if (currentIndex == 0)
        {
            this.actualIndex = 0;
        }
        else
        {
            this.actualIndex++;
        }
    }

    public boolean isNewYear()
    {
        if (currentIndex == 0)
        {
            return true;
        }

        int previous = currentIndex -1;
        ClubPost previousPost = getClubGalleries().get(previous);

        String thisYear = getCurrentYear();
        String lastYear = getYear(previousPost);

        if (StringUtils.equalsIgnoreCase(thisYear, lastYear))
        {
            return false;
        }

        actualIndex = 0;
        return true;
    }

    public String getCurrentYear()
    {
        String year = getYear(currentPost);
        return year;
    }

    private String getYear(AbstractPost post)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(post.getCreated());

        String year = Integer.toString(cal.get(Calendar.YEAR));
        return year;
    }


    public String getColumnClass()
    {
        String cls = layoutManagement.getColumnClassByIndex(actualIndex);
        return cls;
    }

    public String getCurrentImage()
    {
        Image image = postManagement.getPostImage(currentPost);
        return image.getImageLink();
    }

    public String getPostTitleHTML()
    {
        String html = postManagement.getStylizedHTMLTitle(currentPost);
        return html;
    }

    public String getEventDay()
    {
        return dateFormat.dayFormat(currentPost.getEventDate());
    }


    private List<ClubPost> cachedClubGalleries;

    public List<ClubPost> getClubGalleries()
    {
        if (cachedClubGalleries == null)
        {
            cachedClubGalleries = postDAO.getAllClubPosts(true);
        }
        return cachedClubGalleries;
    }


    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            String pageName = getClass().getSimpleName().toLowerCase();
            cachedPage = (Page) postDAO.getByUuid(pageName);
        }
        return cachedPage;
    }
}
