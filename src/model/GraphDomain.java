package model;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public interface GraphDomain {
    SortedMap< String, Node>  getNodeMap();
    Map< String, AbstractEdge> getEdgeMap();
    Node getStartNode();
    Node getTargetNode();
    Map<String, Set<AbstractEdge>> getAdjacencies();

}
