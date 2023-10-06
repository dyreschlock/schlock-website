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
            String num = number.toString();
            if (num.length() == 1)
            {
                return "00" + num;
            }
            if (num.length() == 2)
            {
                return "0" + num;
            }
            return num;
        }
        return "";
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }
}
