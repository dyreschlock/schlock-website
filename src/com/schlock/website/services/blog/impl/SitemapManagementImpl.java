package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.SitemapManagement;
import com.schlock.website.services.blog.TodayArchiveManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SitemapManagementImpl implements SitemapManagement
{
    private final TodayArchiveManagement calendarArchiveManagement;
    private final PostArchiveManagement archiveManagement;

    private final KeywordDAO keywordDAO;
    private final PostDAO postDAO;

    public SitemapManagementImpl(TodayArchiveManagement calendarArchiveManagement,
                                 PostArchiveManagement archiveManagement,
                                 KeywordDAO keywordDAO,
                                 PostDAO postDAO)
    {
        this.calendarArchiveManagement = calendarArchiveManagement;
        this.archiveManagement = archiveManagement;

        this.keywordDAO = keywordDAO;
        this.postDAO = postDAO;
    }


    public List<String> getAllUrlsToIndex()
    {
        List<String> urls = new ArrayList<>();

        urls.addAll(getPostsAndPages());
        urls.addAll(getDirectoryPages());

        return urls;
    }



    public List<String> getPostsAndPages()
    {
        List<String> posts = new ArrayList<>();

        posts.addAll(getPosts());
        posts.addAll(getPages());
        posts.addAll(getCoursePages());
        return posts;
    }

    private List<String> getPosts()
    {
        List<String> pages = new ArrayList<>();

        Integer allPosts = null;
        boolean unpublished = false;
        Integer allYears = null;
        Integer allMonths = null;
        String allKeywords = null;

        List<Post> posts = postDAO.getMostRecentPosts(allPosts, unpublished, allYears, allMonths, allKeywords);

        for(Post post : posts)
        {
            pages.add(post.getUuid() + ".html");
        }
        return pages;
    }

    private List<String> getPages()
    {
        List<String> skip = Arrays.asList("courses", "projects");

        List<String> pages = new ArrayList<>();

        for(Page page : postDAO.getAllPages(false))
        {
            String pageUuid = page.getUuid();
            if (!skip.contains(pageUuid))
            {
                pages.add(pageUuid + ".html");
            }
        }
        return pages;
    }

    private List<String> getCoursePages()
    {
        final String COURSES = "courses";

        List<String> pages = new ArrayList<>();
        pages.add(COURSES + "/index.html");

        for(Keyword keyword : keywordDAO.getTopCourseKeywordsInOrder())
        {
            for(AbstractPost page : postDAO.getAllCoursesByKeyword(keyword.getName()))
            {
                pages.add(COURSES + "/" + page.getUuid() + ".html");
            }
        }
        return pages;
    }


    public List<String> getDirectoryPages()
    {
        List<String> pages = new ArrayList<>();

        pages.addAll(getArchivePages());
        pages.addAll(getCategoryPages());
        pages.addAll(getProjectPages());
        pages.addAll(getCalendarPages());
        return pages;
    }

    private List<String> getArchivePages()
    {
        final String ARCHIVE = "archive";

        List<String> pages = new ArrayList<>();
        pages.add(ARCHIVE + "/index.html");

        for(String year : archiveManagement.getYearlyMonthlyIterations(null, null))
        {
            pages.add(ARCHIVE + "/" + year + "/index.html");

            Integer y = Integer.parseInt(year);
            for(String yearMonth : archiveManagement.getYearlyMonthlyIterations(y, null))
            {
                String path = yearMonth.replace("-", "/");

                pages.add(ARCHIVE + "/" + path + ".html");
            }
        }
        return pages;
    }

    private List<String> getCategoryPages()
    {
        final String KEYWORD = "keyword";

        List<String> pages = new ArrayList<>();

        for(Keyword keyword : keywordDAO.getTopPostKeywordsInOrder())
        {
            pages.add(KEYWORD + "/" + keyword.getName() + ".html");

            for(Keyword subkeyword : keywordDAO.getSubInOrder(keyword))
            {
                pages.add(KEYWORD + "/" + subkeyword.getName() + ".html");
            }
        }
        return pages;
    }

    private List<String> getProjectPages()
    {
        final String PROJECTS = "projects";

        List<String> pages = new ArrayList<>();
        pages.add(PROJECTS + "/index.html");

        for(Keyword keyword : keywordDAO.getTopProjectKeywordsInOrder())
        {
            pages.add(PROJECTS + "/" + keyword.getName() + ".html");

            for(Keyword subkeyword : keywordDAO.getSubInOrder(keyword))
            {
                pages.add(PROJECTS + "/" + subkeyword.getName() + ".html");
            }
        }
        return pages;
    }

    private List<String> getCalendarPages()
    {
        final String TODAY = "today";

        List<String> pages = new ArrayList<>();
        pages.add(TODAY + "/index.html");

        for(String month : calendarArchiveManagement.getAllMonths())
        {
            for(String day : calendarArchiveManagement.getAllDays(month))
            {
                String dateStringPage = month + "-" + day + ".html";
                pages.add(TODAY + "/" + dateStringPage);
            }
        }
        return pages;
    }
}
