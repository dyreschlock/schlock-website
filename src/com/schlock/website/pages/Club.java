package com.schlock.website.pages;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Club
{
    @Inject
    private PostDAO postDAO;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PageRenderLinkSource linkSource;


    @Property
    private Post currentPost;

    private Integer currentIndex;
    private Integer actualIndex;


    Object onActivate(String parameter)
    {
        if (StringUtils.isNotBlank(parameter))
        {
            Post post = postDAO.getByGalleryName(parameter);
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

    public boolean isNewLine()
    {
        int index = actualIndex;

        boolean newLine = (index != 0 && index % 3 == 0);
        boolean newYear = isNewYear();

        return newLine || newYear;
    }

    public boolean isNewYear()
    {
        if (currentIndex == 0)
        {
            return true;
        }

        int previous = currentIndex -1;
        Post previousPost = getClubGalleries().get(previous);

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

    private String getYear(Post post)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(post.getCreated());

        String year = Integer.toString(cal.get(Calendar.YEAR));
        return year;
    }


    public String getLast()
    {
        int index = actualIndex;
        if (index % 3 == 2)
        {
            return "last";
        }
        return "";
    }

    public String getCurrentImage()
    {
        String image = postManagement.getPostImage(currentPost);
        return image;
    }

    public String getPostTitleHTML()
    {
        String html = postManagement.getStylizedHTMLTitle(currentPost);
        return html;
    }

    Object onSelectPost(String uuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, uuid);
    }


    private List<Post> cachedClubGalleries;

    public List<Post> getClubGalleries()
    {
        if (cachedClubGalleries == null)
        {
            List<String> names = Arrays.asList(Category.EVENT, Category.FESTIVAL);
            cachedClubGalleries = postDAO.getByCategoryNames(names, true);
        }
        return cachedClubGalleries;
    }


    public String getPageTitle()
    {
        return getPage().getTitle();
    }

    private Post cachedPage;

    public Post getPage()
    {
        if (cachedPage == null)
        {
            String pageName = getClass().getSimpleName().toLowerCase();
            cachedPage = postDAO.getByUuid(pageName);
        }
        return cachedPage;
    }
}
