package com.schlock.website.services;

import java.util.Date;

public interface DateFormatter
{
    String dateFormat(Date date);

    String shortDateFormat(Date date);

    String htmlTagFormat(Date date);

    String w3DateFormat(Date date);

    String dayFormat(Date date);

    String yearFormat(Date date);

    String rssFeedFormat(Date date);

    String todayArchiveFormat(Date date);

    String dotFormat(Date date);

    String lowercaseFormat(Date date);

    Date todayArchiveFormat(String dateString);

    String todayPrintFormat(String dateString);
}
