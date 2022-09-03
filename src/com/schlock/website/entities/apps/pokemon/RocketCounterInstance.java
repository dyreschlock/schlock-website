package com.schlock.website.entities.apps.pokemon;

public class RocketCounterInstance extends AbstractCounterInstance
{
    private String name;

    private double tdo;
    private double activation;

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

    public RocketCounterInstance(CounterPokemon counter, String fastMove, String chargeMove, double tdo, double activation)
    {
        super(counter, fastMove, chargeMove);

        this.tdo = tdo;
        this.overall = String.format("%.3f", tdo);

        this.activation = activation;
    }

    public String getName()
    {
        if (name == null)
        {
            return super.getName();
        }
        return name;
    }

    public double getTdo()
    {
        return tdo;
    }

    public double getActivation()
    {
        return activation;
    }

    public String getOverall()
    {
        return overall;
    }

    public String getCp()
    {
        return cp;
    }

    public String getTime()
    {
        return time;
    }

    public String getPower()
    {
        return power;
    }

    public int compareTo(AbstractCounterInstance o)
    {
        RocketCounterInstance that = (RocketCounterInstance) o;

        //higher negative tdo better
        double compare = this.getTdo() - that.getTdo();
        if (compare == 0.0)
        {
            //lower level better
            compare = this.getActivation() - that.getActivation();
            if (compare == 0.0)
            {
                //alphabetical
                compare = this.getName().compareTo(that.getName());
            }
        }

        if (compare > 0.0)
        {
            return 1;
        }
        if (compare < 0.0)
        {
            return -1;
        }
        return 0;

    }
}
