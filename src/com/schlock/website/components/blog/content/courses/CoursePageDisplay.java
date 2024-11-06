package com.schlock.website.components.blog.content.courses;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;

import javax.inject.Inject;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class CoursePageDisplay
{
    @Parameter(required = true)
    private Page page;

    @Inject
    private PostManagement postManagement;


    public boolean isDisplayContent()
    {
        if (page != null)
        {
            try
            {
                String htmlFile = page.getUuid() + ".html";
                URL url = this.getClass().getResource(htmlFile);

                return new File(url.toURI()).exists();
            }
            catch (URISyntaxException e)
            {
            }
        }
        return false;
    }

    public String getHtmlContents()
    {
        String html = "";
        if (page != null)
        {
            String htmlFile = page.getUuid() + ".html";

            String contents = readFileContents(htmlFile);
            html = postManagement.generatePostHTML(contents);
        }
        return html;
    }


    private String readFileContents(String filepath)
    {
        String content = "";
        try
        {
            InputStream in = this.getClass().getResourceAsStream(filepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = reader.readLine();
            while (line != null)
            {
                content += line;

                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return content;
    }
}
