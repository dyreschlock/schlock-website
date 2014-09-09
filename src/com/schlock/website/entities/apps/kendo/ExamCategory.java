package com.schlock.website.entities.apps.kendo;

public enum ExamCategory
{
    MEANING(1, "Questions about the meaning of kendo", "剣道の意義に関する問題"),
    RULES(2, "Questions about the rules of a kendo match", "剣道試合規則に関する問題"),
    TECHNIQUE(3, "Questions about technique", "技術に関する問題"),
    THEORY(4, "Questions about theory", "理論に関する問題");

    private int order;
    private String englishText;
    private String japaneseText;

    private ExamCategory(int order, String englishText, String japaneseText)
    {
        this.order = order;
        this.englishText = englishText;
        this.japaneseText = japaneseText;
    }



    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public String getEnglishText()
    {
        return englishText;
    }

    public void setEnglishText(String englishText)
    {
        this.englishText = englishText;
    }

    public String getJapaneseText()
    {
        return japaneseText;
    }

    public void setJapaneseText(String japaneseText)
    {
        this.japaneseText = japaneseText;
    }
}
