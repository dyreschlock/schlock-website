package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.ImageDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class ImageDAOImpl extends BaseDAOImpl<Image> implements ImageDAO
{
    public ImageDAOImpl(Session session)
    {
        super(Image.class, session);
    }


    public Image getByDirectoryGalleryName(String directory, String galleryName, String imageName)
    {
        String text = " from Image i" +
                        " where i.directory = :directory " +
                        " and i.imageName = :imageName ";

        if (StringUtils.isBlank(galleryName))
        {
            text += " and i.galleryName is null ";
        }
        else
        {
            text += " and i.galleryName = :galleryName ";
        }

        Query query = session.createQuery(text);
        query.setParameter("directory", directory);
        query.setParameter("imageName", imageName);

        if (StringUtils.isNotBlank(galleryName))
        {
            query.setParameter("galleryName", galleryName);
        }

        return (Image) singleResult(query);
    }

    public List<Image> getByGallery(String galleryName)
    {
        String text = " from Image i " +
                        " where i.galleryName = :galleryName " +
                        " order by imageName ";

        Query query = session.createQuery(text);
        query.setParameter("galleryName", galleryName);

        return query.list();
    }

    public List<Image> getAllWithoutGooleId()
    {
        String text = " from Image i " +
                " where i.googleId is null ";

        Query query = session.createQuery(text);

        return query.list();
    }
}
