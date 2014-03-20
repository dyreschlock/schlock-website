package com.schlock.website.servlet;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PhotoServlet extends HttpServlet
{
    private static final String OK = "ok";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Post post = getPostByGalleryName(req);
        if (post != null)
        {
            redirectToGallery(post, req, resp);
            return;
        }

        String ok = req.getParameter(OK);
        if (ok != null)
        {
            hostPhoto(req, resp);
            return;
        }

        String referrer = req.getHeader("referer");
        if (deploymentContext(req).isAcceptedUrlReferrer(referrer))
        {
            hostPhoto(req, resp);
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private Post getPostByGalleryName(HttpServletRequest req)
    {
        int HTTP = 0;
        int SLASH = 1;
        int HOST = 2;
        int PHOTO = 3;
        int GALLERY = 4;
        int IMAGE = 5;

        String url = req.getRequestURL().toString();

        String[] parts = url.split("/");
        if (parts.length > IMAGE)
        {
            String part = parts[IMAGE];
            if (StringUtils.isNotBlank(part))
            {
                return null;
            }
        }
        if (parts.length > GALLERY)
        {
            String gallery = parts[GALLERY];
            Post post = postDAO(req).getByGalleryName(gallery);
            return post;
        }
        return null;
    }

    private void redirectToGallery(Post post, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String uuid = post.getUuid();
        String redirect = linkSource(req).createPageRenderLinkWithContext(Index.class, uuid).toURI();

        resp.sendRedirect(redirect);
    }

    private void hostPhoto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String url = req.getRequestURL().toString();
        int photo = url.indexOf(DeploymentContext.PHOTO_DIR);
        String relative = url.substring(photo + DeploymentContext.PHOTO_DIR.length());

        String photoLocation = deploymentContext(req).photoLocation();
        File file = new File(photoLocation + relative);

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

    private PostDAO postDAO(HttpServletRequest req)
    {
        return registry(req).getService(PostDAO.class);
    }

    private DeploymentContext deploymentContext(HttpServletRequest req)
    {
        return registry(req).getService(DeploymentContext.class);
    }

    private PageRenderLinkSource linkSource(HttpServletRequest req)
    {
        return registry(req).getService(PageRenderLinkSource.class);
    }

    private Registry registry(HttpServletRequest req)
    {
        ServletContext context = getServletContext();
        return (Registry) context.getAttribute(TapestryFilter.REGISTRY_CONTEXT_NAME);
    }
}
