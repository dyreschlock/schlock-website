package com.schlock.website.services.blog;

import com.schlock.website.services.blog.impl.*;
import org.apache.tapestry5.ioc.ServiceBinder;

public class BlogModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(LayoutManagement.class, LayoutManagementImpl.class);
        binder.bind(IconManagement.class, IconManagementImpl.class);
        binder.bind(PostManagement.class, PostManagementImpl.class);
        binder.bind(ImageManagement.class, ImageManagementImpl.class);
        binder.bind(PostArchiveManagement.class, PostArchiveManagementImpl.class);
        binder.bind(LessonsManagement.class, LessonsManagementImpl.class);
        binder.bind(KeywordManagement.class, KeywordManagementImpl.class);

        binder.bind(CssCache.class, CssCacheImpl.class);

        binder.bind(ConvertWordpress.class, ConvertWordpressImpl.class);
    }
}
