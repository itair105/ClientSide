package model;

import common.TreeStringComparator;

import java.io.Serializable;
import java.util.*;

/**
 * Created by user on 12/13/2014.
 */
public abstract class AbstractGraphDomain implements GraphDomain, Serializable {
    public static final Comparator<String> treeMapComparator = new TreeStringComparator();
    protected SortedMap< String, Node> nodeMap = null;
    protected Map< String, AbstractEdge > edgeMap = null;
    protected Node startNode;
    protected Node endNode;

    public AbstractGraphDomain() {
        nodeMap = new TreeMap< String, Node>(treeMapComparator);
        edgeMap = new HashMap< String, AbstractEdge>();
    }

    public void setNodeMap(SortedMap<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public void setEdgeMap(Map<String, AbstractEdge> edgeMap) {
        this.edgeMap = edgeMap;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    @Override
    public Node getTargetNode() {
        return endNode;
    }

    @Override
    public Node getStartNode() {
        return startNode;
    }

    public void addNode( Node n ) {
        if ( n == null ) throw new IllegalArgumentException( "Argument must be non-null!" );
        if ( nodeMap.get( n.getId() ) != null ) throw new IllegalArgumentException( "Attempt to add node with duplicate id <" + n.getId() + ">" );
        nodeMap.put( n.getId(), n);
    }

    @Override
    public SortedMap<String, Node> getNodeMap() {
        return nodeMap;
    }

    @Override
    public Map<String, AbstractEdge> getEdgeMap() {
        return edgeMap;
    }

    protected void addEdge(AbstractEdge e) {
        if ( edgeMap.get( e.getId() ) != null ) {
            // tried to add same edge twice
            return;
        }

        edgeMap.put( e.getId(), e );
    }


    ////////////////////////
    public static int getMaxAbstractEdgesForGraph( int nodeCount ) {
        if  ( nodeCount < 0 ) throw new IllegalArgumentException( "nodeCount must be >= 0!" );
        if ( nodeCount == 0 ) return 0;
        int n = nodeCount - 1;
        // Use math formula sum of first n integers where n here is nodeCount - 1
        int maxAbstractEdges = ( n * n + n )/2;
        return maxAbstractEdges;
    }
    ////////////////////////
    public boolean hasSelfLoops() {
        for ( AbstractEdge e : edgeMap.values() )
            if ( e.getN1() == e.getN2() )
                return true;
        return false;
    }

    ////////////////////////
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "Graph Object Dump:\n" );
        sb.append( "\tmodel.Node Count: " + getNodeMap().size() + "\n" );
        sb.append( "\tmodel.AbstractEdge Count: "  + getEdgeMap().size() + "\n" );
        sb.append( "\tNodes: \n" );
        int nodeIndex = 0;
        for ( Node n : nodeMap.values() )
            sb.append( "\t\tmodel.Node[ " + nodeIndex++ + " ]: " + n.toString() + "\n" );
        sb.append( "\tAbstractEdges: \n" );
        int AbstractEdgeIndex = 0;
        for ( AbstractEdge e : edgeMap.values() )
            sb.append( "\t\tmodel.AbstractEdge[ " + AbstractEdgeIndex++ + " ]: " + e.toString() + "\n" );

        sb.append("Start Node: " + startNode + "\n");
        sb.append("End Node: " + endNode);
        return sb.toString();
    }
}

