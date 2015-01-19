package view;

import common.Observer;
import common.ServerProperties;
import model.GraphDomain;
import model.ServerCallingModel;
import model.Solution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class SelectDomainWindow extends BasicWindow implements View {
    public static final String FILE = "properties.xml";
    private String userAction;
    public static final String WEIGHTLESS = "Shortest path (edges without weight)";
    public static final String WITH_WEIGHT = "Quickest path (edges with weight)";

    private UIView graphWindow;

    public SelectDomainWindow(int width, int height, String title) {
        super(width, height, title);
    }

    Menu menuBar, fileMenu;

    MenuItem fileMenuHeader;

    MenuItem fileExitItem;
    MenuItem loadPropertiesItem;

    @Override
    void initWidgets() {
        shell.setLayout(new GridLayout(1, false));
        menuBar = new Menu(shell, SWT.BAR);
        fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuHeader.setText("&File");

        fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);



        loadPropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
        loadPropertiesItem.setText("&Load Properties");
        loadPropertiesItem.addSelectionListener(new LoadPropertiesListener());

        fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
        fileExitItem.setText("E&xit");
        fileExitItem.addSelectionListener(new FileExitItemListener());

        shell.setMenuBar(menuBar);
        final Combo combo = new Combo(shell, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        String items[] = {WEIGHTLESS, WITH_WEIGHT};
        combo.setItems(items);

        Button btnSelectModel = new Button(shell, SWT.PUSH);
        btnSelectModel.setText("Start");
        btnSelectModel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

        btnSelectModel.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                String selectedGame = combo.getText();
                if (WEIGHTLESS.equals(combo.getText())) {
                    userAction = "domain=weightlessGraph";
                } else if (WITH_WEIGHT.equals(combo.getText())) {
                    userAction = "domain=weightedGraph";
                }

                notifyObservers();

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (selectedGame.equals(WEIGHTLESS)) {
                    graphWindow = new WeightlessGraphWindow(SelectDomainWindow.this, display, 600, 600, "Weightless");
                    graphWindow.run();

                } else {
                    graphWindow = new WeightedGraphWindow(SelectDomainWindow.this, display, 600, 600, "Weighted");
                    graphWindow.run();
                }

                shell.setVisible(false);

            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void start() {
        run();
    }

    @Override
    public void displayCurrentState(GraphDomain domain) {
        graphWindow.displayDomain(domain);
    }

    @Override
    public void displaySolution(Solution solution) {
        graphWindow.displaySolution(solution);
    }

    @Override
    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    @Override
    public void displayError(String message) {

    }

    @Override
    public void displayIsCalculating(boolean calculationRunning) {

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

    Set<Observer> listeners = new HashSet<Observer>();


    class LoadPropertiesListener implements SelectionListener {
        public void widgetSelected(SelectionEvent event) {
            FileInputStream fis = null;
            try {
                FileDialog fd = new FileDialog(shell, SWT.OPEN);
                fd.setText("Open");
                fd.setFilterPath("C:/");
                String[] filterExt = { "*.xml" };
                fd.setFilterExtensions(filterExt);
                String selected = fd.open();
                fis = new FileInputStream(selected);
                BufferedInputStream bis = new BufferedInputStream(fis);
                XMLDecoder xmlDecoder = new XMLDecoder(bis);
                ServerProperties properties = (ServerProperties)xmlDecoder.readObject();
                userAction = "settings=" + properties.getIp() + ";" + properties.getPort();
                notifyObservers();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


        @Override
        public void widgetDefaultSelected(SelectionEvent selectionEvent) {

        }
    }

    class FileExitItemListener implements SelectionListener {
        public void widgetSelected(SelectionEvent event) {
            shell.close();
            display.dispose();
            System.exit(0);
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent selectionEvent) {

        }
    }
}
