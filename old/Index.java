package com.schlock.website.pages.codejam.oct2011;

import com.schlock.website.codejam.oct2011.model.DestinationType;
import com.schlock.website.codejam.oct2011.model.Distance;
import com.schlock.website.codejam.oct2011.model.Origin;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.json.JSONException;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index
{
	private static final double MOVEMENT = 0.001;

	@Property
	@Persist
	private String originsText;

	@Property
	@Persist
	private String destinationText;

	@Property
	@Persist
	private boolean usePinPoint;

	@Property
	@Persist
	private boolean determineLocation;

	@Property
	@Persist
	private String guessText;

	@Property
	@Persist
	private boolean useDuration;

	@Property
	@Persist
	private int multiplier;

	@Property
	@Persist
	private int iterations;


	@Property
	@Persist
	private List<Origin> origins;

	@Property
	@Persist
	private Map<DestinationType, Origin> destinations;


	@Property
	@Persist
	private String errorMessage;

	@Property
	@Persist
	private String imgUrl;


	@Property
	private Origin loopOrigin;

	@Property
	private DestinationType loopKey;

	@Property
	private String locationDivId;

	@Property
	private String guessDivId;

	@Inject
	private JavaScriptSupport javascriptSupport;


	public double getMovementNum()
	{
		if(multiplier <= 0)
		{
			return MOVEMENT;
		}
		return MOVEMENT * multiplier;
	}

	public int getIterationsNum()
	{
		if(iterations <= 0)
		{
			return 1;
		}
		return iterations;
	}

	public boolean isHasImage()
	{
		return StringUtils.isNotBlank(imgUrl);
	}

	public boolean isHasErrorMessage()
	{
		return StringUtils.isNotBlank(errorMessage);
	}

	public boolean isHasOrigins()
	{
		return origins != null && !origins.isEmpty();
	}

	public List<DestinationType> getDestinationKeys()
	{
		List<DestinationType> keys = new ArrayList<DestinationType>();
		for(DestinationType type : DestinationType.getDisplayTypes())
		{
			if(destinations.get(type) != null)
			{
				keys.add(type);
			}
		}
		return keys;
	}

	public Origin getLoopDestination()
	{
		return destinations.get(loopKey);
	}

	public Distance getLoopOriginDestinationDistance()
	{
		return loopOrigin.getDistance(loopKey);
	}

	public String getLoopDestinationDistanceAverage()
	{
		Distance distance = getLoopDestination().getDistance(DestinationType.AVERAGE);
		if(distance != null)
		{
			return distance.getDistanceText();
		}
		return "";
	}

	public String getLoopDestinationDurationAverage()
	{
		Distance distance = getLoopDestination().getDistance(DestinationType.AVERAGE);
		if(distance != null)
		{
			return distance.getDurationText();
		}
		return "";
	}

	void setupRender()
	{
		guessDivId = javascriptSupport.allocateClientId("guessDivId");
		locationDivId = javascriptSupport.allocateClientId("locationDivId");

		if(StringUtils.isBlank(originsText))
		{
			originsText = "116 E Gilman St, Madison, WI\n"+
							"842 Jenifer St., Madison, WI\n"+
							"9 Dunran Ct, Madison, WI\n"+
							"6301 Offshore Dr., Madison, WI\n";
		}
		if(StringUtils.isBlank(destinationText))
		{
			destinationText = "6911 Mangrove Ln., Monona, WI";
		}
		if(StringUtils.isBlank(guessText))
		{
			guessText = "4864 Sheboygan Ave, Madison, WI";
		}
		if(iterations == 0)
		{
			iterations = 1;
		}
		if(multiplier == 0)
		{
			multiplier = 1;
		}
	}


	Object onSubmitFromMapForm()
	{
		this.origins = new ArrayList<Origin>();
		this.destinations = new HashMap<DestinationType, Origin>();

		this.errorMessage = null;
		this.imgUrl = null;
		try
		{

			generateOrigins();
			generateDestination();

			if(usePinPoint)
			{
				generatePinPoint();
				generateGuess();
			}

			if(determineLocation)
			{
				generateMods();
			}

			updateDistances();

			this.imgUrl = generateMapURL();
		}
		catch(Exception e)
		{
			this.errorMessage = e.getMessage();
		}
		return this;
	}

	private void generateMods() throws Exception
	{
		for(DestinationType type : DestinationType.getMods())
		{
			DestinationType original = DestinationType.getOriginal(type);

			Origin destination = destinations.get(original);

			Origin duplicate = new Origin(destination);
			duplicate.setDestinationType(type);

			destinations.put(type, duplicate);
		}

		List<DestinationType> mods = DestinationType.getMods();

		for(int i = 0; i < getIterationsNum(); i++)
		{

			List<Origin> alts = new ArrayList<Origin>();
			for(DestinationType key : mods)
			{
				Origin destination = destinations.get(key);
				alts.addAll(createAlts(destination));
				alts.add(destination);
			}

			getDistanceDuration(origins, alts);
			generateAverages(alts);

			for(DestinationType type : mods)
			{
				Origin destination = destinations.get(type);

				Origin northPoint = getByDestinationType(alts, DestinationType.getNorth(type));
				Origin southPoint = getByDestinationType(alts, DestinationType.getSouth(type));
				Origin westPoint = getByDestinationType(alts, DestinationType.getWest(type));
				Origin eastPoint = getByDestinationType(alts, DestinationType.getEast(type));

				Origin better = returnBetter(northPoint, southPoint, westPoint, eastPoint);

				Origin best = returnBetter(destination, better);

				best.setDestinationType(type);
				destinations.put(type, best);
			}
		}

		for(DestinationType type : DestinationType.getMods())
		{
			Origin destination = destinations.get(type);

			destination = createOrigin(null, destination.getLatLong());
			destination.setDestinationType(type);

			destinations.put(type, destination);
		}
	}

	private Origin getByDestinationType(List<Origin> destinations, DestinationType type)
	{
		for(Origin destination : destinations)
		{
			if(type.equals(destination.getDestinationType()))
			{
				return destination;
			}
		}
		return null;
	}

	private List<Origin> createAlts(Origin original) throws Exception
	{
		double north = original.getLatitude() + getMovementNum();
		double south = original.getLatitude() - getMovementNum();
		double east = original.getLongitude() + getMovementNum();
		double west = original.getLongitude() - getMovementNum();

		Origin northPoint = new Origin(null, north+","+original.getLongitude());
		northPoint.setDestinationType(DestinationType.getNorth(original.getDestinationType()));

		Origin southPoint = new Origin(null, south+","+original.getLongitude());
		southPoint.setDestinationType(DestinationType.getSouth(original.getDestinationType()));

		Origin eastPoint = new Origin(null, original.getLatitude()+","+east);
		eastPoint.setDestinationType(DestinationType.getEast(original.getDestinationType()));

		Origin westPoint = new Origin(null, original.getLatitude()+","+west);
		westPoint.setDestinationType(DestinationType.getWest(original.getDestinationType()));

		List<Origin> destinations = new ArrayList<Origin>();
		destinations.add(northPoint);
		destinations.add(southPoint);
		destinations.add(eastPoint);
		destinations.add(westPoint);

		return destinations;
	}

	private Origin returnBetter(Origin o1, Origin o2, Origin o3, Origin o4)
	{
		Origin best1 = returnBetter(o1, o2);
		Origin best2 = returnBetter(o3, o4);

		return returnBetter(best1, best2);
	}

	private Origin returnBetter(Origin o1, Origin o2)
	{
		Distance d1 = o1.getDistance(DestinationType.AVERAGE);
		Distance d2 = o2.getDistance(DestinationType.AVERAGE);

		if(useDuration)
		{
			if(d1.getDurationValue() < d2.getDurationValue())
			{
				return o1;
			}
			return o2;
		}
		else
		{
			if(d1.getDistanceValue() < d2.getDistanceValue())
			{
				return o1;
			}
			return o2;
		}
	}

	private void updateDistances() throws Exception
	{
		List<Origin> destinations = new ArrayList<Origin>();
		destinations.addAll(this.destinations.values());

		getDistanceDuration(origins, destinations);
		generateAverages(destinations);
	}

	private void generateAverages(List<Origin> destinations) throws Exception
	{
		for(Origin destination : destinations)
		{
			Distance average = generateAverage(destination.getDestinationType());

			destination.putDistance(DestinationType.AVERAGE, average);
		}
	}

	private Distance generateAverage(DestinationType storeKey) throws Exception
	{
		Integer distanceTotal = 0;
		Integer durationTotal = 0;

		for(Origin origin : origins)
		{
			Distance distance = origin.getDistance(storeKey);

			if(distance == null)
			{
				throw new RuntimeException(origin.getAddress() +":"+ storeKey.getDisplayText());
			}

			distanceTotal += distance.getDistanceValue();
			durationTotal += distance.getDurationValue();
		}

		Integer distanceAve = distanceTotal / origins.size();
		Integer durationAve = durationTotal / origins.size();

		return new Distance(durationAve, distanceAve);
	}

	private void generateDestination() throws Exception
	{
		if(StringUtils.isNotBlank(destinationText))
		{
			Origin destination = createOrigin(destinationText, null);
			destination.setDestinationType(DestinationType.DESTINATION);

			destinations.put(DestinationType.DESTINATION, destination);
		}
	}

	private void generateGuess() throws Exception
	{
		if(StringUtils.isNotBlank(guessText))
		{
			Origin destination = createOrigin(guessText, null);
			destination.setDestinationType(DestinationType.GUESS);

			destinations.put(DestinationType.GUESS, destination);
		}
	}

	private void generatePinPoint() throws Exception
	{
		double totalLat = 0;
		double totalLong = 0;

		for(Origin origin : origins)
		{
			totalLat += origin.getLatitude();
			totalLong += origin.getLongitude();
		}

		double aveLat = totalLat / origins.size();
		double aveLong = totalLong / origins.size();

		Origin pinPoint = createOrigin(null, aveLat +","+ aveLong);
		pinPoint.setDestinationType(DestinationType.PIN_POINT);

		destinations.put(DestinationType.PIN_POINT, pinPoint);
	}

	private void generateOrigins() throws Exception
	{
		String[] os = originsText.split("\n");

		for(String o : os)
		{
			if(StringUtils.isNotBlank(o))
			{
				Origin origin = createOrigin(o, null);

				origins.add(origin);
			}
		}
	}

	private Origin createOrigin(String addressText, String latlongText) throws Exception
	{
		JSONObject location = getAddress(addressText, latlongText);

		String address = getAddressText(location);
		String latlong = getLatLong(location);

		return new Origin(address, latlong);
	}

	private String getAddressText(JSONObject location) throws Exception
	{
		return location.getString("formatted_address");
	}

	private String getLatLong(JSONObject location) throws Exception
	{
		JSONObject geometry = location.getJSONObject("geometry");
		JSONObject loc = geometry.getJSONObject("location");

		double latitude = loc.getDouble("lat");
		double longitude = loc.getDouble("lng");

		return latitude +","+ longitude;
	}

	private JSONObject getAddress(String address, String latlong) throws Exception
	{
		String urlString = generateGeocodeURL(address, latlong);
		JSONObject data = resolveUrl(urlString);

		return data.getJSONArray("results").getJSONObject(0);
	}

	private void getDistanceDuration(List<Origin> origins, List<Origin> destinations) throws Exception
	{
		String urlString = generateDistanceURL(origins, destinations);
		JSONObject data = resolveUrl(urlString);

		String status = data.getString("status");
		if(!StringUtils.equals("OK", status))
		{
			throw new RuntimeException(status);
		}

		JSONArray rows = data.getJSONArray("rows");

		for(int i = 0; i < rows.length(); i++)
		{
			Origin origin = origins.get(i);
			JSONArray elements = rows.getJSONObject(i).getJSONArray("elements");

			for(int j = 0; j < elements.length(); j++)
			{
				Origin destination = destinations.get(j);

				System.out.println(origin.getAddress() + ":" + destination.getDestinationType().getDisplayText());

				Distance distance = new Distance(elements.getJSONObject(j));

				origin.putDistance(destination.getDestinationType(), distance);
			}
		}
	}

	private JSONObject resolveUrl(String url) throws IOException, JSONException
	{
		InputStream in = new URL(url).openStream();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
			String jsonText = readAll(reader);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
		finally
		{
			in.close();
		}
	}

	private String readAll(Reader reader) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = reader.read()) != -1)
		{
			sb.append((char) c);
		}
		return sb.toString();
	}

	private static final String API_URL = "http://maps.googleapis.com/maps/api/";

	private static final String GEOCODE = "geocode/json?";

	private static final String ADDRESS = "address=";
	private static final String LATLONG = "latlng=";

	private String generateGeocodeURL(String address, String latlong) throws Exception
	{
//		System.out.println("geocode url called");

		StringBuilder sb = new StringBuilder();

		sb.append(API_URL).append(GEOCODE);

		if(StringUtils.isNotBlank(address))
		{
			sb.append(ADDRESS);
			sb.append(URLEncoder.encode(address, "UTF-8"));
		}
		else if(StringUtils.isNotBlank(latlong))
		{
			sb.append(LATLONG);
			sb.append(URLEncoder.encode(latlong, "UTF-8"));
		}

		sb.append("&sensor=false");
		return sb.toString();
	}


	private static final String DISTANCE = "distancematrix/json?units=imperial";

	private static final String SEPARATER = "%7C";

	private static final String ORIGINS = "&origins=";
	private static final String DESTINATIONS = "&destinations=";

	private String generateDistanceURL(List<Origin> origins, List<Origin> destinations) throws Exception
	{
//		System.out.println("distance url called");

		StringBuilder sb = new StringBuilder();

		sb.append(API_URL).append(DISTANCE);

		sb.append(ORIGINS);
		for(int i = 0; i < origins.size(); i++)
		{
			Origin origin = origins.get(i);
			if(i > 0)
			{
				sb.append(SEPARATER);
			}
			sb.append(URLEncoder.encode(origin.getLatLong(), "UTF-8"));
		}

		sb.append(DESTINATIONS);
		for(int i = 0; i < destinations.size(); i++)
		{
			Origin destination = destinations.get(i);
			if(i > 0)
			{
				sb.append(SEPARATER);
			}
			sb.append(URLEncoder.encode(destination.getLatLong(), "UTF-8"));
		}

		sb.append("&sensor=false");
		return sb.toString();
	}


	private static final String STATIC_MAP = "staticmap?";

	private static final String WHITE_MARKER = "&markers=color:white";

	private String generateMapURL() throws Exception
	{
		StringBuilder sb = new StringBuilder();

		sb.append(API_URL).append(STATIC_MAP);
		sb.append("size=512x512");
		sb.append("&maptype=roadmap");

		for(Origin origin : origins)
		{
			sb.append(addressParameters(origin, WHITE_MARKER, null));
		}

		for(DestinationType type : destinations.keySet())
		{
			Origin destination = destinations.get(type);
			sb.append(addressParameters(destination, type.getMarkerColor(), type.getMarkerSize()));
		}

		sb.append("&sensor=false");
		return sb.toString();
	}

	private String addressParameters(Origin origin, String markerColor, String size) throws Exception
	{
		StringBuilder sb = new StringBuilder();

		sb.append(markerColor);
		if(StringUtils.isNotBlank(size))
		{
			sb.append(SEPARATER);
			sb.append(size);
		}
		sb.append(SEPARATER);
		sb.append(URLEncoder.encode(origin.getLatLong(), "UTF-8"));

		return sb.toString();
	}
}
