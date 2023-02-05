package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.ImageDAO;
import org.hibernate.Session;

public class ImageDAOImpl extends BaseDAOImpl<Image> implements ImageDAO
{
    public ImageDAOImpl(Session session)
    {
        super(Image.class, session);
    }
}
