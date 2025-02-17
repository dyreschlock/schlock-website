package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.blog.PostContentsManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;

import java.io.*;

public class PostContentsManagementImpl implements PostContentsManagement
{
    private final PostManagement postManagement;

    private final SiteGenerationCache siteCache;
    private final DeploymentContext context;

    public PostContentsManagementImpl(PostManagement postManagement,
                                        SiteGenerationCache siteCache,
                                        DeploymentContext context)
    {
        this.postManagement = postManagement;

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
        String htmlFile = page.getUuid() + ".html";
        String filepath = context.blogContentInputDirectory() + "/courses/" + htmlFile;

        String contents = readFileContents(filepath);
        return postManagement.generatePostHTML(contents);
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
}
