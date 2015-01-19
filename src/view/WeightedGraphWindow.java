package view;

import model.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeightedGraphWindow extends UIView {

	public static final String BFS = "BFS";
	public static final String DIKJSTRA = "Dikjstra";
	private static final float PI = 3.14159265359f;
	private Canvas canvas;
	final Map<Node, Point> nodeToPoint = new HashMap<Node, Point>();
	private Set<AbstractEdge> solutionEdges = null;

	public WeightedGraphWindow(SelectDomainWindow selectDomainWindow, Display display, int width, int height, String title) {
		super(selectDomainWindow, display, width, height, title);

	}

	public void clearSolutions() {
		if (solutionEdges != null) {
			solutionEdges.clear();
		}
	}
	
	@Override
	void initWidgets() {
		
		shell.setLayout(new RowLayout(SWT.VERTICAL));

		Label lblAlgorithm = new Label(shell, SWT.NONE);
		lblAlgorithm.setText("Choose algorithm: ");
		
		final Combo combo = new Combo(shell, SWT.READ_ONLY);
		combo.setLayoutData(new RowData(150, 20));
	    String items[] = {BFS, DIKJSTRA};
	    combo.setItems(items);
		combo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				if (combo.getText().equals(BFS)) {
					selectDomainWindow.setUserAction("algorithm=bfs");
				} else if (combo.getText().equals(DIKJSTRA)) {
					selectDomainWindow.setUserAction("algorithm=dijkstra");
				}

				selectDomainWindow.notifyObservers();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent selectionEvent) {

			}
		});


		Button startButton = new Button(shell, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new RowData(100, 20));
				
		startButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				clearSolutions();
				selectDomainWindow.setUserAction("start");
				selectDomainWindow.notifyObservers();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent selectionEvent) {

			}
		});

		canvas = new Canvas(shell, SWT.BORDER);
		canvas.setLayoutData(new RowData(400, 400));
		// Create a paint handler for the canvas

		showDomain();
	}

	@Override
	public void displaySolution(Solution solution) {
		java.util.List<Node> path = solution.getPath();
		solutionEdges = new HashSet<AbstractEdge>();
		Node prevNode = null;
		if (path != null) {
			for (Node node : path) {
				if (prevNode == null) {
					prevNode = node;
					continue;
				}

				solutionEdges.add(new WeightedEdge(prevNode, node, 1));
				prevNode = node;
			}
		}
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				canvas.redraw();
			}
		});
	}

	@Override
	public void displayDomain(final GraphDomain domain) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				canvas.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						if (domain == null) {
							return;
						}
						// Do some drawing
						GC gc = e.gc;

						int nodeWidth = 30;
						int nodeHeight = 30;
						float nodeNum = domain.getNodeMap().size();

						int x, y;
						int length = 150;
						float angle = 0.0f;
						float angleStepSize = (2 / nodeNum) * PI;

						// go through all angles from 0 to 2 * PI radians
						for (Node node : domain.getNodeMap().values()) {
							String nodeName = node.getId();
							if (domain != null && domain.getStartNode().equals(node)) {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
							} else if (domain != null && domain.getTargetNode().equals(node)) {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_GREEN));
							} else {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
							}

							x = 200 + (int)(length * Math.cos (angle));
							y = 200 + (int)(length * Math.sin(angle));
							nodeToPoint.put(node, new Point(x, y));

							gc.drawOval(x, y, nodeWidth, nodeHeight);
							gc.drawText(nodeName.substring(4), x + 12, y + 4);
							angle += angleStepSize;
						}


						for (AbstractEdge edge : domain.getEdgeMap().values()) {
							WeightedEdge weightedEdge = (WeightedEdge)edge;
							if (solutionEdges == null || solutionEdges.contains(edge)) {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
							} else {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_GRAY));
							}

							Point n1Point = nodeToPoint.get(edge.getN1());
							Point n2Point = nodeToPoint.get(edge.getN2());
							if (!n1Point.equals(n2Point)) {
								gc.drawLine(n1Point.x + 10, n1Point.y + 10, n2Point.x + 10, n2Point.y + 10);
								Point middlePoint = new Point((n1Point.x + n2Point.x) / 2 - 2, (n2Point.y + n1Point.y) / 2 - 5);
								gc.drawText(String.valueOf(weightedEdge.getWeight()), middlePoint.x, middlePoint.y);
							}


						}

					}


				});

				canvas.redraw();
			}
		});

	}

	private int getAngle(int nodeNum, int i) {
		return (int)(((double)i/nodeNum) * 360);
	}

	private void showDomain() {
		selectDomainWindow.setUserAction("displayState");
		selectDomainWindow.notifyObservers();
	}
}
