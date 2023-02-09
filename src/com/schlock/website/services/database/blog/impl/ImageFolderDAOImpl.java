package com.schlock.website.services.database.blog.impl;

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

        String text = " from ImageFolder f " +
                " where f.folderName = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", ROOT_FOLDER_NAME);

        return (ImageFolder) singleResult(query);
    }

    public ImageFolder getFolderByNameParentId(String name, String parentId)
    {
        String text = " select child " +
                " from ImageFolder child " +
                " join child.parent par " +
                " where par.googleId = :parentId " +
                " and child.folderName = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", name);
        query.setParameter("parentId", parentId);

        return (ImageFolder) singleResult(query);
    }
}
