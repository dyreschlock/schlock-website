package com.schlock.website.services;

import java.util.Date;

public interface DateFormatter
{
    public String dateFormat(Date date);

    public String htmlTagFormat(Date date);

    public String dayFormat(Date date);
}
