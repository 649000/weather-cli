package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.model.mapbox.FeatureCollection;
import org.example.model.openweather.Weather;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class App {
    public static void main( String[] args ) throws RuntimeException {
        // Check user entered a location
        if(args.length == 0) {
            throw new RuntimeException("Please enter a location. Eg. Australia");
        }

        //Get Coordinates
        URI mapboxURI = buildMapBoxURI(String.join(" ", args));
        FeatureCollection featureCollection = getCoordinates(mapboxURI);
        List<Double> coordinates = featureCollection.getFeatures().stream().findFirst().get().getCenter();

        //Get Weather
        URI weatherURI = buildWeatherURI(coordinates.get(1).toString(),coordinates.get(0).toString());
        Weather weather = getWeather(weatherURI);

        DecimalFormat df = new DecimalFormat("0.00");

        //Print out weather information
        System.out.println("Current temperature in " + featureCollection.getFeatures().get(0).getPlaceName() + " is "
                + df.format(convertToCelsius(weather.getCurrent().getTemp())) + "C / "
                + df.format(convertToFarenheit(weather.getCurrent().getTemp())) + "F");
        System.out.println("Conditions are currently: " + weather.getCurrent().getCurrentWeatherList().get(0).getMain());
        System.out.println("What you should expect: " + weather.getCurrent().getCurrentWeatherList().get(0).getDescription());
    }

    /**
     * Construct the URI to call Mapbox API
     * @param location
     * @return
     */
    private static URI buildMapBoxURI(String location)  {
        final String mapboxBaseURL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
        final String mapboxAPIKey = System.getenv("mapboxAPIKey");

        if(mapboxAPIKey == null || mapboxAPIKey.isEmpty()) {
            throw new RuntimeException("Invalid Mapbox API Key");
        }

        String mapboxURI = mapboxBaseURL + location + ".json";
        URIBuilder uriBuilder = null;
        URI uri = null;
        try {
            uriBuilder = new URIBuilder(mapboxURI);
            uriBuilder.addParameter("limit", "1");
            uriBuilder.addParameter("access_token", mapboxAPIKey);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Exception in building URI for MapBox API");
        }

        Logger.getAnonymousLogger().log(Level.INFO, uri.toString());
        return uri;
    }

    /**
     * HTTP call to Mapbox API
     * @param uri
     * @return
     */
    private static FeatureCollection getCoordinates(URI uri)  {
        ObjectMapper mapper = new ObjectMapper();
        //Creating a HttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a HttpGet object
        HttpGet httpget = new HttpGet(uri);

        //Executing the Get request
        FeatureCollection response = null;
        try {
            HttpResponse httpresponse = httpclient.execute(httpget);
            response = httpclient.execute(httpget, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), FeatureCollection.class));
        } catch (IOException e) {
            throw new RuntimeException("Exception in getting location coordinates");
        }

        if(response.getFeatures().size()==0) {
            throw new RuntimeException("Unable to retrieve coordinates for this location");
        }

        Logger.getAnonymousLogger().log(Level.INFO, response.toString());

        return response;
    }

    /**
     * Construct the URI to Open Weather API
     * @param latitude
     * @param longitude
     * @return
     */
    private static URI buildWeatherURI(String latitude, String longitude) {
        final String weatherBaseURL = "https://api.openweathermap.org/data/3.0/onecall";
        final String weatherAPIKey = System.getenv("weatherAPIKey");

        if(weatherAPIKey == null || weatherAPIKey.isEmpty()) {
            throw new RuntimeException("Invalid Open Weather API Key");
        }

        URIBuilder uriBuilder = null;
        URI uri = null;

        try {
            uriBuilder = new URIBuilder(weatherBaseURL);
            uriBuilder.addParameter("lat",latitude);
            uriBuilder.addParameter("lon",longitude);
            uriBuilder.addParameter("appid",weatherAPIKey);
            uriBuilder.addParameter("exclude","minutely,hourly,daily,alerts");
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Exception in building URI for Weather API");
        }

        Logger.getAnonymousLogger().log(Level.INFO, uri.toString());
        return uri;
    }

    /**
     * HTTP call to Weather API
     * @param uri
     * @return
     */
    public static Weather getWeather(URI uri) {
        ObjectMapper mapper = new ObjectMapper();
        //Creating a HttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a HttpGet object
        HttpGet httpget = new HttpGet(uri);

        //Executing the Get request
        Weather response = null;
        try {
            HttpResponse httpresponse = httpclient.execute(httpget);
            response = httpclient.execute(httpget, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), Weather.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(response.getCurrent()==null) {
            throw new RuntimeException("Unable to retrieve weather for this location");
        }

        Logger.getAnonymousLogger().log(Level.INFO, response.toString());

        return response;
    }

    private static double convertToCelsius(double temperature){
        return temperature-273.15;
    }

    private static double convertToFarenheit(double temperature) {
        return (9/5*(temperature - 273.15) + 32);
    }

}
