package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.ImageFolder;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.ImageFolderDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class ImageFolderDAOImpl extends BaseDAOImpl<ImageFolder> implements ImageFolderDAO
{
    public ImageFolderDAOImpl(Session session)
    {
        super(ImageFolder.class, session);
    }

    public ImageFolder getRoot()
    {
        final String ROOT_FOLDER_NAME = "www";

        return getByName(ROOT_FOLDER_NAME);
    }

    public ImageFolder getWebpFolder()
    {
        return getByName(Image.WEBP_FOLDER_NAME);
    }

    public ImageFolder getByName(String name)
    {
        String text = " from ImageFolder i " +
                " where i.folderName = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", name);

        return (ImageFolder) singleResult(query);
    }

    public ImageFolder getFolderByNameParentGoogleId(String name, String parentGoogleId)
    {
        String text = " select child " +
                " from ImageFolder child " +
                " join child.parent par " +
                " where par.googleId = :parentId " +
                " and child.folderName = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", name);
        query.setParameter("parentId", parentGoogleId);

        return (ImageFolder) singleResult(query);
    }
}
