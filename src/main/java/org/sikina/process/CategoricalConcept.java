package org.sikina.process;

import java.util.List;

public record CategoricalConcept(String name, String path, List<String> values, int observations) implements Concept {
    @Override
    public String toString() {
        return """
            %s
                path: %s
                values: [%s]
                observations: %s
            """.formatted(name, path, String.join(", ", values), observations);
    }
}
