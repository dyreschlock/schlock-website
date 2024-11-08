package com.schlock.website.servlet;

import com.schlock.website.services.blog.CssCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class CssServlet extends AbstractHttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String css = "";//css().getSecondaryCss();

        byte[] bytes = css.getBytes();

        OutputStream out = resp.getOutputStream();
        resp.setContentType("text/css");
        resp.setContentLength(bytes.length);

        if (!deploymentContext().isLocal())
        {
            // 604800 seconds = 1 week
            long week = 604800l;

            resp.addHeader("Cache-Control", "max-age=" + Long.toString(week));
            resp.addHeader("Vary", "Accept-Encoding");

            Calendar cal = Calendar.getInstance();
            long now = cal.getTimeInMillis();

            long weekAgo = now - week;
            resp.addDateHeader("Last-Modified", weekAgo);
        }

        out.write(bytes);
        out.flush();
        out.close();
    }


    private CssCache css()
    {
        return registry().getService(CssCache.class);
    }
}
