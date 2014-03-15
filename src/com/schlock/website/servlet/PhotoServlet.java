package com.schlock.website.servlet;

import com.schlock.website.DeploymentContext;
import com.schlock.website.services.blog.impl.PostManagementImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PhotoServlet extends HttpServlet
{
    private static final String OK = "ok";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String ok = req.getParameter(OK);
        if (ok != null)
        {
            hostPhoto(req, resp);
            return;
        }

        //if is gallery only, redirect to post using gallery

        String referrer = req.getHeader("referer");
        if (DeploymentContext.isAcceptedUrlReferrer(referrer))
        {
            hostPhoto(req, resp);
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void hostPhoto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String url = req.getRequestURL().toString();
        int photo = url.indexOf(PostManagementImpl.PHOTO_DIR);
        String relative = url.substring(photo + PostManagementImpl.PHOTO_DIR.length());

        File file = new File(photoLocation() + relative);

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

    private String photoLocation()
    {
        if (DeploymentContext.isLocal())
        {
            return PostManagementImpl.LOCAL_PHOTO_DIR;
        }
        return PostManagementImpl.HOSTED_PHOTO_DIR;
    }
}
