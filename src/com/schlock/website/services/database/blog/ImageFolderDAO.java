package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.ImageFolder;
import com.schlock.website.services.database.BaseDAO;

public interface ImageFolderDAO extends BaseDAO<ImageFolder>
{
    ImageFolder getRoot();

    ImageFolder getFolderByNameParentId(String name, String parentId);
}
