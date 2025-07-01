package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.DeploymentContext;
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

    public List<Image> getByGalleryOrderByNameDesc(String galleryName)
    {
        String text = " from Image i " +
                        " where i.galleryName = :galleryName " +
                        " order by imageName desc";

        Query query = session.createQuery(text);
        query.setParameter("galleryName", galleryName);

        return query.list();
    }

    public List<Image> getFavorites()
    {
        final int LIMIT = 1503; //factor of 9 near 1500

        String text = " from Image i " +
                        " where i.favorite > :not_favorite " +
                        " order by i.favorite desc, i.filenameHash desc ";

        Query query = session.createQuery(text);
        query.setParameter("not_favorite", Image.NOT_FAVORITE);
        query.setMaxResults(LIMIT);

        return query.list();
    }

    public List<Image> getImagesInGalleriesButNoPosts()
    {
        String PHOTO_DIR = DeploymentContext.PHOTO_DIR;
        PHOTO_DIR = PHOTO_DIR.substring(0, PHOTO_DIR.length() - 1);

        String text = " from Image i " +
                        " where i.directory = :gallery " +
                        " and i.postUuid is null ";

        Query query = session.createQuery(text);
        query.setParameter("gallery", PHOTO_DIR);

        return query.list();
    }
}
