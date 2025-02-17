package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.blog.PostContentsManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.List;

public class PostContentsManagementImpl implements PostContentsManagement
{
    private static final String COURSES = "courses";
    private static final String PAGES = "pages";
    private static final String POSTS = "posts";

    private static final String FILE_EXT = ".html";

    private final PostManagement postManagement;
    private final PostDAO postDAO;

    private final DateFormatter dateFormatter;
    private final SiteGenerationCache siteCache;
    private final DeploymentContext context;

    public PostContentsManagementImpl(PostManagement postManagement,
                                        PostDAO postDAO,
                                        DateFormatter dateFormatter,
                                        SiteGenerationCache siteCache,
                                        DeploymentContext context)
    {
        this.postManagement = postManagement;
        this.postDAO = postDAO;

        this.dateFormatter = dateFormatter;
        this.siteCache = siteCache;
        this.context = context;
    }


    public String getHTMLContents(Page page)
    {
        String html = "";
        if (page != null)
        {
            html = siteCache.getCachedString(SiteGenerationCache.RAW_POST_HTML, page.getUuid());
            if (StringUtils.isBlank(html))
            {
                html = readHTMLContents(page);

                siteCache.addToStringCache(html, SiteGenerationCache.RAW_POST_HTML, page.getUuid());
            }
        }
        return html;
    }

    private String readHTMLContents(Page page)
    {
        String filepath = getFilepath(page);

        String contents = readFileContents(filepath);
        return postManagement.generatePostHTML(contents);
    }

    private String getFilepath(AbstractPost post)
    {
        String htmlFile = post.getUuid() + FILE_EXT;
        if (post.isCoursePage())
        {
            return context.blogContentInputDirectory() + "/" + COURSES + "/" + htmlFile;
        }
        if(post.isPage())
        {
            return context.blogContentInputDirectory() + "/" + PAGES + "/" + htmlFile;
        }

        String year = dateFormatter.yearFormat(post.getCreated());
        return context.blogContentInputDirectory() + "/" + POSTS + "/" + year + "/" + htmlFile;
    }

    private final String TABLE_START = "<table";
    private final String TABLE_END = "</table>";

    private final String NEW_LINE = "\r\n";

    private String readFileContents(String filepath)
    {
        StringBuilder content = new StringBuilder();
        try
        {
            InputStream in = new FileInputStream(filepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            boolean withinTable = false;

            String line = reader.readLine();
            while (line != null)
            {
                if (line.startsWith(TABLE_START))
                {
                    withinTable = true;
                }
                if (line.startsWith(TABLE_END))
                {
                    withinTable = false;
                }

                content.append(line);
                if (!withinTable)
                {
                    content.append(NEW_LINE);
                }

                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return content.toString();
    }


    public void writeHTMLFilesForPosts()
    {
        List<AbstractPost> posts = postDAO.getAll();
        for(AbstractPost post : posts)
        {
            writeHTMLFilesForPosts(post);
        }
    }

    private void writeHTMLFilesForPosts(AbstractPost post)
    {
        String filepath = getFilepath(post);
        File file = new File(filepath);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
//                FileUtils.writeStringToFile(file, post.getBodyText());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
