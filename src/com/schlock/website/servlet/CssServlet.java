package com.schlock.website.servlet;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.services.Context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class CssServlet extends HttpServlet
{
    private final static String BLOG_LESS = "layout/blog.less";
    private final static String LAYOUT_CSS = "layout/layout.css";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String css = createCss(req);

        byte[] bytes = css.getBytes();

        OutputStream out = resp.getOutputStream();
        resp.setContentType("text/css");
        resp.setContentLength(bytes.length);
        out.write(bytes);
        out.flush();
        out.close();
    }

    private String createCss(HttpServletRequest req) throws IOException
    {
        String css = "";

        css += getFileAsString(req, BLOG_LESS);
        css += getFileAsString(req, LAYOUT_CSS);

        css = convertLessToCss(css);

        return css;
    }

    private String getFileAsString(HttpServletRequest req, String path) throws IOException
    {
        String css = "";

        File resource = context(req).getRealFile(path);
        if (resource.exists())
        {
            BufferedReader io = new BufferedReader(new FileReader(resource));

            String line = io.readLine();
            while (line != null)
            {
                css += line;
                css += "\r\n";

                line = io.readLine();
            }
        }
        return css;
    }

    private String convertLessToCss(String less)
    {
        LessEngine engine = new LessEngine();
        try
        {
            String css = engine.compile(less);
            return css;
        }
        catch (LessException e)
        {
            throw new RuntimeException(e);
        }
    }


    private Context context(HttpServletRequest req)
    {
        return registry(req).getService(Context.class);
    }

    private Registry registry(HttpServletRequest req)
    {
        ServletContext context = getServletContext();
        return (Registry) context.getAttribute(TapestryFilter.REGISTRY_CONTEXT_NAME);
    }
}
