package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WeightLessGraphDomain extends AbstractGraphDomain implements Serializable {
    public Map<String, Set<AbstractEdge>> adjacencies = new HashMap<String, Set<AbstractEdge>>();
    public void addEdge( WeightLessEdge e ) {
        super.addEdge(e);
        if (!adjacencies.containsKey(e.getN1().getId())) {
            adjacencies.put(e.getN1().getId(), new HashSet<AbstractEdge>());
        }

        if (!adjacencies.containsKey(e.getN2().getId())) {
            adjacencies.put(e.getN2().getId(), new HashSet<AbstractEdge>());
        }

        adjacencies.get(e.getN1().getId()).add(e);
        adjacencies.get(e.getN2().getId()).add(e);
    }

    @Override
    public Map<String, Set<AbstractEdge>> getAdjacencies() {
        return adjacencies;
    }

    @Override
    public int hashCode() {
        int i = 2000000000;
        Set<String> nodeKeys = this.nodeMap.keySet();
        for (String nodeKey : nodeKeys) {
            i += nodeKey.hashCode();
        }

        Set<String> edgeKeys = this.edgeMap.keySet();
        for (String edgeKey : edgeKeys) {
            i += edgeKey.hashCode();
        }

        return i;
    }
}