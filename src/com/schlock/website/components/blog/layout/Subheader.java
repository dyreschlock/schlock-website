package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.Index;
import com.schlock.website.pages.keyword.KeywordIndex;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Subheader
{
    @Parameter
    private AbstractPost post;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @Property
    private Keyword currentKeyword;

    @Property
    private Integer currentIndex;


    public boolean isHasBoth()
    {
        return isHasNext() && isHasPrevious();
    }

    public boolean isHasPrevious()
    {
        return getPreviousPost() != null;
    }

    private Post cachedPreviousPost;

    public Post getPreviousPost()
    {
        if (post != null && post.isPost() && cachedPreviousPost == null)
        {
            Post currentPost = (Post) post;
            boolean unpublished = viewState.isShowUnpublished();

            cachedPreviousPost = postDAO.getPreviousPost(currentPost, unpublished, null);
        }
        return cachedPreviousPost;
    }

    public String getPreviousPostLink()
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, getPreviousPost().getUuid()).toURI();
    }

    public String getPreviousPostTitleHtml()
    {
        String title = getPreviousPost().getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }


    public boolean isHasNext()
    {
        return getNextPost() != null;
    }

    private Post cachedNextPost;

    public Post getNextPost()
    {
        if (post != null && post.isPost() && cachedNextPost == null)
        {
            Post currentPost = (Post) post;
            boolean unpublished = viewState.isShowUnpublished();

            cachedNextPost = postDAO.getNextPost(currentPost, unpublished, null);
        }
        return cachedNextPost;
    }

    public String getNextPostLink()
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, getNextPost().getUuid()).toURI();
    }

    public String getNextPostTitleHtml()
    {
        String title = getNextPost().getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }


    public List<Keyword> getKeywords()
    {
        return keywordDAO.getTopPostKeywordsInOrder();
    }

    public boolean isNotFirst()
    {
        return currentIndex != 0;
    }

    public String getKeywordLink()
    {
        return linkSource.createPageRenderLinkWithContext(KeywordIndex.class, currentKeyword.getName()).toURI();
    }
}
