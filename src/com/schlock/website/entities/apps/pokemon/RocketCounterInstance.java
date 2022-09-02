package com.schlock.website.entities.apps.pokemon;

public class RocketCounterInstance
{
    private String name;

    private String fastMove;
    private String chargeMove;

    private String overall;
    private String cp;
    private String time;
    private String power;

    public RocketCounterInstance(String name, String fastMove, String chargeMove, String overall, String cp, String time, String power)
    {
        this.name = name;

        this.fastMove = fastMove;
        this.chargeMove = chargeMove;

        this.overall = overall;
        this.cp = cp;
        this.time = time;
        this.power = power;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFastMove()
    {
        return fastMove;
    }

    public void setFastMove(String fastMove)
    {
        this.fastMove = fastMove;
    }

    public String getChargeMove()
    {
        return chargeMove;
    }

    public void setChargeMove(String chargeMove)
    {
        this.chargeMove = chargeMove;
    }

    public String getOverall()
    {
        return overall;
    }

    public void setOverall(String overall)
    {
        this.overall = overall;
    }

    public String getCp()
    {
        return cp;
    }

    public void setCp(String cp)
    {
        this.cp = cp;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getPower()
    {
        return power;
    }

    public void setPower(String power)
    {
        this.power = power;
    }
}
