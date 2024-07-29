package org.sikina.process;

public sealed interface Concept permits CategoricalConcept, NumericConcept {
    String name();
    String path();
    int observations();
}
