package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

public class Keyword extends Persisted
{
    public static final String DELIMITER = ",";

    private String name;
    private String title;
    private String description;

    private KeywordType type;

    private Keyword parent;
    private Integer ordering;


    protected Keyword()
    {
    }

    public Keyword(String name)
    {
        this.name = name;
    }

    public boolean isTopKeyword()
    {
        return parent == null;
    }

    public boolean isVisible()
    {
        return type != null && type.equals(KeywordType.VISIBLE);
    }




    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public KeywordType getType()
    {
        return type;
    }

    public void setType(KeywordType type)
    {
        this.type = type;
    }

    public Keyword getParent()
    {
        return parent;
    }

    public void setParent(Keyword parent)
    {
        this.parent = parent;
    }

    public Integer getOrdering()
    {
        return ordering;
    }

    public void setOrdering(Integer ordering)
    {
        this.ordering = ordering;
    }
}
