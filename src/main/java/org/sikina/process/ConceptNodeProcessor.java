package org.sikina.process;

import org.sikina.parse.HPDSConceptNode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConceptNodeProcessor {

    public List<Concept> process(Map<String, HPDSConceptNode> nodes) {
        Map<String, HPDSConceptNode> leafNodes = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>(nodes.keySet());
        paths.sort(Comparator.comparingInt(String::length));
        paths.forEach(p -> addNodeToMap(leafNodes, nodes.get(p)));

        return leafNodes.values().stream()
            .sorted(Comparator.comparing(HPDSConceptNode::name))
            .map(this::toConcept)
            .toList();

    }

    private Concept toConcept(HPDSConceptNode node) {
        String name = List.of(node.name().split("\\\\")).getLast(); // leaf node of concept path
        if (node.categoryValues() == null || node.categoryValues().isEmpty()) {
            return new NumericConcept(name, node.name(), node.min(), node.max(), node.observationCount());
        } else {
            return new CategoricalConcept(name, node.name(), node.categoryValues(), node.observationCount());
        }
    }

    private void addNodeToMap(Map<String, HPDSConceptNode> leafNodes, HPDSConceptNode node) {
        leafNodes.put(node.name(), node);
        List<String> segments = List.of(node.name().split("\\\\"));
        for (int i = 0; i < segments.size() - 1; i++) {
            leafNodes.remove(String.join("\\", segments.subList(0, i + 1)));
        }
    }
}
