package com.schlock.website.entities.apps.movies;

public enum MovieTitle
{
    FAST_AND_FURIOUS(Type.TITLE_CHANGE_KATAKANA),
    WRECK_IT_RALPH(Type.TITLE_CHANGE_KATAKANA),

    RATATOUILLE(Type.TITLE_CHANGE),
    FROZEN(Type.TITLE_CHANGE),
    UP(Type.TITLE_CHANGE),
    DESPICABLE_ME(Type.TITLE_CHANGE),
    SPIRITED_AWAY(Type.TITLE_CHANGE),
    LAPUTA(Type.TITLE_CHANGE),
    POWER_RANGERS(Type.TITLE_CHANGE),
    PUSS_IN_BOOTS(Type.TITLE_CHANGE),
    ACE_ATTORNEY(Type.TITLE_CHANGE),
    TANGLED(Type.TITLE_CHANGE),
    BRAVE(Type.TITLE_CHANGE),
    CONAN(Type.TITLE_CHANGE),

    KAZE_TACHINU(Type.DIRECT_TRANSLATION),
    WHISPER_OF_THE_HEART(Type.DIRECT_TRANSLATION),
    ATTACK_ON_TITAN(Type.DIRECT_TRANSLATION),

    MEATBALLS(Type.KATAKANA_ONLY),
    SHIN_CHAN(Type.KATAKANA_ONLY),
    LILO_AND_STITCH(Type.KATAKANA_ONLY),
    GODZILLA(Type.KATAKANA_ONLY),
    EVANGELION(Type.KATAKANA_ONLY);


    private Type type;

    private MovieTitle(Type type)
    {
        this.type = type;
    }


    public String getEnglishKey()
    {
        return createKey() + "-english";
    }

    public String getJapaneseKey()
    {
        return createKey() + "-japanese";
    }

    public String getJapaneseRomajiKey()
    {
        return createKey() + "-japanese-romaji";
    }

    public String getJapaneseEnglishKey()
    {
        return createKey() + "-japanese-english";
    }

    private String createKey()
    {
        String key = this.name();
        key = key.toLowerCase();
        key = key.replaceAll("_", "-");
        return key;
    }


    public boolean hasJapaneseRomaji()
    {
        return this.type == Type.TITLE_CHANGE ||
                this.type == Type.DIRECT_TRANSLATION;
    }

    public boolean hasJapaneseEnglish()
    {
        return this.type == Type.TITLE_CHANGE;
    }




    private enum Type
    {
        TITLE_CHANGE,
        TITLE_CHANGE_KATAKANA,
        DIRECT_TRANSLATION,
        KATAKANA_ONLY;
    }
}
