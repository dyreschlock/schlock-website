package com.schlock.website.entities.apps.pocket;

import org.apache.commons.lang.StringUtils;

public class PocketCore
{
    public static final String CAT_CONSOLE = "Console";
    public static final String CAT_HANDHELD = "Handheld";
    public static final String CAT_COMPUTER = "Computer";
    public static final String CAT_ARCADE_1 = "Arcade";
    public static final String CAT_ARCADE_2 = "Arcade";
    public static final String CAT_ARCADE_MULTI = "Arcade Multi";

    private static final String IMAGE_LINK = "https://raw.githubusercontent.com/dyreschlock/pocket-platform-images/main/pics/%s/%s.png";
    private static final String HOME = "home";
    private static final String ARCADE = "arcade";

    private static final String FAKE_ARCADE_NAME = "Single Arcade Cores";
    private static final String FAKE_ARCADE_MANU = "Various Manufacturers";

    protected final static String ATARI_2600 = "2600";
    protected final static String ATARI_7800 = "7800";
    protected final static String PC_486 = "486pc";

    protected final static String ATARI_2600_FIXED = "a2600";
    protected final static String ATARI_7800_FIXED = "a7800";
    protected final static String PC_486_FIXED = "pc486";

    private String platformId;
    private String name;
    private String category;
    private String manufacturer;
    private String year;

    public String getImageLink()
    {
        String coreType = HOME;
        if (StringUtils.equalsIgnoreCase(CAT_ARCADE_1, this.category) ||
                StringUtils.equalsIgnoreCase(CAT_ARCADE_2, this.category) ||
                StringUtils.equalsIgnoreCase(CAT_ARCADE_MULTI, this.category))
        {
            coreType = ARCADE;
        }

        return String.format(IMAGE_LINK, coreType, this.platformId);
    }

    public boolean isCategoryArcade()
    {
        return CAT_ARCADE_1.equalsIgnoreCase(getCategory()) || CAT_ARCADE_2.equalsIgnoreCase(getCategory());
    }

    public boolean isFakeArcadeCore()
    {
        return ARCADE.equalsIgnoreCase(this.platformId);
    }

    public String getPlatformId()
    {
        if (ATARI_2600.equalsIgnoreCase(platformId))
        {
            return ATARI_2600_FIXED;
        }
        if (ATARI_7800.equalsIgnoreCase(platformId))
        {
            return ATARI_7800_FIXED;
        }
        if (PC_486.equalsIgnoreCase(platformId))
        {
            return PC_486_FIXED;
        }
        return platformId;
    }

    public void setPlatformId(String platformId)
    {
        this.platformId = platformId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public static PocketCore createFakeArcadeCore()
    {
        PocketCore core = new PocketCore();
        core.setName(FAKE_ARCADE_NAME);
        core.setPlatformId(ARCADE);
        core.setCategory(CAT_ARCADE_MULTI);
        core.setManufacturer(FAKE_ARCADE_MANU);

        return core;
    }
}
