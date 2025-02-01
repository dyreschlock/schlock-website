package com.schlock.website.components.blog.layout;

import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class HeaderScript
{
    @Inject
    private SiteGenerationCache siteCache;

    @Inject
    private PostDAO postDAO;

    public String getBlogDataJS()
    {
        String code = siteCache.getCachedString(SiteGenerationCache.HEADER_JAVASCRIPT);
        if (code == null)
        {
            StringBuilder sb = new StringBuilder();

            List<String> uuids = postDAO.getAllPublishedUuids();
            for(int i = 0; i < uuids.size(); i++)
            {
                String index = Integer.toString(i);
                String uuid = uuids.get(i);

                String postcode = String.format("blogLinks[%s] = \"%s\";\n", index, uuid);

                sb.append(postcode);
            }
            code = sb.toString();

            siteCache.addToStringCache(code, SiteGenerationCache.HEADER_JAVASCRIPT);
        }
        return code;
    }
}
