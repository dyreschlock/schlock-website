package com.schlock.website.services.blog;

import com.schlock.website.services.blog.impl.*;
import org.apache.tapestry5.ioc.ServiceBinder;

public class BlogModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(PostManagement.class, PostManagementImpl.class);
        binder.bind(PostArchiveManagement.class, PostArchiveManagementImpl.class);
        binder.bind(PostContentsManagement.class, PostContentsManagementImpl.class);

        binder.bind(LayoutManagement.class, LayoutManagementImpl.class);
        binder.bind(IconManagement.class, IconManagementImpl.class);
        binder.bind(ImageManagement.class, ImageManagementImpl.class);
        binder.bind(TodayArchiveManagement.class, TodayArchiveManagementImpl.class);
        binder.bind(LessonsManagement.class, LessonsManagementImpl.class);
        binder.bind(KeywordManagement.class, KeywordManagementImpl.class);
        binder.bind(SitemapManagement.class, SitemapManagementImpl.class);
        binder.bind(MapLocationManagement.class, MapLocationManagementImpl.class);

        binder.bind(CssCache.class, CssCacheImpl.class);
        binder.bind(JavaScriptCache.class, JavaScriptCacheImpl.class);

        binder.bind(ConvertWordpress.class, ConvertWordpressImpl.class);
    }
}
