package com.schlock.website.entities.apps.personality;

public class Entry
{
    private static final String DELIM = ",";

    private static final String MALE_PARAM = "m";
    private static final String FEMALE_PARAM = "f";

    private static final String MALE = "male";
    private static final String FEMALE = "female";

    private String name;
    private String gender;

    private String code;
    private String type;

    private String mindType;
    private Integer mindScore;

    private String energyType;
    private Integer energyScore;

    private String natureType;
    private Integer natureScore;

    private String tacticsType;
    private Integer tacticsScore;

    private String identityType;
    private Integer identityScore;

    private String role;
    private String strategy;

    public Entry(String paramString)
    {
        String[] params = paramString.split(DELIM);

        this.name = params[0];
        this.gender = params[1];

        this.code = params[2];
        this.type = params[3];

        this.mindType = params[4];
        this.mindScore = Integer.parseInt(params[5]);

        this.energyType = params[6];
        this.energyScore = Integer.parseInt(params[7]);

        this.natureType = params[8];
        this.natureScore = Integer.parseInt(params[9]);

        this.tacticsType = params[10];
        this.tacticsScore = Integer.parseInt(params[11]);

        this.identityType = params[12];
        this.identityScore = Integer.parseInt(params[13]);

//        this.role = params[14];
//        this.strategy = params[15];
    }

    public String getImageName()
    {
        // intj-architect-s2-male

        String imageName = getShortCode() + "-" + getType() + "-s2-";

        if (getGender().equalsIgnoreCase(MALE_PARAM))
        {
            imageName += MALE;
        }
        else
        {
            imageName += FEMALE;
        }
        return imageName.toLowerCase();
    }

    public String getShortCode()
    {
        String[] params = getCode().split("-");
        return params[0];
    }

    public String getMindDegrees()
    {
        return getDegreesFromScore(mindScore);
    }

    public String getEnergyDegrees()
    {
        return getDegreesFromScore(energyScore);
    }

    public String getNatureDegrees()
    {
        return getDegreesFromScore(natureScore);
    }

    public String getTacticsDegrees()
    {
        return getDegreesFromScore(tacticsScore);
    }

    public String getIdentityDegrees()
    {
        return getDegreesFromScore(identityScore);
    }

    private String getDegreesFromScore(Integer score)
    {
        double degree = 100 - score;

        degree = degree *2;

        degree = 100 - degree;

        degree = degree / 100;

        degree = 180 * degree;

        degree = degree + 90;

        return String.format("%.2f", degree);
    }



    public String getName()
    {
        return name;
    }

    public String getGender()
    {
        return gender;
    }

    public String getCode()
    {
        return code;
    }

    public String getType()
    {
        return type;
    }

    public String getMindType()
    {
        return mindType;
    }

    public Integer getMindScore()
    {
        return mindScore;
    }

    public String getEnergyType()
    {
        return energyType;
    }

    public Integer getEnergyScore()
    {
        return energyScore;
    }

    public String getNatureType()
    {
        return natureType;
    }

    public Integer getNatureScore()
    {
        return natureScore;
    }

    public String getTacticsType()
    {
        return tacticsType;
    }

    public Integer getTacticsScore()
    {
        return tacticsScore;
    }

    public String getIdentityType()
    {
        return identityType;
    }

    public Integer getIdentityScore()
    {
        return identityScore;
    }

    public String getRole()
    {
        return role;
    }

    public String getStrategy()
    {
        return strategy;
    }
}
