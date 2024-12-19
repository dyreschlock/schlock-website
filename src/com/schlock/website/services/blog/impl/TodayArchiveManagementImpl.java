package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Post;
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

    private List<String> cachedOrderedDates;

    public TodayArchiveManagementImpl(DateFormatter dateFormatter,
                                        PostDAO postDAO)
    {
        this.dateFormatter = dateFormatter;

        this.postDAO = postDAO;
    }

    public List<String> getAllMonths()
    {
        return Arrays.asList("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec");
    }

    public List<String> getAllDays(String month)
    {
        int y = 2024;
        int m = getAllMonths().indexOf(month);
        int d = 1;

        List<String> days = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);

        while(cal.get(Calendar.MONTH) == m)
        {
            int day = cal.get(Calendar.DAY_OF_MONTH);
            days.add(Integer.toString(day));

            cal.add(Calendar.DATE, 1);
        }
        return days;
    }



    public Post getMostRecent(String dateString)
    {
        return getPostsByDate(dateString).get(0);
    }

    public int getPostCount(String dateString)
    {
        List<String> uuids = getUuidsByDate(dateString);

        return uuids.size();
    }

    private List<Post> getPostsByDate(String dateString)
    {
        List<String> uuids = getUuidsByDate(dateString);

        List<Post> posts = postDAO.getPostsByUuid(uuids);
        Collections.sort(posts, new Comparator<Post>()
        {
            public int compare(Post o1, Post o2)
            {
                return o2.getCreated().compareTo(o1.getCreated());
            }
        });

        return posts;
    }

    public List<String> getUuidsByDate(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        if (!postsByDateByYear.containsKey(dateString))
        {
            return Collections.EMPTY_LIST;
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

    public List<Post> getPosts(String dateString, String year)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        List<String> uuids = postsByDateByYear.get(dateString).get(year);
        return postDAO.getPostsByUuid(uuids);
    }

    public List<Post> getPreviewPosts(String dateString, String year)
    {
        List<String> years = getYears(dateString);

        if (StringUtils.equals(year, years.get(0)))
        {
            return Collections.EMPTY_LIST;
        }

        List<String> uuids = postsByDateByYear.get(dateString).get(year);

        return postDAO.getPostsByUuid(uuids.subList(0, 1));
    }


    public String getNextDayString(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        List<String> dates = getOrderedDates();

        int index = dates.indexOf(dateString);
        if (index == dates.size() - 1)
        {
            return dates.get(0);
        }
        return dates.get(index + 1);
    }

    public String getPreviousDayString(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        List<String> dates = getOrderedDates();

        int index = dates.indexOf(dateString);
        if (index == 0)
        {
            return dates.get(dates.size() -1);
        }
        return dates.get(index - 1);
    }

    private List<String> getOrderedDates()
    {
        if (cachedOrderedDates == null)
        {
            List<String> dates = new ArrayList<>();
            dates.addAll(postsByDateByYear.keySet());

            Collections.sort(dates, new Comparator<String>()
            {
                public int compare(String o1, String o2)
                {
                    Date date1 = dateFormatter.todayArchiveFormat(o1);
                    Date date2 = dateFormatter.todayArchiveFormat(o2);

                    return date1.compareTo(date2);
                }
            });

            cachedOrderedDates = dates;
        }
        return cachedOrderedDates;
    }



    private void generateCachedMap()
    {
        postsByDateByYear = new HashMap<>();

        List<Post> posts = postDAO.getAllPublished();
        for(Post post : posts)
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
