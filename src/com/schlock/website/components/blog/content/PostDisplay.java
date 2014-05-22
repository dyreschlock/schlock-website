package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.category.CategoryIndex;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class PostDisplay
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String cssClass;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private DateFormatter dateFormat;

    @Inject
    private Messages messages;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @Property
    private Category currentCategory;

    @Property
    private Category currentSubcategory;


    public String getPostTitleHtml()
    {
        String title = post.getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    public List<Category> getTopCategories()
    {
        return post.getTopCategories();
    }

    public List<Category> getSubcategories()
    {
        return post.getSubcategories(currentCategory);
    }

    Object onSelectCategory(String categoryUuid)
    {
        return linkSource.createPageRenderLinkWithContext(CategoryIndex.class, categoryUuid);
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }

    public String getCreatedDateHtmlTag()
    {
        return dateFormat.htmlTagFormat(post.getCreated());
    }


    public boolean isHasLinks()
    {
        return isHasFlashCards() || isHasLessonPlan();
    }

    public boolean isHasLessonPlan()
    {
        return StringUtils.isNotBlank(post.getLessonPlanLink());
    }

    public boolean isHasFlashCards()
    {
        return StringUtils.isNotBlank(post.getFlashCardsLink());
    }

    public boolean isHasPreviousNextPosts()
    {
        return !post.isPage();
    }
}
