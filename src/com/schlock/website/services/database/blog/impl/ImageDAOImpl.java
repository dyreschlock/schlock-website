package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.ImageDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class ImageDAOImpl extends BaseDAOImpl<Image> implements ImageDAO
{
    public ImageDAOImpl(Session session)
    {
        super(Image.class, session);
    }


    public List<Image> getByGallery(String galleryName)
    {
        String text = "from Image i " +
                " where i.galleryName = :galleryName " +
                " order by imageName ";

        Query query = session.createQuery(text);
        query.setParameter("galleryName", galleryName);

        return query.list();
    }
}
