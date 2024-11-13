package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.SitemapManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SitemapManagementImpl implements SitemapManagement
{
    private final PostArchiveManagement archiveManagement;

    private final CategoryDAO categoryDAO;
    private final PostDAO postDAO;

    public SitemapManagementImpl(PostArchiveManagement archiveManagement,
                                 CategoryDAO categoryDAO,
                                 PostDAO postDAO)
    {
        this.archiveManagement = archiveManagement;

        this.categoryDAO = categoryDAO;
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
        Long allCategories = null;

        List<Post> posts = postDAO.getMostRecentPosts(allPosts, unpublished, allYears, allMonths, allCategories);

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

        for(CourseCategory cat : categoryDAO.getCourseInOrder())
        {
            for(AbstractPost page : postDAO.getAllCoursesByCategory(cat.getId()))
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
        final String CATEGORY = "category";

        List<String> pages = new ArrayList<>();

        for(PostCategory cat : categoryDAO.getTopInOrder())
        {
            pages.add(CATEGORY + "/" + cat.getUuid() + ".html");

            for(PostCategory sub : categoryDAO.getSubInOrder(cat.getId()))
            {
                pages.add(CATEGORY + "/" + sub.getUuid() + ".html");
            }
        }
        return pages;
    }

    private List<String> getProjectPages()
    {
        final String PROJECTS = "projects";

        List<String> pages = new ArrayList<>();
        pages.add(PROJECTS + "/index.html");

        for(ProjectCategory cat : categoryDAO.getTopProjectInOrder())
        {
            pages.add(PROJECTS + "/" + cat.getUuid() + ".html");

            for(ProjectCategory sub : categoryDAO.getSubProjectInOrder(cat.getId()))
            {
                pages.add(PROJECTS + "/" + sub.getUuid() + ".html");
            }
        }
        return pages;
    }
}
