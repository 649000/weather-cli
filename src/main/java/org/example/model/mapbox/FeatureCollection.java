package org.example.model.mapbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureCollection {

    @JsonProperty("type")
    private String type;

    @JsonProperty("query")
    private List<String> query;

    @JsonProperty("features")
//    @JsonIgnore()
    private List<Feature> features;

    @JsonProperty("attribution")
    private String attribution;

}
