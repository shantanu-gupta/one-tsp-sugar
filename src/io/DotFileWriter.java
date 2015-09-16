/**
 * 
 */
package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import tour.Tour;
import tspdef.Euc2DPosition;

/**
 * @author sg
 *
 */
public class DotFileWriter {
	private Tour tour;
	private Euc2DPosition[] nodePositions;
	
	/**
	 * 
	 */
	public DotFileWriter(Tour tour, Euc2DPosition[] nodePositions) {
		this.tour = tour;
		this.nodePositions = nodePositions;
	}
	
	public void writeFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		file.getParentFile().mkdirs();
		
		PrintWriter writer = new PrintWriter(file);
		writer.println("graph tspTour");
		writer.println("{");
		writer.println("node[fontsize=12 shape=point margin=0 label=\"\" "
						+ "width=0.1 fixedsize=true fillcolor=blue4"
						+ "]");
		
		// Specify node locations somehow.
		// Do something here.
		for (int i = 0; i < this.nodePositions.length; ++i) {
			Euc2DPosition p = this.nodePositions[i];
			writer.println(i + " [" + "pos=" + "\"" + p.x + "," + p.y + "\" "
							 +  "];");
		}
		
		// Printing the tour as one chain of edges.
		writer.print(this.tour.getNodeList().get(0));
		for (int i = 1; i < this.tour.getNodeList().size(); ++i) {
			writer.print(" -- " + this.tour.getNodeList().get(i));
		}
		writer.print(" -- " + this.tour.getNodeList().get(0) + ";\n");
		
		writer.println("}");
		writer.close();
	}
}
