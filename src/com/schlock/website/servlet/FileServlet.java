package com.schlock.website.servlet;

import com.schlock.website.services.DeploymentContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class FileServlet extends AbstractHttpServlet
{
    private final static String[] ACCEPTED_LOCATIONS =
            new String[]{DeploymentContext.MISC_DIR, DeploymentContext.PAGE_DIR, DeploymentContext.SPAMM_DIR};

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String relativePath = relativePath(req);
        if (StringUtils.startsWithAny(relativePath.toLowerCase(), ACCEPTED_LOCATIONS))
        {
            hostFile(relativePath, resp);
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void hostFile(String relativePath, HttpServletResponse response) throws IOException
    {
        File file = new File(deploymentContext().webDirectory() + relativePath);
        String contentType = getContentType(relativePath);

        hostFile(file, contentType, response);
    }


    private String relativePath(HttpServletRequest req)
    {
        String url = req.getRequestURL().toString();

        int slash = url.substring(10).indexOf("/") + 10;

        String relativePath = url.substring(slash + 1);
        return relativePath;
    }
}
