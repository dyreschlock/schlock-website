package com.schlock.website.services.apps;

import com.schlock.website.services.apps.notfibbage.NotfibbageManagement;
import com.schlock.website.services.apps.notfibbage.impl.NotfibbageManagementImpl;
import com.schlock.website.services.apps.subtitles.SubtitleFixerService;
import com.schlock.website.services.apps.subtitles.impl.SubtitleFixerServiceImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class ApplicationsModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(SubtitleFixerService.class, SubtitleFixerServiceImpl.class);
        binder.bind(NotfibbageManagement.class, NotfibbageManagementImpl.class);
    }
}
