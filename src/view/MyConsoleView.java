package view;

import common.Observer;
import model.GraphDomain;
import model.Solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class MyConsoleView implements View {
    private String userAction;
    Set<Observer> listeners = new HashSet<Observer>();
    @Override
    public void start() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Possible Commands:");
        System.out.println("Pay Attention: algorithm dijkstra can't work on weightlessGraph !!");
        System.out.println("* domain=[weightedGraph or weightlessGraph]");
        System.out.println("* algorithm=[bfs or dijkstra]");
        System.out.println("* displayState");
        System.out.println("* start");
        String input;

        try {
            while (!(input=in.readLine()).equals("exit")) {
                userAction = input;
                notifyObservers();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayCurrentState(final GraphDomain domain) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(domain);
            }
        }).start();

    }

    @Override
    public void displaySolution(final Solution solution) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(solution);
            }
        }).start();
    }

    @Override
    public String getUserAction() {
        return userAction;
    }

    @Override
    public void displayError(final String error) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(error);
            }
        }).start();

    }

    @Override
    public void displayIsCalculating(boolean calculationRunning) {
        if (calculationRunning) {
            System.out.println("Model is calculating currently...");
        } else {
            System.out.println("Model is not calculating currently.");
        }
    }

    @Override
    public void addObserver(Observer observer) {
        listeners.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        listeners.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer listener : listeners) {
            listener.update(this);
        }

    }
}
