package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.TodayArchiveManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class TodayArchiveManagementImpl implements TodayArchiveManagement
{
    private final ImageManagement imageManagement;

    private final DateFormatter dateFormatter;

    private final PostDAO postDAO;

    private Map<String, CachedDatePosts> postsByDateByYear;

    private List<String> cachedOrderedDates;

    public TodayArchiveManagementImpl(ImageManagement imageManagement,
                                        DateFormatter dateFormatter,
                                        PostDAO postDAO)
    {
        this.imageManagement = imageManagement;
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




    public boolean isDayExists(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }
        return postsByDateByYear.get(dateString) != null;
    }





    public Post getPreviewPost(String dateString)
    {
        String uuid = getPreviewPostUuid(dateString);

        List<Post> posts = postDAO.getPostsByUuid(Arrays.asList(uuid));
        return posts.get(0);
    }

    public String getPreviewPostUuid(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        if (!postsByDateByYear.containsKey(dateString))
        {
            return null;
        }
        return postsByDateByYear.get(dateString).getPreviewPost();
    }

    public int getPostCount(String dateString)
    {
        List<String> uuids = getUuidsByDate(dateString);

        return uuids.size();
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

        return postsByDateByYear.get(dateString).getAllUuids();
    }

    public List<String> getYears(String dateString)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        return postsByDateByYear.get(dateString).getYears(dateString);
    }

    public List<Post> getPosts(String dateString, String year)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        List<String> uuids = postsByDateByYear.get(dateString).getUuids(year);
        return postDAO.getPostsByUuid(uuids);
    }

    public List<Post> getPreviewPosts(String dateString, String year)
    {
        if (postsByDateByYear == null)
        {
            generateCachedMap();
        }

        String uuid = postsByDateByYear.get(dateString).getPreviewPost(year);

        if (StringUtils.isBlank(uuid))
        {
            return Collections.EMPTY_LIST;
        }
        return postDAO.getPostsByUuid(Arrays.asList(uuid));
    }


    public String getNextDayString(String dateString)
    {
        List<String> dates = getOrderedDates();

        int index = dates.indexOf(dateString);
        if (index == dates.size() - 1)
        {
            return dates.get(0);
        }
        if (index != -1)
        {
            return dates.get(index + 1);
        }
        return getNextDate(dateString);
    }

    public String getPreviousDayString(String dateString)
    {
        List<String> dates = getOrderedDates();

        int index = dates.indexOf(dateString);
        if (index == 0)
        {
            return dates.get(dates.size() -1);
        }
        if (index != -1)
        {
            return dates.get(index - 1);
        }
        String date = getNextDate(dateString);
        return getPreviousDayString(date);
    }

    private String getNextDate(String dateString)
    {
        String currentMonth = dateString.split("-")[0];
        int currentDay = Integer.parseInt(dateString.split("-")[1]);

        boolean foundMonth = false;
        for(String date : getOrderedDates())
        {
            if (date.startsWith(currentMonth))
            {
                foundMonth = true;
                int day = Integer.parseInt(date.split("-")[1]);
                if (day > currentDay)
                {
                    return date;
                }
            }
            else if (foundMonth)
            {
                return date;
            }
        }
        return getOrderedDates().get(0);
    }

    private List<String> getOrderedDates()
    {
        if (cachedOrderedDates == null)
        {
            if (postsByDateByYear == null)
            {
                generateCachedMap();
            }

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
            String dateString = dateFormatter.todayArchiveFormat(post.getCreated());

            if (!postsByDateByYear.containsKey(dateString))
            {
                postsByDateByYear.put(dateString, new CachedDatePosts());
            }
            postsByDateByYear.get(dateString).addPost(post);
        }
    }

    private class CachedDatePosts
    {
        private Map<String, List<String>> postsByYear;

        private String previewPost;
        private String mostRecent;

        public CachedDatePosts()
        {
            postsByYear = new HashMap<>();
        }


        public List<String> getYears(String dateString)
        {
            List<String> years = new ArrayList<>();
            years.addAll(postsByYear.keySet());

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

        public List<String> getUuids(String year)
        {
            return postsByYear.get(year);
        }

        public List<String> getAllUuids()
        {
            List<String> uuids = new ArrayList<>();
            for(List<String> posts : postsByYear.values())
            {
                uuids.addAll(posts);
            }
            return uuids;
        }

        public String getPreviewPost()
        {
            if (StringUtils.isNotBlank(previewPost))
            {
                return previewPost;
            }
            return mostRecent;
        }

        public String getPreviewPost(String year)
        {
            List<String> posts = postsByYear.get(year);

            if (posts.isEmpty())
            {
                return null;
            }
            return posts.get(0);
        }



        public void addPost(Post post)
        {
            String year = getYear(post.getCreated());
            if (!postsByYear.containsKey(year))
            {
                postsByYear.put(year, new ArrayList<String>());
            }

            String uuid = post.getUuid();
            postsByYear.get(year).add(uuid);

            String link = imageManagement.getPostPreviewImageLink(post);
            if (link != null && previewPost == null)
            {
                previewPost = uuid;
            }

            if (mostRecent == null)
            {
                mostRecent = uuid;
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
}
