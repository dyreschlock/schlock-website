package com.schlock.website.services.impl;

import com.schlock.website.services.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterImpl implements DateFormatter
{
    private static final String DATE_FORMAT = "EEEEE  MMMMM d, yyyy";
    private static final String HTML_TAG_FORMAT = "yyyy-MM-dd'T'hh:mm";
    private static final String DAY_FORMAT = "MMMMM d";


    public String dateFormat(Date date)
    {
        return format(DATE_FORMAT, date);
    }

    public String htmlTagFormat(Date date)
    {
        return format(HTML_TAG_FORMAT, date);
    }

    public String dayFormat(Date date)
    {
        return format(DAY_FORMAT, date);
    }

    private String format(String format, Date date)
    {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }
}
