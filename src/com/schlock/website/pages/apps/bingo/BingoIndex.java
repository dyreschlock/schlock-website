package com.schlock.website.pages.apps.bingo;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class BingoIndex
{
    private static final String FIFTH_GRADE_PARAM = "5th";
    private static final String VOCAB_PARAM = "vocab";

    private static final String HIGH_SCHOOL_PARAM = "hs";
    private static final String SELF_INTRO_PARAM = "self-intro";


    @Persist
    @Property
    private Boolean displayFifthGradeVocab;

    @Persist
    @Property
    private Boolean displayHighSchoolSelfIntro;


    Object onActivate()
    {
        return onActivate(null, null);
    }

    Object onActivate(String parameter)
    {
        return onActivate(parameter, null);
    }

    Object onActivate(String param1, String param2)
    {
        resetFlags();

        if (StringUtils.isNotEmpty(param1) &&
                StringUtils.isNotEmpty(param2))
        {
            if (StringUtils.equalsIgnoreCase(param1, HIGH_SCHOOL_PARAM) &&
                StringUtils.equalsIgnoreCase(param2, SELF_INTRO_PARAM))
            {
                displayHighSchoolSelfIntro = true;
            }
            else if (StringUtils.equalsIgnoreCase(param1, FIFTH_GRADE_PARAM) &&
                     StringUtils.equalsIgnoreCase(param2, VOCAB_PARAM))
            {
                displayFifthGradeVocab = true;
            }
            else
            {
                displayFifthGradeVocab = true;
            }
        }
        else
        {
            displayFifthGradeVocab = true;
        }
        return true;
    }

    private void resetFlags()
    {
        displayFifthGradeVocab = false;
        displayHighSchoolSelfIntro = false;
    }
}
