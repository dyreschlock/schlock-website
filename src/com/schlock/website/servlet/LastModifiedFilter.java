package com.schlock.website.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LastModifiedFilter implements Filter
{
    private static final String LAST_MODIFIED_HEADER = "Last-Modified";

    private long lastModifiedDate;

    public void init(FilterConfig filterConfig) throws ServletException
    {
        long oneday = 24 * 60 * 60 * 1000;

        lastModifiedDate = System.currentTimeMillis() - oneday;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        if (response instanceof HttpServletResponse)
        {
            ((HttpServletResponse) response).addDateHeader(LAST_MODIFIED_HEADER, lastModifiedDate);
        }
        chain.doFilter(request, response);
    }

    public void destroy()
    {
    }
}
