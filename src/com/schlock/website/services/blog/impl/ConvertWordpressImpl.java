package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.ConvertWordpress;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class ConvertWordpressImpl implements ConvertWordpress
{
    private final PostManagement postManagement;

    private final PostDAO postDAO;
    private final Session session;

    public ConvertWordpressImpl(PostManagement postManagement,
                                PostDAO postDAO,
                                Session session)
    {
        this.postManagement = postManagement;

        this.postDAO = postDAO;
        this.session = session;
    }

    public void startProcess()
    {
        List<Object[]> entries = retrieveWordpressEntries();
        for(Object[] entry : entries)
        {
            Post post = createPost(entry);
        }
    }

    private Post createPost(Object[] entry)
    {
        Date created = (Date) entry[0];
        String postContent = (String) entry[2];
        String postTitle = (String) entry[3];
        Long id = (Long) entry[4];

        Date date = new Date();
        date = DateUtils.setYears(date, 2014);
        date = DateUtils.setMonths(date, 1);
        date = DateUtils.setDays(date, 1);

        Post post = postManagement.createPost(created, postTitle, postContent);
        post.setWpid(id.toString());
        if (created.after(date))
        {
            post.setPublishedLevel(AbstractPost.LEVEL_PUBLISHED);
        }
        return post;
    }

    private List<Object[]> retrieveWordpressEntries()
    {
        String queryText = "select post_date, post_date_gmt, post_content, post_title, id " +
                            " from wp_posts " +
                            " where post_type = 'post' " +
                            " and post_status = 'publish' ";

        Query query = session.createSQLQuery(queryText);
        return query.list();
    }
}
