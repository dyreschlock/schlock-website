package com.schlock.website.entities.apps.ps2;

public enum PlaystationGameParental
{
    ESRB_E("esrb/e", "E"),
    ESRB_3("esrb/3", "eC"),
    ESRB_10("esrb/10", "E+"),
    ESRB_M("esrb/17", "M"),
    ESRB_AO("esrb/18", "Ao"),
    ESRB_T("esrb/teen", "T"),
    CERO_A("cero/a", "A"),
    CERO_B("cero/b", "B"),
    CERO_C("cero/c", "C"),
    CERO_D("cero/d", "D"),
    CERO_Z("cero/z", "Z"),
    CERO_DEMO("cero/demo", "Demo"),
    CERO_PEND("cero/pending", "Pending"),
    PEGI_3("pegi/3", "3"),
    PEGI_7("pegi/7", "7"),
    PEGI_12("pegi/12", "12"),
    PEGI_16("pegi/16", "16"),
    PEGI_18("pegi/18", "18");

    private final String parental;
    private final String parentalText;

    PlaystationGameParental(String parental, String parentalText)
    {
        this.parental = parental;
        this.parentalText = parentalText;
    }

    public String parental()
    {
        return this.parental;
    }

    public String parentalText()
    {
        return this.parentalText;
    }

    public static PlaystationGameParental getFromText(String text)
    {
        for(PlaystationGameParental value : PlaystationGameParental.values())
        {
            if (value.parental.equalsIgnoreCase(text))
            {
                return value;
            }
        }
        return null;
    }
}
