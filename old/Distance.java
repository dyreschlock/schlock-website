package com.schlock.website.codejam.oct2011.model;

import org.apache.tapestry5.json.JSONObject;

public class Distance
{
	private static final double METERS_TO_MILES = 0.00062137;
	private static final double SECONDS_TO_MINUTES = 60;

	private String durationText;
	private Integer durationValue;

	private String distanceText;
	private Integer distanceValue;

	public Distance(Integer durationValue, Integer distanceValue)
	{
		this.durationValue = durationValue;

		double minutes = durationValue / SECONDS_TO_MINUTES;

		this.durationText = new DecimalFormat("#.#").format(minutes) + " min";

		///

		this.distanceValue = distanceValue;

		double miles = distanceValue * METERS_TO_MILES;

		this.distanceText = new DecimalFormat("#.#").format(miles) + " mi";
	}

	public Distance(JSONObject response) throws Exception
	{
		JSONObject duration = response.getJSONObject("duration");

		this.durationText = duration.getString("text");
		this.durationValue = duration.getInt("value");

		JSONObject distance = response.getJSONObject("distance");

		this.distanceText = distance.getString("text");
		this.distanceValue = distance.getInt("value");
	}


	public String getDistanceText()
	{
		return distanceText;
	}

	public Integer getDistanceValue()
	{
		return distanceValue;
	}

	public String getDurationText()
	{
		return durationText;
	}

	public Integer getDurationValue()
	{
		return durationValue;
	}

	public Integer getDistanceDifference(Distance that)
	{
		Integer diff = this.distanceValue - that.getDistanceValue();
		if(diff < 0)
		{
			diff = 0 - diff;
		}
		return diff;
	}

	public Integer getDurationDifference(Distance that)
	{
		Integer diff = this.durationValue - that.getDurationValue();
		if(diff < 0)
		{
			diff = 0 - diff;
		}
		return diff;
	}
}
