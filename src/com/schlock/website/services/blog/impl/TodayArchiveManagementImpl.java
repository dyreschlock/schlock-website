package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.TodayArchiveManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class TodayArchiveManagementImpl implements TodayArchiveManagement
{
    private final DateFormatter dateFormatter;

    private final PostDAO postDAO;

    private Map<String, Map<String, List<String>>> postsByDateByYear;

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

    private List<AbstractPost> getPostsByDate(String dateString)
    {
        List<String> uuids = getUuidsByDate(dateString);

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

    private List<String> getUuidsByDate(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        Map<String, List<String>> postsByYear = postsByDateByYear.get(dateString);

        List<String> uuids = new ArrayList<>();
        for(List<String> posts : postsByYear.values())
        {
            uuids.addAll(posts);
        }
        return uuids;
    }

    public List<String> getYears(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        List<String> years = new ArrayList<>();
        years.addAll(postsByDateByYear.get(dateString).keySet());

        Collections.sort(years, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.compareTo(o1);
            }
        });

        return years;
    }

    public List<AbstractPost> getPosts(String dateString, String year)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        List<String> uuids = postsByDateByYear.get(dateString).get(year);
        return postDAO.getByUuid(uuids);
    }

    public List<AbstractPost> getPreviewPosts(String dateString, String year)
    {
        List<String> years = getYears(dateString);

        if (StringUtils.equals(year, years.get(0)))
        {
            return Collections.EMPTY_LIST;
        }

        List<String> uuids = postsByDateByYear.get(dateString).get(year);

        return postDAO.getByUuid(uuids.subList(0, 1));
    }

    private void generateCachedMap()
    {
        postsByDateByYear = new HashMap<>();

        List<AbstractPost> posts = postDAO.getAllPublished();
        for(AbstractPost post : posts)
        {
            String uuid = post.getUuid();
            String dateString = dateFormatter.todayArchiveFormat(post.getCreated());

            if (postsByDateByYear.containsKey(dateString))
            {
                String year = getYear(post.getCreated());
                if(postsByDateByYear.get(dateString).containsKey(year))
                {
                    postsByDateByYear.get(dateString).get(year).add(uuid);
                }
                else
                {
                    List<String> uuids = new ArrayList<>();
                    uuids.add(uuid);

                    postsByDateByYear.get(dateString).put(year, uuids);
                }
            }
            else
            {
                List<String> uuids = new ArrayList<>();
                uuids.add(uuid);

                String year = getYear(post.getCreated());

                Map<String, List<String>> postsByYear = new HashMap<>();
                postsByYear.put(year, uuids);

                postsByDateByYear.put(dateString, postsByYear);
            }
        }
    }

    private String getYear(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);

        return Integer.toString(year);
    }
}
