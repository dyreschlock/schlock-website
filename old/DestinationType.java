package com.schlock.website.codejam.oct2011.model;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum DestinationType
{
	DESTINATION_MOD("Destination Modified", null, "red", null),
	DESTINATION("Destination Entered", DESTINATION_MOD, "red", "mid"),
	PIN_POINT_MOD("Pin Point Modified", null, "green", null),
	PIN_POINT("Pin Point Destination", PIN_POINT_MOD, "green", "mid"),
	GUESS_MOD("Guess Modified", null, "blue", null),
	GUESS("Guess Entered", GUESS_MOD, "blue", "mid"),

	GUESS_MOD_N("guess north", GUESS_MOD, null, null),
	GUESS_MOD_E("guess east", GUESS_MOD, null, null),
	GUESS_MOD_W("guess west", GUESS_MOD, null, null),
	GUESS_MOD_S("guess south", GUESS_MOD, null, null),

	PIN_POINT_MOD_N("pin point north", PIN_POINT_MOD, null, null),
	PIN_POINT_MOD_E("pin point east", PIN_POINT_MOD, null, null),
	PIN_POINT_MOD_W("pin point west", PIN_POINT_MOD, null, null),
	PIN_POINT_MOD_S("pin point south", PIN_POINT_MOD, null, null),

	DESTINATION_MOD_N("destination north", DESTINATION_MOD, null, null),
	DESTINATION_MOD_E("destination east", DESTINATION_MOD, null, null),
	DESTINATION_MOD_W("destination west", DESTINATION_MOD, null, null),
	DESTINATION_MOD_S("destination south", DESTINATION_MOD, null, null),

	AVERAGE(null, null, null, null);

	private static final String MARKER = "&markers=color:";
	private static final String SIZE = "size:";

	private DestinationType mod;

	private String displayText;
	private String markerColor;
	private String markerSize;

	private DestinationType(String text, DestinationType mod, String color, String size)
	{
		this.mod = mod;

		this.displayText = text;
		this.markerColor = color;
		this.markerSize = size;
	}

	public DestinationType getMod()
	{
		return mod;
	}

	public String getDisplayText()
	{
		return displayText;
	}

	public String getMarkerColor()
	{
		return MARKER + markerColor;
	}

	public String getMarkerSize()
	{
		if(StringUtils.isNotBlank(markerSize))
		{
			return SIZE + markerSize;
		}
		return markerSize;
	}

	public static List<DestinationType> getDisplayTypes()
	{
		return Arrays.asList(DESTINATION, DESTINATION_MOD, GUESS, GUESS_MOD, PIN_POINT, PIN_POINT_MOD);
	}

	public static List<DestinationType> getMods()
	{
		return Arrays.asList(DESTINATION_MOD, GUESS_MOD, PIN_POINT_MOD);
	}

	public static DestinationType getOriginal(DestinationType type)
	{
		if(DESTINATION_MOD.equals(type))
		{
			return DESTINATION;
		}
		if(GUESS_MOD.equals(type))
		{
			return GUESS;
		}
		if(PIN_POINT_MOD.equals(type))
		{
			return PIN_POINT;
		}
		return null;
	}

	public static DestinationType getNorth(DestinationType type)
	{
		if(GUESS_MOD.equals(type))
		{
			return GUESS_MOD_N;
		}
		if(PIN_POINT_MOD.equals(type))
		{
			return PIN_POINT_MOD_N;
		}
		if(DESTINATION_MOD.equals(type))
		{
			return DESTINATION_MOD_N;
		}
		return null;
	}

	public static DestinationType getEast(DestinationType type)
	{
		if(GUESS_MOD.equals(type))
		{
			return GUESS_MOD_E;
		}
		if(PIN_POINT_MOD.equals(type))
		{
			return PIN_POINT_MOD_E;
		}
		if(DESTINATION_MOD.equals(type))
		{
			return DESTINATION_MOD_E;
		}
		return null;
	}

	public static DestinationType getWest(DestinationType type)
	{
		if(GUESS_MOD.equals(type))
		{
			return GUESS_MOD_W;
		}
		if(PIN_POINT_MOD.equals(type))
		{
			return PIN_POINT_MOD_W;
		}
		if(DESTINATION_MOD.equals(type))
		{
			return DESTINATION_MOD_W;
		}
		return null;
	}

	public static DestinationType getSouth(DestinationType type)
	{
		if(GUESS_MOD.equals(type))
		{
			return GUESS_MOD_S;
		}
		if(PIN_POINT_MOD.equals(type))
		{
			return PIN_POINT_MOD_S;
		}
		if(DESTINATION_MOD.equals(type))
		{
			return DESTINATION_MOD_S;
		}
		return null;
	}
}
