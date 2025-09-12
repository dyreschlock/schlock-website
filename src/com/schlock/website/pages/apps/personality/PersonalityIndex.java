package com.schlock.website.pages.apps.personality;

import com.schlock.website.entities.apps.personality.Entry;
import org.apache.tapestry5.annotations.Persist;

import java.util.Arrays;
import java.util.List;

public class PersonalityIndex
{
    private static final List<String> DATA_STRING = Arrays.asList(
            "Jim Hendricks,m,INTJ-T,Architect,Introverted,80,Intuitive,76,Thinking,52,Judging,69,Turbulent,61,Analyst,Constant Improvement"
    );


    @Persist
    private Integer person;

    Object onActivate()
    {
        return true;
    }

    Object onActivate(String param)
    {
        this.person = Integer.parseInt(param);

        return true;
    }

    public Integer getPerson()
    {
        if (person == null)
        {
            this.person = 0;
        }
        return person;
    }

    public Entry getEntry()
    {
        String data = DATA_STRING.get(getPerson());

        Entry entry = new Entry(data);
        return entry;
    }


}
