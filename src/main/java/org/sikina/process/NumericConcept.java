package org.sikina.process;

public record NumericConcept(String name, String path, float min, float max, int observations) implements Concept {
    @Override
    public String toString() {
        return """
            %s
                path: %s
                values: [%f, %f]
                observations: %s
            """.formatted(name, path, min, max, observations);
    }
}
