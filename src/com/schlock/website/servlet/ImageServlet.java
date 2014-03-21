package com.schlock.website.servlet;

import com.schlock.website.services.DeploymentContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class ImageServlet extends AbstractHttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String referrer = req.getHeader("referer");
        if (deploymentContext().isAcceptedUrlReferrer(referrer))
        {
            hostImage(req, resp);
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void hostImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String url = req.getRequestURL().toString();
        int photo = url.indexOf(DeploymentContext.IMAGE_DIR);
        String relative = url.substring(photo + DeploymentContext.IMAGE_DIR.length());

        String imageLocation = deploymentContext().imageLocation();
        File file = new File(imageLocation + relative);

        String type = getContentType(relative);

        hostFile(file, type, resp);
    }
}
