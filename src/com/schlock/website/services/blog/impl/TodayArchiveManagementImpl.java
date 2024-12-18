package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.TodayArchiveManagement;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.*;

public class TodayArchiveManagementImpl implements TodayArchiveManagement
{
    private final DateFormatter dateFormatter;

    private final PostDAO postDAO;

    private Map<String, List<String>> postsByDate;

    public TodayArchiveManagementImpl(DateFormatter dateFormatter,
                                        PostDAO postDAO)
    {
        this.dateFormatter = dateFormatter;

        this.postDAO = postDAO;
    }


    public AbstractPost getMostRecent(String dateString)
    {
        return getPostsByDate(dateString).get(0);
    }

    public List<AbstractPost> getPostsByDate(String dateString)
    {
        if (postsByDate == null)
        {
            generateCachedMap();
        }

        List<String> uuids = postsByDate.get(dateString);

        List<AbstractPost> posts = postDAO.getByUuid(uuids);
        Collections.sort(posts, new Comparator<AbstractPost>()
        {
            @Override
            public int compare(AbstractPost o1, AbstractPost o2)
            {
                return o2.getCreated().compareTo(o1.getCreated());
            }
        });

        return posts;
    }



    private void generateCachedMap()
    {
        postsByDate = new HashMap<>();

        List<AbstractPost> posts = postDAO.getAllPublished();
        for(AbstractPost post : posts)
        {
            String uuid = post.getUuid();
            String dateString = dateFormatter.todayArchiveFormat(post.getCreated());

            if (postsByDate.containsKey(dateString))
            {
                postsByDate.get(dateString).add(uuid);
            }
            else
            {
                List<String> uuids = new ArrayList<>();
                uuids.add(uuid);

                postsByDate.put(dateString, uuids);
            }
        }
    }
}
