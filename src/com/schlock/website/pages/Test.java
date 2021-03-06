package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test
{
    @Inject
    private DeploymentContext deploymentContext;

    @Inject
    private PostDAO postDAO;


    @Property
    private String currentFolder;


    Object onActivate()
    {
        boolean local = deploymentContext.isLocal();
        if(!local)
        {
            return Index.class;
        }
        return true;
    }



    public List<String> getFolders()
    {
        String photo = deploymentContext.photoLocation();
        File directory = new File(photo);

        List<String> folders = new ArrayList<String>();
        for (File file : directory.listFiles())
        {
            if (file.isDirectory())
            {
                String name = file.getName();
                AbstractPost post = postDAO.getByGalleryName(name);
                if(post == null)
                {
                    folders.add(name);
                }
            }
        }
        return folders;
    }
}
