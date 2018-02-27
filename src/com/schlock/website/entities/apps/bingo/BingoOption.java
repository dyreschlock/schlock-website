package com.schlock.website.entities.apps.bingo;

import com.schlock.website.entities.Persisted;

public class BingoOption extends Persisted
{
    private String sheet;
    private String course;
    private String category;
    private boolean skip;

    private String entry;


    public String getSheet() { return sheet; }

    public void setSheet(String sheet) { this.sheet = sheet; }

    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public boolean isSkip() { return skip; }

    public void setSkip(boolean skip) { this.skip = skip; }

    public String getEntry() { return entry; }

    public void setEntry(String entry) { this.entry = entry; }
}