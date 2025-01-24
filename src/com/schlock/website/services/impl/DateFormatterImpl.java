package com.schlock.website.services.impl;

import com.schlock.website.services.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterImpl implements DateFormatter
{
    private static final String DATE_FORMAT = "EEEEE  MMMMM d, yyyy";
    private static final String SHORT_DATE_FORMAT = "MMMMM d, yyyy";
    private static final String HTML_TAG_FORMAT = "yyyy-MM-dd'T'hh:mm";
    private static final String DAY_FORMAT = "MMMMM d";
    private static final String YEAR_FORMAT = "yyyy";
    private static final String RSS_FORMAT = "EEE, dd MMM yyyy hh:mm:ss";
    private static final String W3_FORMAT = "YYYY-MM-dd'T'hh:mm:ss'+08:00'";
    private static final String TODAY_FORMAT = "MMM-d";
    private static final String DOT_FORMAT = "M.d.yyyy";
    private static final String LOWERCASE_FORMAT = "EEE MMM d, yyyy";

    public String dateFormat(Date date)
    {
        return format(DATE_FORMAT, date);
    }

    public String shortDateFormat(Date date)
    {
        return format(SHORT_DATE_FORMAT, date);
    }

    public String htmlTagFormat(Date date)
    {
        return format(HTML_TAG_FORMAT, date);
    }

    public String w3DateFormat(Date date)
    {
        return format(W3_FORMAT, date);
    }

    public String dayFormat(Date date)
    {
        return format(DAY_FORMAT, date);
    }

    public String yearFormat(Date date)
    {
        return format(YEAR_FORMAT, date);
    }

    public String rssFeedFormat(Date date)
    {
        return format(RSS_FORMAT, date) + " +0900";
    }

    public String todayArchiveFormat(Date date)
    {
        return format(TODAY_FORMAT, date).toLowerCase();
    }

    public String dotFormat(Date date)
    {
        return format(DOT_FORMAT, date);
    }

    public String lowercaseFormat(Date date)
    {
        return format(LOWERCASE_FORMAT, date).toLowerCase();
    }

    private String format(String format, Date date)
    {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public Date todayArchiveFormat(String dateString)
    {
        try
        {
            SimpleDateFormat f = new SimpleDateFormat(TODAY_FORMAT);
            Date date = f.parse(dateString);
            return date;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public String todayPrintFormat(String dateString)
    {
        Date date = todayArchiveFormat(dateString);
        return dayFormat(date);
    }
}
