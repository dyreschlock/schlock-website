package com.schlock.website.services;

import java.util.Date;

public interface DateFormatter
{
    String dateFormat(Date date);

    String htmlTagFormat(Date date);

    String w3DateFormat(Date date);

    String dayFormat(Date date);

    String yearFormat(Date date);

    String rssFeedFormat(Date date);

    String todayArchiveFormat(Date date);

    String todayPrintFormat(String dateString);
}
