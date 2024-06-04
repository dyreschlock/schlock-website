package com.schlock.website.entities.apps.ps2;

public enum PlaystationGameParental
{
    ESRB_E("esrb/e", "E"),
    ESRB_3("esrb/3", "eC"),
    ESRB_10("esrb/10", "E+"),
    ESRB_17("esrb/17", "M"),
    ESRB_18("esrb/18", "Ao"),
    ERSC_TEEN("esrb/teen", "T"),
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
        if (ESRB_E.parental.equals(text))
        {
            return ESRB_E;
        }
        if (ESRB_3.parental.equals(text))
        {
            return ESRB_3;
        }
        if (ESRB_10.parental.equals(text))
        {
            return ESRB_10;
        }
        if (ESRB_17.parental.equals(text))
        {
            return ESRB_17;
        }
        if (ESRB_18.parental.equals(text))
        {
            return ESRB_18;
        }
        if (ERSC_TEEN.parental.equals(text))
        {
            return ERSC_TEEN;
        }
        if (CERO_A.parental.equals(text))
        {
            return CERO_A;
        }
        if (CERO_B.parental.equals(text))
        {
            return CERO_B;
        }
        if (CERO_C.parental.equals(text))
        {
            return CERO_C;
        }
        if (CERO_D.parental.equals(text))
        {
            return CERO_D;
        }
        if (CERO_Z.parental.equals(text))
        {
            return CERO_Z;
        }
        if (CERO_DEMO.parental.equals(text))
        {
            return CERO_DEMO;
        }
        if (CERO_PEND.parental.equals(text))
        {
            return CERO_PEND;
        }
        if (PEGI_3.parental.equals(text))
        {
            return PEGI_3;
        }
        if (PEGI_7.parental.equals(text))
        {
            return PEGI_7;
        }
        if (PEGI_12.parental.equals(text))
        {
            return PEGI_12;
        }
        if (PEGI_16.parental.equals(text))
        {
            return PEGI_16;
        }
        if (PEGI_18.parental.equals(text))
        {
            return PEGI_18;
        }
        return null;
    }
}
