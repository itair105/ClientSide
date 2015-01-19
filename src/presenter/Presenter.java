package presenter;

import common.Command;
import common.Observable;
import common.Observer;
import common.ServerProperties;
import model.Model;
import model.ServerCallingModel;
import model.WeightGraphDomain;
import view.MyConsoleView;
import view.SelectDomainWindow;
import view.View;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Presenter implements Observer {
    private View view;
    private Model model;
    Map<String, Command> stringCommandMap = new HashMap<String, Command>();

    public Presenter(final View view, final Model model) {
        this.view = view;
        this.model = model;

        stringCommandMap.put("algorithm", new Command() {
            @Override
            public void doCommand(String param) {
                model.selectAlgorithm(param);
            }
        });
        stringCommandMap.put("domain", new Command() {
            @Override
            public void doCommand(String param) {
                try {
                    model.selectDomain(param);
                } catch (IllegalArgumentException e) {
                    view.displayError(e.getMessage());
                }
            }
        });

        stringCommandMap.put("settings", new Command() {
                    @Override
                    public void doCommand(String param) {
                        String ip = param.substring(0, param.indexOf(";"));
                        int port = Integer.parseInt(param.substring(param.indexOf(";") + 1));
                        model.setPort(port);
                        model.setIp(ip);
                    }
                });

        stringCommandMap.put("start", new Command() {
                    @Override
                    public void doCommand(String param) {
                        model.solveDomain();
                    }
                });
        stringCommandMap.put("displayState", new Command() {
            @Override
            public void doCommand(String param) {
                view.displayCurrentState(model.getDomain());
            }
        });
    }

    @Override
    public void update(Observable observable) {
        if (observable == view) {
            String userAction = view.getUserAction();
            String commandType = userAction.substring(0, userAction.indexOf("=") > 0 ? userAction.indexOf("=") : userAction.length());
            String commandParam = "";
            if (userAction.indexOf("=") >= 0) {
                commandParam = userAction.substring(userAction.indexOf("=") + 1);
            }

            final Command command = stringCommandMap.get(commandType);
            if (command != null) {
                final String finalCommandParam = commandParam;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        command.doCommand(finalCommandParam);
                    }
                }).start();

            } else {
                view.displayError("No such command");
            }
        } else  if (observable == model) {
            view.displaySolution(model.getSolution());
        }
    }


    public static void main( String [] args ) {
        Model model;
        if (args.length > 0 && args[0].equals("createXml")) {
            try {
                FileOutputStream fos = new FileOutputStream(SelectDomainWindow.FILE);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                XMLEncoder xmlEncoder = new XMLEncoder(bos);
                xmlEncoder.writeObject(new ServerProperties("127.0.0.1", 5555));
                xmlEncoder.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            return;
        } else {
            try {
                FileInputStream fis = new FileInputStream(SelectDomainWindow.FILE);
                BufferedInputStream bis = new BufferedInputStream(fis);
                XMLDecoder xmlDecoder = new XMLDecoder(bis);
                ServerProperties properties = (ServerProperties)xmlDecoder.readObject();
                model = new ServerCallingModel(properties.getIp(), properties.getPort());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }

        View view = new SelectDomainWindow(600,600,"Select Domain");


        Presenter presenter = new Presenter(view, model);
        view.addObserver(presenter);
        model.addObserver(presenter);
        view.start();
    }
}
