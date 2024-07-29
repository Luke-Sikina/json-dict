package org.sikina.parse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public record HPDSConceptNode(
    String name, boolean categorical, boolean continuous, String info, List<String> categoryValues, int observationCount, List<String> resourceAvailability, float min, float max
) {
}
