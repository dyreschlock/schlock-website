package com.schlock.website.components.blog.layout;

import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class HeaderScript
{
    @Inject
    private PostDAO postDAO;

    public String getBlogDataJS()
    {
        String code = "";

        List<String> uuids = postDAO.getAllPublishedUuids();
        for(int i = 0; i < uuids.size(); i++)
        {
            String index = Integer.toString(i);
            String uuid = uuids.get(i);

            String postcode = String.format("blogLinks[%s] = \"%s\";\n", index, uuid);

            code += postcode;
        }
        return code;
    }
}
