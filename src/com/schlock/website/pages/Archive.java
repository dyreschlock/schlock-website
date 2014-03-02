package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collections;
import java.util.List;

public class Archive
{
    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;

    @InjectComponent
    private Zone archiveZone;


    @SessionState
    private ViewState viewState;


    @Property
    private Integer currentYear;

    @Property
    private Integer currentMonth;

    @Property
    private Post currentPost;



    public List<Integer> getPostYears()
    {
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = viewState.getCurrentCategoryId();

        List<Integer> years = postDAO.getPostYears(unpublished, categoryId);
        return years;
    }

    Object onSelectYear(int year)
    {
        viewState.setArchiveYear(year);

        return archiveZone;
    }

    public boolean isYearSelected()
    {
        return viewState.isHasArchiveYear();
    }

    public List<Integer> getPostMonths()
    {
        Integer year = viewState.getArchiveYear();
        if(year == null)
        {
            return Collections.emptyList();
        }

        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = viewState.getCurrentCategoryId();

        List<Integer> months = postDAO.getPostMonths(year, unpublished, categoryId);
        return months;
    }

    Object onSelectMonth(int month)
    {
        viewState.setArchiveMonth(month);

        return archiveZone;
    }

    public boolean isMonthSelected()
    {
        return viewState.isHasArchiveMonth();
    }

    public String getCurrentMonthString()
    {
        return messages.get(Integer.toString(currentMonth));
    }

    public List<Post> getArchivedPosts()
    {
        Integer year = viewState.getArchiveYear();
        Integer month = viewState.getArchiveMonth();

        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = viewState.getCurrentCategoryId();

        List<Post> posts = postDAO.getPostsByYearMonth(year, month, unpublished, categoryId);
        return posts;
    }




    public String getPageTitle()
    {
        return messages.get("page-title");
    }
}
