package com.schlock.website.servlet;

import com.schlock.website.services.blog.impl.PostManagementImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PhotoServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String url = req.getRequestURL().toString();
        int photo = url.indexOf(PostManagementImpl.PHOTO_DIR);
        String relative = url.substring(photo + PostManagementImpl.PHOTO_DIR.length());

        File file = new File(PostManagementImpl.LOCAL_PHOTO_DIR + relative);

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
}
