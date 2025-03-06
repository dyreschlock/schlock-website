package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
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

import java.util.List;

public class PostDisplay
{
    private static final String POST_NUMBER_KEY = "post-number";

    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String cssClass;

    @Inject
    private DeploymentContext context;

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

    public String getWebUrl()
    {
        return context.webDomain();
    }

    public boolean isHasCategories()
    {
        return !post.getCategories().isEmpty();
    }

    public List<PostCategory> getTopCategories()
    {
        return post.getTopPostCategories();
    }

    public List<AbstractCategory> getSubcategories()
    {
        return post.getSubcategories(currentCategory);
    }

    public List<AbstractCategory> getExtraCategories()
    {
        return post.getExtraCategories();
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

    public boolean isLessonDisplay()
    {
        return post.isLessonPost() && post.getCoursePage() == null;
    }

    public boolean isCourseDisplay()
    {
        return post.isLessonPost() && post.getCoursePage() != null;
    }

    public String getLessonCssClass()
    {
        if (isCourseDisplay())
        {
            return "h27";
        }
        String y = lessonManagement.getYear(post);
        return y;
    }

    public String getLessonYearKeyword()
    {
        String keyword = lessonManagement.getYear(post);
        return keyword;
    }

    public String getLessonYearText()
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

    public String getLessonGrade()
    {
        String text = messages.get(currentGrade);
        String html = postManagement.wrapJapaneseTextInTags(text);
        return html;
    }

    public String getCoursePageUuid()
    {
        return (post.getCoursePage() != null) ? post.getCoursePage().getUuid() : null;
    }

    public String getCoursePageTitle()
    {
        return (post.getCoursePage() != null) ? post.getCoursePage().getTitle() : null;
    }


    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post);
        return html;
    }

    public boolean isShowNumber()
    {
        if (post.isPost() && !post.isStubPost())
        {
            return ((Post) post).getNumber() != null;
        }
        return false;
    }

    public String getPostNumber()
    {
        if (post.isPost())
        {
            return messages.format(POST_NUMBER_KEY, ((Post) post).getDisplayNumber());
        }
        return null;
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
        return post.isHasLessonLinks();
    }

    public boolean isHasLessonPlan()
    {
        return StringUtils.isNotBlank(post.getLessonPlanLink());
    }

    public String getLessonPlanImage()
    {
        return lessonManagement.getLessonPlanImageLink(post);
    }

    public boolean isHasFlashCards()
    {
        return StringUtils.isNotBlank(post.getFlashCardsLink());
    }

    public String getFlashCardsImage()
    {
        return lessonManagement.getFlashCardImageLink(post);
    }

    public boolean isHasPreviousNextPosts()
    {
        return !post.isPage();
    }
}
