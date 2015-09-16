/**
 * 
 */
package search;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movegenerator.MoveGen;
import movegenerator.TwoEdgeSwapGenerator;
import tour.Tour;
import tspdef.AdjacencyMatrix;

/**
 * @author sg
 *
 */
public class TabuSearch implements TSPRouteSearch {
	/**
	 * Contains tabu tours along with the time they were added at.
	 * Entries are supposed to live only for tabuTenure iterations.
	 */
	private Map<Tour, Integer> tabuList;
	
	/**
	 * How long entries live in the tabu list.
	 */
	private int tabuTenure;
	
	/**
	 * The seed tour.
	 */
	private Tour initialTour;
	
	/**
	 * The move generator. Generates candidate tours.
	 */
	private MoveGen moveGenerator;
	
	/**
	 * XXX: Hard-coded constant governing how long the search runs.
	 */
	private final int MAX_ITERATIONS = 1000;
	
	/**
	 * XXX : maximum number of iterations we will go before we give up on the
	 * search path traversed from the current seed.
	 */
	private final int MAX_FUTILE_ITERATIONS = 100;
	
	/**
	 * The adjacency matrix for the given graph. 
	 */
	private AdjacencyMatrix matrix;
	
	/**
	 * Sets up a TabuSearch object
	 * @param matrix The adjacency matrix containing inter-node distances.
	 * @param tabuTenure How many iterations an entry stays tabu for.
	 */
	public TabuSearch(AdjacencyMatrix matrix, int tabuTenure) {
		this.matrix = matrix;
		this.tabuList = new HashMap<Tour, Integer>();
		this.tabuTenure = tabuTenure;
		this.initialTour = new NearestNeighbour(matrix).bestTour();
		this.moveGenerator = new TwoEdgeSwapGenerator(matrix);
	}

	/* (non-Javadoc)
	 * @see search.TSPRouteSearch#bestTour()
	 */
	@Override
	public Tour bestTour() {
		// TODO Auto-generated method stub
		Tour current = this.initialTour;
		Tour best = current;
		
		int futileIterations = 0;
		int currentIteration = 0;
		while (currentIteration < this.MAX_ITERATIONS) {
			currentIteration++;
			
			// Find our choices.
			List<Tour> choices = this.moveGenerator
									 .generateCandidateList(current);
			
			// Arrange choices in increasing order of cost, or decreasing order
			// of utility.
			Collections.sort(choices);
			
			// Find the best candidate tour which is not in our tabu list, or
			// so good that it overrides the tabu list (aspiration).
			for (Tour t : choices) {
				// Not comparing with current for now : can accept non-tabu
				// but worse moves. Is this bad?
				if (t.compareTo(best) < 0	// aspiration criterion
					|| !this.tabuList.containsKey(t)) {
					current = t;
					break;
				}
			}
			
			// Add the best candidate to the tabu list.
			this.tabuList.put(current, currentIteration);
			
			// Clean up the tabu list.
			for (Tour t : this.tabuList.keySet()) {
				if (this.tabuList.get(t) 
						+ this.tabuTenure > currentIteration) {
					this.tabuList.remove(t);
				}
			}

			// Update the best tour found so far, if this one is better.
			// This path through the search space is not futile any more.
			if (current.compareTo(best) < 0) {
				best = current;
				futileIterations = 0;
			} else {
				// We have been going down the wrong line a while more...
				futileIterations++;
			}
			
			if (futileIterations == this.MAX_FUTILE_ITERATIONS) {
				// Enough is enough! We need to start afresh.
				current = new NearestNeighbour(this.matrix).bestTour();
				futileIterations = 0;
			}
		}
		
		return best;
	}

}
