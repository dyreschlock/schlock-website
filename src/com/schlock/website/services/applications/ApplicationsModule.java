package com.schlock.website.services.applications;

import com.schlock.website.services.applications.subtitles.SubtitleFixerService;
import com.schlock.website.services.applications.subtitles.impl.SubtitleFixerServiceImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class ApplicationsModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(SubtitleFixerService.class, SubtitleFixerServiceImpl.class);
    }
}
