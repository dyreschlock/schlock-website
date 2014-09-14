package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface CategoryDAO extends BaseDAO<AbstractCategory>
{
    public AbstractCategory getByUuid(String uuid);

    public PostCategory getFirstCategory();

    public List<PostCategory> getAllInOrder();

    public List<PostCategory> getTopInOrder();

    public List<PostCategory> getSubInOrder(Long categoryId);
}
