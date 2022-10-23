package org.example.model.mapbox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("place_type")
    private List<String> placeType;

    @JsonProperty("relevance")
    private int relevance;

    @JsonProperty("properties")
    private FeatureProperties properties;

    @JsonProperty("text")
    private String text;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("bbox")
    private List<Double> bbox;

    @JsonProperty("center")
    private List<Double> center;

    @JsonProperty("geometry")
    private FeatureGeometry geometry;

    @JsonIgnore()
    private Context context;
}

class FeatureProperties {

    @JsonProperty("short_code")
    private String shortCode;

    @JsonProperty("wikidata")
    private String wikidata;
}

class FeatureGeometry{
    @JsonProperty("type")
    private String type;

    @JsonProperty("coordinates")
    private List<Double> coordinates;
}

class Context {
    @JsonProperty("id")
    private String id;

    @JsonProperty("short_code")
    private String shortCode;

    @JsonProperty("wikidata")
    private String wikidata;

    @JsonProperty("text")
    private String text;
}