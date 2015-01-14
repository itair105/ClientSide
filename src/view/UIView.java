package view;

import common.Observer;
import model.GraphDomain;
import model.Solution;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class UIView implements Runnable {
	protected Shell shell;
	protected  SelectDomainWindow selectDomainWindow;
		
	public UIView(SelectDomainWindow selectDomainWindow, Display display, int width, int height, String title) {
		shell =new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);

		this.selectDomainWindow = selectDomainWindow;
		
		//this.addObserver(presenter);
		//presenter.setView(this);
	}
	
	abstract void initWidgets();
	
	@Override
	public void run() {
		initWidgets();
		shell.open();
		
		// Display the main shell when this shell is closed
		shell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				// TODO Auto-generated method stub
				Shell mainShell = shell.getDisplay().getShells()[0];
				mainShell.setVisible(true);
			}
		});
	}

	public abstract void displayDomain(GraphDomain domain);

	public abstract void displaySolution(Solution solution);
}
