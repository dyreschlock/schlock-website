package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface ImageDAO extends BaseDAO<Image>
{
    List<Image> getByGallery(String galleryName);

    List<Image> getAllWithoutGooleId();
}
