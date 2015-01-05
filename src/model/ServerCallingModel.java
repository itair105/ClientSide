package model;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.org.apache.xpath.internal.operations.Bool;
import common.Observer;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerCallingModel implements Model {
    static int i = 0;
    Set<Observer> listeners = new HashSet<Observer>();
    String host = "127.0.0.1";
    int port = 5555;

    public ServerCallingModel() {
        /*try {
            *//*Socket socket = createSocket(host, port);
            BufferedWriter out = createWriter(socket);
            *//**//*ObjectInputStream in = createReader(socket);*//**//*
            out.write("Writing message " + i);
            out.close();*//*
            System.err.println("Connected to " + host + " on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    private ObjectOutputStream createWriter(Socket socket) throws IOException {
            return new ObjectOutputStream(socket.getOutputStream());
    }

    private ObjectInputStream createReader(Socket socket) throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void selectAlgorithm(String param) {
        try {
            Socket socket = createSocket(host, port);
            ObjectOutputStream out = createWriter(socket);
            createReader(socket);
            out.writeUTF("algorithm=" + param);
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectDomain(String param) {
        try {
            Socket socket = createSocket(host, port);
            ObjectOutputStream out = createWriter(socket);
            createReader(socket);
            out.writeUTF("domain=" + param);
            out.flush();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void solveDomain() {
        try {
            Socket socket = createSocket(host, port);
            ObjectOutputStream out = createWriter(socket);
            ObjectInputStream in = createReader(socket);
            out.writeUTF("start");
            out.flush();
            in.readObject();
            out.close();
            socket.close();
            notifyObservers();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCalculationRunning() {
        try {
            Socket socket = createSocket(host, port);
            ObjectOutputStream out = createWriter(socket);
            ObjectInputStream in = createReader(socket);
            out.writeUTF("is_calculating");
            out.flush();
            Boolean isCalculating = (Boolean)in.readObject();
            in.close();
            out.close();
            socket.close();
            return isCalculating;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public GraphDomain getDomain() {
        try {
            Socket socket = createSocket(host, port);
            ObjectOutputStream out = createWriter(socket);
            ObjectInputStream in = createReader(socket);
            out.writeUTF("displayState");
            out.flush();

            GraphDomain graphDomain = (GraphDomain) in.readObject();
            in.close();
            out.close();
            socket.close();
            return graphDomain;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Solution getSolution() {
        try {
            Socket socket = createSocket(host, port);
            ObjectOutputStream out = createWriter(socket);
            ObjectInputStream in = createReader(socket);
            out.writeUTF("getSolution");
            out.flush();
            Solution solution = (Solution) in.readObject();
            in.close();
            out.close();
            socket.close();
            return solution;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
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
