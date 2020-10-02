package com.fitapp.logic.googlemap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.lynden.gmapsfx.javascript.object.LatLong;

public class Geocode {

	private LatLong coordinates;

	public LatLong getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(LatLong coordinates) {
		this.coordinates = coordinates;
	}

	public StringBuilder getConnection(String addr) {

		final String protocol = "https://maps.googleapis.com/maps/api/geocode/json?address=";
		final String key = "&key=AIzaSyDP-NfD5FVlNeLw52M7Ff_HPa8K3MByAa8";
		StringBuilder responseContent = new StringBuilder();
		String newUrl = makeRequest(addr, key, protocol);

		try (GoogleMapConnection googleMapConnection = new GoogleMapConnection()) {

			responseContent = googleMapConnection.getResponse(newUrl);

		} catch (Exception e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return responseContent;

	}

	public static String makeRequest(String address, String key, String protocol) {
		StringBuilder urlFinal = new StringBuilder();
		try {
			urlFinal.append(protocol);
			urlFinal.append(URLEncoder.encode(address, "UTF-8"));
			urlFinal.append(key);
		} catch (UnsupportedEncodingException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		urlFinal.append(key);
		return urlFinal.toString();
	}

	public void getLocation(String address) {


			LatLong coord = null;
			JSONObject location = googleGeocode(address);
			if(location!=null) {

				double lng = (double) location.get("lng");
				double lat = (double) location.get("lat");
				coord = new LatLong(lat,lng);
			}
			setCoordinates(coord);
	}
	
	public Double[] getCoords(String address) {

		
			Double[] coord = new Double[2];
			JSONObject location = googleGeocode(address);
			if(location!=null) {
				double lng = (double) location.get("lng");
				double lat = (double) location.get("lat");
				coord[0] = lat;
				coord[1] = lng;
				return coord;
			}
			return coord;
			
		
	}

	
	private JSONObject googleGeocode(String address) {
		StringBuilder str = this.getConnection(address);
		try {
			JSONParser parser = new JSONParser();
			String requestResult = str.toString();
			Object obj = parser.parse(requestResult);
			JSONObject jb = (JSONObject) obj;
			JSONArray array = (JSONArray) jb.get("results");
			JSONObject result = (JSONObject) array.get(0);
			JSONObject geometry = (JSONObject) result.get("geometry");
			return (JSONObject) geometry.get("location");
		} catch (ParseException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
		
	}


}
