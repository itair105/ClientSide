package model;

import java.io.Serializable;

/**
 * Created by user on 12/13/2014.
 */
public class Node implements Serializable{
    private String id;

    public Node() {
    }

    public Node(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public synchronized String toString() {
        return id;
    }
}
