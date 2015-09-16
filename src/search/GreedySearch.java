/**
 * 
 */
package search;

import java.util.Collections;
import java.util.List;

import movegenerator.MoveGen;
import movegenerator.TwoEdgeSwapGenerator;
import tour.Tour;
import tspdef.AdjacencyMatrix;

/**
 * @author sg
 *
 */
public class GreedySearch implements TSPRouteSearch {
	/**
	 * The seed tour for the search.
	 */
	private Tour initialTour;
	
	/**
	 * The candidate generator. 
	 */
	private MoveGen moveGenerator;
	
	/**
	 * Creates a greedy searcher.
	 * @param adjacencyMatrix The adjacency matrix.
	 */
	public GreedySearch(AdjacencyMatrix matrix) {
		this.moveGenerator = new TwoEdgeSwapGenerator(matrix);
		this.initialTour = new NearestNeighbour(matrix).bestTour();
	}
	
	/* (non-Javadoc)
	 * @see search.TSPRouteSearch#bestTour()
	 */
	@Override
	public Tour bestTour() {
		// Fairly self-explanatory. Finds locally better paths as long as it 
		// can.
		Tour currentTour = initialTour;
		Tour bestSoFar;
		do {
			bestSoFar = currentTour; 
			List<Tour> choices 
				= this.moveGenerator.generateCandidateList(currentTour);			
			currentTour = Collections.min(choices);
		} while (currentTour.compareTo(bestSoFar) < 0);
		
		return bestSoFar;
	}
}