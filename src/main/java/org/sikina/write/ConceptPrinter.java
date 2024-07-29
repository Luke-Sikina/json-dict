package org.sikina.write;

import org.sikina.process.Concept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConceptPrinter {
    @Value("${output.path:/tmp/dict.txt}")
    private String outputPath;

    private static final Logger LOG = LoggerFactory.getLogger(ConceptPrinter.class);

    public int print(List<Concept> concepts) {
        LOG.info("Formatting concepts for print");
        String dict = concepts.stream().map(Concept::toString)
            .collect(Collectors.joining("\n"));
        LOG.info("Printing concepts to {}", outputPath);
        try {
            Files.write(Path.of(outputPath), dict.getBytes());
        } catch (IOException e) {
            LOG.error("Error writing to file.", e);
            return 1;
        }
        LOG.info("Successfully printed");
        return 0;
    }
}
