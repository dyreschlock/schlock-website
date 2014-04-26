package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface CategoryDAO extends BaseDAO<Category>
{
    public Category getByUuid(String uuid);

    public List<Category> getAllInOrder();

    public List<Category> getTopInOrder();

    public List<Category> getSubInOrder(Long categoryId);
}
