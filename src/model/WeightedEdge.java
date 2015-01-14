package model;

import java.io.Serializable;

/**
 * Created by user on 12/13/2014.
 */
public final class WeightedEdge extends AbstractEdge implements Serializable {
    double weight;
    public WeightedEdge(Node n1, Node n2, double weight) {
        super(n1, n2);
        this.weight = weight;
    }

    public String toString() {
        return "Edge id: " + id + " n1: " + n1.getId() + " n2: " + n2.getId() + " weight: " + weight;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }

        WeightedEdge that = (WeightedEdge) o;

        if (Double.compare(that.weight, weight) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
