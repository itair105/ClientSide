package model;

import common.Observable;

/**
 * Created by user on 1/3/2015.
 */
public interface Model extends Observable {
    void selectAlgorithm(String param);

    void selectDomain(String param);

    void solveDomain();

    boolean isCalculationRunning();

    GraphDomain getDomain();

    Solution getSolution();
}
