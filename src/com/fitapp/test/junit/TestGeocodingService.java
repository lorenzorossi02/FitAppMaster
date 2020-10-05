package com.fitapp.test.junit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fitapp.logic.googlemap.Geocode;

class TestGeocodingService {
//Lorenzo Rossi
	@Test
	void testGeocodingService() {
		String address = "Via due Giugno 3, Ciampino";
		double delta = 0.001;
		double[] expectedCoords =new double[]{41.800560,12.601260};
		Geocode geocode = new Geocode();
		double[] coordinates = geocode.getCoords(address);
		
		Assertions.assertArrayEquals(expectedCoords, coordinates, delta);
	}

}
