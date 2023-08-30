package com.schlock.website.entities.apps.pokemon;

public class RaidCounterInstance extends AbstractCounterInstance
{
    private static final String MEGA_STRING = "Mega";
    private static final String SHADOW_STRING = "Shadow";

    private double dps;
    private double tdo;

    private double rank = 0.0;

    private int count = 1;

    public RaidCounterInstance(CounterPokemon counter, PokemonMove fastMove, PokemonMove chargeMove, double dps, double tdo)
    {
        super(counter, fastMove, chargeMove);

        this.dps = formatDouble("%.3f", dps);
        this.tdo = formatDouble("%.1f", tdo);
    }

    private double formatDouble(String format, double value)
    {
        String sValue = (String) String.format(format, value);
        return Double.parseDouble(sValue);
    }

    public double getRank()
    {
        if (rank == 0.0)
        {
            double dps4tdo = dps * dps * dps * dps * tdo;
            double overall_er = Math.pow(dps4tdo, 0.25) / 2.4;

//            rank = dps4tdo;

//            double dps3tdo = Math.pow(dps, 3) * tdo;
//            double overall_er = Math.pow(dps3tdo, 0.25);

            rank = overall_er;
        }
        return rank;
    }

    public String getFormattedRank()
    {
        return String.format("%.2f", getRank());
    }

    public boolean isMega()
    {
        return getName().startsWith(MEGA_STRING);
    }

    public boolean isShadow()
    {
        return getName().startsWith(SHADOW_STRING);
    }

    public boolean isRegular()
    {
        return !isMega() && !isShadow();
    }

    public void incrementCount()
    {
        this.count++;
    }

    public double getDps() { return dps; }

    public double getTdo() { return tdo; }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int compareTo(AbstractCounterInstance o)
    {
        RaidCounterInstance that = (RaidCounterInstance) o;

        //higher tdo better
        double compare = that.getRank() - this.getRank();
        if (compare == 0.0)
        {
            //lower level better
            compare = this.getLevel() - that.getLevel();
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

    public String getUniqueID()
    {
        return this.getName() +
                this.getFastMove() +
                this.getChargeMove() +
                this.getLevel();
    }

    public boolean equals(Object o)
    {
        RaidCounterInstance that = (RaidCounterInstance) o;

        String thisUniqueId = this.getUniqueID();
        String thatUniqueId = that.getUniqueID();

        return thisUniqueId.equals(thatUniqueId);
    }
}
