package com.schlock.website.servlet;

import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String referrer = req.getHeader("referer");
        if (deploymentContext(req).isAcceptedUrlReferrer(referrer))
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

        String imageLocation = deploymentContext(req).imageLocation();
        File file = new File(imageLocation + relative);

        if (file.exists())
        {
            FileInputStream io = new FileInputStream(file);
            int bytes = io.available();
            byte[] body = new byte[bytes];
            io.read(body);
            io.close();

            OutputStream out = resp.getOutputStream();
            resp.setContentType("image/jpeg");
            resp.setContentLength(bytes);
            out.write(body);
            out.flush();
            out.close();
        }
    }


    private DeploymentContext deploymentContext(HttpServletRequest req)
    {
        return registry(req).getService(DeploymentContext.class);
    }

    private Registry registry(HttpServletRequest req)
    {
        ServletContext context = getServletContext();
        return (Registry) context.getAttribute(TapestryFilter.REGISTRY_CONTEXT_NAME);
    }
}
