package modele;
import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class EMcity {
public EMcity() {
	// TODO Auto-generated constructor stub
}
public static String getCity(double latitude, double longitude) throws ApiException, InterruptedException, IOException
{
	String city;
	
	GeoApiContext context = new GeoApiContext.Builder()
		    .apiKey("AIzaSyAxZ0Nrn93w4FmfqeP5mseh-t2xyYB393M")
		    .build();
	
	GeocodingApiRequest request = new GeocodingApiRequest(context);
	request.language("fr");
	request.latlng(new LatLng(latitude,longitude));
	request.resultType(AddressType.LOCALITY);
	
	GeocodingResult[] results = request.await();
	city = results[0].addressComponents[0].shortName;
	
	return city;
}
}
