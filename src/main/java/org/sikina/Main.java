package org.sikina;

import org.sikina.parse.ConceptNodeParser;
import org.sikina.process.ConceptNodeProcessor;
import org.sikina.write.ConceptPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Autowired
    private ConceptNodeParser parser;

    @Autowired
    private ConceptNodeProcessor processor;

    @Autowired
    private ConceptPrinter printer;

    @Value("${output.path:/tmp/dict.txt}")
    private String outputPath;

    @Value("${input.path:/tmp/input.json}")
    private String inputPath;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return _ -> {
            LOG.info("Generating human readable data dictionary in {}", outputPath);
            File input = new File(inputPath);
            int exitCode = parser.parse(input)
                .map(processor::process)
                .map(printer::print)
                .orElse(1);
            LOG.info("Done. Exiting");
            System.exit(exitCode);
        };
    }
}