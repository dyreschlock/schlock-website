package com.schlock.website.codejam.oct2011.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Origin
{
	private DestinationType destinationType;

	private String address;

	private Double latitude;
	private Double longitude;

	private Map<DestinationType, Distance> distances;

	public Origin(String address, String latlong)
	{
		this.address = address;

		String[] bits = latlong.split(",");
		this.latitude = Double.parseDouble(bits[0]);
		this.longitude = Double.parseDouble(bits[1]);

		distances = new HashMap<DestinationType, Distance>();
	}

	public Origin(Origin that)
	{
		this.destinationType = that.getDestinationType();
		this.address = that.getAddress();

		this.latitude = that.getLatitude();
		this.longitude = that.getLongitude();

		distances = new HashMap<DestinationType, Distance>();
	}

	public DestinationType getDestinationType()
	{
		return destinationType;
	}

	public void setDestinationType(DestinationType destinationType)
	{
		this.destinationType = destinationType;
	}

	public String getAddress()
	{
		return address;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public String getLatLong()
	{
		return getLatitudeText()+","+getLongitudeText();
	}

	public String getLatitudeText()
	{
		return new DecimalFormat("#.######").format(latitude);
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public String getLongitudeText()
	{
		return new DecimalFormat("#.######").format(longitude);
	}

	public void putDistance(DestinationType key, Distance distance)
	{
		distances.put(key, distance);
	}

	public Distance getDistance(DestinationType key)
	{
		return distances.get(key);
	}

	public boolean isSame(Origin that)
	{
		return this.latitude.equals(that.getLatitude()) &&
				this.longitude.equals(that.getLongitude());
	}
}
