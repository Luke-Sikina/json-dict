package org.sikina.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class ConceptNodeParser {

    private static final Logger LOG = LoggerFactory.getLogger(ConceptNodeParser.class);

    private final ObjectMapper mapper;


    @Autowired
    public ConceptNodeParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<Map<String, HPDSConceptNode>> parse(File rawJson) {
        if (!rawJson.exists()) {
            LOG.error("{} DNE. Exiting.", rawJson.getPath());
        }
        LOG.info("Parsing {}", rawJson.getName());
        TypeReference<HPDSTopLevelResponse> ref = new TypeReference<>() {};

        try {
            Map<String, HPDSConceptNode> nodes = mapper.readValue(rawJson, ref).results().phenotypes();
            LOG.info("Successfully parsed {} concepts", nodes.size());
            return Optional.of(nodes);
        } catch (IOException e) {
            LOG.error("Error parsing file: ", e);
            return Optional.empty();
        }
    }
}
