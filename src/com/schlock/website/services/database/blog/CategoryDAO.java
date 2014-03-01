package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface CategoryDAO extends BaseDAO<Category>
{
    public List<Category> getTopCategoriesInOrder();

    public List<Category> getSubcategoriesInOrder(Long categoryId);
}
