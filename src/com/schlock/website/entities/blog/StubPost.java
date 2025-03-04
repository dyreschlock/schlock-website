package com.schlock.website.entities.blog;

public class StubPost extends Post
{

    public boolean isStubPost()
    {
        return true;
    }

    public Integer getNumber()
    {
        return 0;
    }

    public String getDisplayNumber()
    {
        return "—";
    }
}
