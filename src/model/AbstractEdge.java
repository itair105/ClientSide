package model;

import java.io.Serializable;

/**
 * Created by user on 12/13/2014.
 */
public class AbstractEdge implements Serializable {
    protected final Node n1;
    protected final Node n2;
    protected final String id;

    public AbstractEdge(Node n1, Node n2) {
        if ( n1 == null || n2== null ) throw new IllegalArgumentException( "Nodes must not be null!" );
        this.n1 = n1;
        this.n2 = n2;
        this.id = computeDefaultEdgeId( n1, n2 );
    }

    public String getId() {
        return id;
    }


    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    public String toString() {
        return "Edge id: " + id + " n1: " + n1.getId() + " n2: " + n2.getId();
    }

    public static String computeDefaultEdgeId( Node n1, Node n2 ) {
        return n1.getId() +"<->" + n2.getId();
    }
}
