import java.io.FileNotFoundException;

import io.DotFileWriter;
import io.Euc2DTSPParser;
import search.BeamSearch;
import search.TSPRouteSearch;
import tour.Tour;
import tspdef.AdjacencyMatrix;
import tspdef.Euc2DPosition;

/**
 * 
 */

/**
 * @author sg
 *
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "/home/sg/Downloads/tsp/sourcesSymmetricTSP/ch130.tsp";
		Euc2DTSPParser parser = new Euc2DTSPParser(filename);
		AdjacencyMatrix matrix = parser.getAdjacencyMatrix();
		Euc2DPosition[] nodes  = parser.getNodes();
		for (Euc2DPosition p : nodes) {
			System.out.println(String.valueOf(p.x) + " " + String.valueOf(p.y));
		}
		
		TSPRouteSearch searcher = new BeamSearch(matrix, 7, 100);
		Tour bestTour = searcher.bestTour();

		System.out.println(bestTour.toString());
		System.out.println(bestTour.getCost());
		
		try {
			new DotFileWriter(bestTour, nodes).writeFile("./output/tsp_out_ch130.dot");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}