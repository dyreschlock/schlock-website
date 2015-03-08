package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.*;
import com.schlock.website.pages.Projects;
import com.schlock.website.pages.category.CategoryIndex;
import com.schlock.website.pages.lessons.LessonsIndex;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.LessonsManagement;
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
    private LessonsManagement lessonManagement;

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
    private Keyword currentKeyword;

    @Property
    private AbstractCategory currentCategory;

    @Property
    private AbstractCategory currentSubcategory;

    @Property
    private String currentGrade;


    public String getPostTitleHtml()
    {
        String title = post.getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    public List<PostCategory> getTopCategories()
    {
        return post.getTopPostCategories();
    }

    public List<AbstractCategory> getSubcategories()
    {
        return post.getSubcategories(currentCategory);
    }

    public boolean isProject()
    {
        return post.isProject();
    }

    public List<ProjectCategory> getProjectCategories()
    {
        return post.getProjectCategories();
    }

    public String getCategoryCssClass()
    {
        String css = "";

        AbstractCategory parent = currentCategory.getParent();
        if (parent != null)
        {
            css = parent.getUuid();
        }
        return css;
    }

    Object onSelectCategory(String categoryUuid)
    {
        return linkSource.createPageRenderLinkWithContext(CategoryIndex.class, categoryUuid);
    }

    Object onSelectProjectCategory(String categoryUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Projects.class, categoryUuid);
    }


    public boolean isLesson()
    {
        return post.isLessonPost();
    }

    public String getLessonCssClass()
    {
        String y = lessonManagement.getYear(post);
        return y;
    }

    public String getLessonYear()
    {
        String y = lessonManagement.getYear(post);

        String text = messages.get(y);
        String html = postManagement.wrapJapaneseTextInTags(text);
        return html;
    }

    public List<String> getGrades()
    {
        return lessonManagement.getGrades(post);
    }

    Object onSelectYearPage()
    {
        String y = lessonManagement.getYear(post);

        return linkSource.createPageRenderLinkWithContext(LessonsIndex.class, y);
    }


    public String getLessonGrade()
    {
        String text = messages.get(currentGrade);
        String html = postManagement.wrapJapaneseTextInTags(text);
        return html;
    }

    Object onSelectYearGradePage(String grade)
    {
        String y = lessonManagement.getYear(post);

        return linkSource.createPageRenderLinkWithContext(LessonsIndex.class, y, grade);
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
