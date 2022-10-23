package org.example.model.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Current {
    @JsonProperty("dt")
    private double dt;

    @JsonProperty("sunrise")
    private double sunrise;

    @JsonProperty("sunset")
    private double sunset;

    @JsonProperty("temp")
    private double temp;

    @JsonProperty("feels_like")
    private double feelsLike;

    @JsonProperty("pressure")
    private double pressure;

    @JsonProperty("humidity")
    private double humidity;

    @JsonProperty("dew_point")
    private double dewPoint;

    @JsonProperty("uvi")
    private double uvi;

    @JsonProperty("clouds")
    private double clouds;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind_speed")
    private double windSpeed;

    @JsonProperty("wind_deg")
    private double windDegree;

    @JsonProperty("weather")
    private List<CurrentWeather> currentWeatherList;

    @JsonProperty("rain")
    private CurrentWeatherRain currentWeatherRain;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class CurrentWeatherRain {

    @JsonProperty("1h")
    private double oneH;
}
