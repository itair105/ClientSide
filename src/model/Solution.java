package model;

import java.io.Serializable;
import java.util.List;

public class Solution implements Serializable {
    List<Node> path;
    private String problem;

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Solution() {
    }

    public Solution(List<Node> path, String problem)
    {
        this.problem = problem;
        this.path = path;
    }

    public List<Node> getPath() {
        return path;
    }

    @Override
    public String toString() {
        if (path == null) {
            return "No Path found";
        }

        return "Solution{" +
                "path=" + path +
                '}';
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }
}
