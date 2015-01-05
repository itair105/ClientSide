package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WeightGraphDomain extends AbstractGraphDomain implements Serializable{
    public Map<String, Set<WeightedEdge>> adjacencies = new HashMap<String, Set<WeightedEdge>>();
    public Map<String, Set<AbstractEdge>> abstractAdjacencies = new HashMap<String, Set<AbstractEdge>>();

    public void addEdge( WeightedEdge e ) {
        super.addEdge(e);
        if (!adjacencies.containsKey(e.getN1().getId())) {
            adjacencies.put(e.getN1().getId(), new HashSet<WeightedEdge>());
            abstractAdjacencies.put(e.getN1().getId(), new HashSet<AbstractEdge>());
        }

        if (!adjacencies.containsKey(e.getN2().getId())) {
            adjacencies.put(e.getN2().getId(), new HashSet<WeightedEdge>());
            abstractAdjacencies.put(e.getN2().getId(), new HashSet<AbstractEdge>());
        }

        adjacencies.get(e.getN1().getId()).add(e);
        adjacencies.get(e.getN2().getId()).add(e);
        abstractAdjacencies.get(e.getN1().getId()).add(e);
        abstractAdjacencies.get(e.getN2().getId()).add(e);
    }

    @Override
    public Map<String, Set<AbstractEdge>> getAdjacencies() {
        return abstractAdjacencies;
    }

    @Override
    public int hashCode() {
        int i = 0;
        Set<String> nodeKeys = this.nodeMap.keySet();
        for (String nodeKey : nodeKeys) {
            i += nodeKey.hashCode();
        }

        Set<String> edgeKeys = this.edgeMap.keySet();
        for (String edgeKey : edgeKeys) {
            i += edgeKey.hashCode();
            WeightedEdge weightedEdge = (WeightedEdge) edgeMap.get(edgeKey);
            i += weightedEdge.weight;
        }

        return i;
    }
}
