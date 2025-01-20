package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.CourseCategory;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface CategoryDAO extends BaseDAO<AbstractCategory>
{
    AbstractCategory getByUuid(Class cls, String uuid);

    PostCategory getFirstCategory();

    List<PostCategory> getTopInOrder();

    List<PostCategory> getSubInOrder(Long categoryId);

    List<PostCategory> getAllPostCategoriesInAlphaOrder();

    List<ProjectCategory> getTopProjectInOrder();

    List<ProjectCategory> getSubProjectInOrder();

    List<ProjectCategory> getSubProjectInOrder(Long categoryId);

    List<CourseCategory> getCourseInOrder();
}
