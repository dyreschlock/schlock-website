package com.schlock.website.entities.blog;

public class Post extends AbstractPost
{
    private Integer number;

    protected Post()
    {
    }

    public Post(String uuid)
    {
        super(uuid);
    }


    public boolean isPost()
    {
        return true;
    }

    public Integer getNumber()
    {
        return number;
    }

    public String getDisplayNumber()
    {
        if (number != null)
        {
            return number.toString();
        }
        return "";
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }
}
