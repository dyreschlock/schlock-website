package com.schlock.website.servlet;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHttpServlet extends HttpServlet
{
    protected String getContentType(String filePath)
    {
        int index = filePath.lastIndexOf(".");

        String fileExtension = filePath.substring(index + 1);
        String type = contentTypes().get(fileExtension);
        return type;
    }


    protected Map<String, String> contentTypes()
    {
        Map<String, String> contentTypes = new HashMap<String, String>();

        contentTypes.put("pdf", "application/pdf");
        contentTypes.put("zip", "application/zip");

        contentTypes.put("doc", "application/msword");
        contentTypes.put("xls", "application/vnd.ms-excel");

        contentTypes.put("mp3", "audio/mpeg");
        contentTypes.put("mpg", "video/mpeg");

        contentTypes.put("gif", "image/gif");
        contentTypes.put("jpeg", "image/jpeg");
        contentTypes.put("jpg", "image/jpeg");
        contentTypes.put("psd", "image/psd");

        contentTypes.put("htm", "text/html");
        contentTypes.put("html", "text/html");
        contentTypes.put("txt", "text/plain");

        return contentTypes;
    }


    protected void hostFile(File file, String contentType, HttpServletResponse resp) throws IOException
    {
        if (file.exists() && !file.isDirectory())
        {
            byte[] body = FileUtils.readFileToByteArray(file);

            OutputStream out = resp.getOutputStream();
            resp.setContentType(contentType);
            resp.setContentLength(body.length);

            // 2592000 seconds = 30 days
            resp.addHeader("Cache-Control", "max-age=2592000");
            resp.addHeader("Vary", "Accept-Encoding");

            long lastModified = file.lastModified();
            resp.setDateHeader("Last-Modified", lastModified);

            out.write(body);
            out.flush();
            out.close();
        }
    }


    protected PostDAO postDAO()
    {
        return registry().getService(PostDAO.class);
    }

    protected DeploymentContext deploymentContext()
    {
        return registry().getService(DeploymentContext.class);
    }

    protected PageRenderLinkSource linkSource()
    {
        return registry().getService(PageRenderLinkSource.class);
    }

    protected Registry registry()
    {
        ServletContext context = getServletContext();
        return (Registry) context.getAttribute(TapestryFilter.REGISTRY_CONTEXT_NAME);
    }
}
