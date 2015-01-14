package view;

import common.Observer;
import model.GraphDomain;
import model.Solution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import java.util.HashSet;
import java.util.Set;

public class SelectDomainWindow extends BasicWindow implements View{
    private String userAction;
    public static final String WEIGHTLESS = "Shortest path (edges without weight)";
    public static final String WITH_WEIGHT = "Quickest path (edges with weight)";

    private UIView graphWindow;

    public SelectDomainWindow(int width, int height, String title) {
        super(width, height, title);
    }

    @Override
    void initWidgets() {
        shell.setLayout(new GridLayout(1, false));

        final Combo combo = new Combo(shell, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        String items[] = {WEIGHTLESS, WITH_WEIGHT};
        combo.setItems(items);
        combo.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (WEIGHTLESS.equals(combo.getText())) {
                    userAction = "domain=weightlessGraph";
                } else if (WITH_WEIGHT.equals(combo.getText())) {
                    userAction = "domain=weightedGraph";
                }

                notifyObservers();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        Button btnSelectModel = new Button(shell, SWT.PUSH);
        btnSelectModel.setText("Start");
        btnSelectModel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

        btnSelectModel.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // Choose window according to the game (using a factory)
                String selectedGame = combo.getText();
                if (selectedGame.equals(WEIGHTLESS)) {
                    graphWindow = new WeightlessGraphWindow(SelectDomainWindow.this, display, 600, 600, "Weightless");
                    graphWindow.run();
                    shell.setVisible(false);
                }
                else {
                    /*weightGraphWindow = new WeightGraphWindow(SelectDomainWindow.this, display, 400, 300, "With weight");
                    weightGraphWindow.run();*/
                }
                //shell.setVisible(false);

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
}
