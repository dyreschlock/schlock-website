package com.schlock.website.entities.apps.pokemon;

import java.util.Comparator;

public class RaidCounterPokemon implements Comparable<RaidCounterPokemon>
{
    private String name;
    private String fastMove;
    private String chargeMove;

    private double dps;
    private double tdo;
    private double dps3tdo;

    private int cp;

    public RaidCounterPokemon(String name, String fastMove, String chargeMove, double dps, double tdo, double dps3tdo, int cp)
    {
        this.name = name;
        this.fastMove = fastMove;
        this.chargeMove = chargeMove;
        this.dps = dps;
        this.tdo = tdo;
        this.dps3tdo = dps3tdo;
        this.cp = cp;
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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFastMove() { return fastMove; }

    public void setFastMove(String fastMove) { this.fastMove = fastMove; }

    public String getChargeMove() { return chargeMove; }

    public void setChargeMove(String chargeMove) { this.chargeMove = chargeMove; }

    public double getDps() { return dps; }

    public void setDsp(double dps) { this.dps = dps; }

    public double getTdo() { return tdo; }

    public void setTdo(double tdo) { this.tdo = tdo; }

    public double getDps3tdo() { return dps3tdo; }

    public void setDps3tdo(double dps3tdo) { this.dps3tdo = dps3tdo; }

    public int getCp() { return cp; }

    public void setCp(int cp) { this.cp = cp; }


    public int compareTo(RaidCounterPokemon p)
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
}
