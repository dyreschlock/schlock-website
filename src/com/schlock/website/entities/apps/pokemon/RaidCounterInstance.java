package com.schlock.website.entities.apps.pokemon;

public class RaidCounterInstance extends AbstractCounterInstance implements Comparable<RaidCounterInstance>
{
    private static final String MEGA_STRING = "Mega";
    private static final String SHADOW_STRING = "Shadow";

    private double dps;
    private double tdo;
    private double dps3tdo;

    private int count = 1;

    public RaidCounterInstance(CounterPokemon counter, String fastMove, String chargeMove, double dps, double tdo, double dps3tdo)
    {
        super(counter, fastMove, chargeMove);

        this.dps = formatDouble("%.3f", dps);
        this.tdo = formatDouble("%.1f", tdo);
        this.dps3tdo = formatDouble("%.1f", dps3tdo);
    }

    private double formatDouble(String format, double value)
    {
        String sValue = (String) String.format(format, value);
        return Double.parseDouble(sValue);
    }

    public double getDps4tdo()
    {
        double dps4 = dps * dps * dps * dps;

        return dps4 * tdo / 100000;
    }

    public String getDps4tdoFormat()
    {
        return String.format("%.2f", getDps4tdo());
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

    public void setDsp(double dps) { this.dps = dps; }

    public double getTdo() { return tdo; }

    public void setTdo(double tdo) { this.tdo = tdo; }

    public double getDps3tdo() { return dps3tdo; }

    public void setDps3tdo(double dps3tdo) { this.dps3tdo = dps3tdo; }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int compareTo(RaidCounterInstance p)
    {
        if(getDps4tdo() < p.getDps4tdo())
        {
            return 1;
        }
        if (getDps4tdo() > p.getDps4tdo())
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
