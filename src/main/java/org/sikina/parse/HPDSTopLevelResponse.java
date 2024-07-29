package org.sikina.parse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

public record HPDSTopLevelResponse(Results results, String searchQuery) {

    @JsonIgnoreProperties
    record Results(Map<String, HPDSConceptNode> phenotypes, Object info){};
}
