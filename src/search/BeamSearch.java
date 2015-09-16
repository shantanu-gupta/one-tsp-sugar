/**
 * 
 */
package search;

import java.util.ArrayList;
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
public class BeamSearch implements TSPRouteSearch {
	/**
	 * The seed tours from which we start our search.
	 */
	private List<Tour> initialTours;
		
	/**
	 * Size of the beam of paths we consider at a given iteration.
	 */
	private int beamWidth;
	
	/**
	 * The candidate generator.
	 */
	private MoveGen moveGenerator;
	
	/**
	 * How many iterations we wish to perform, equivalent to how deep in the
	 * search graph we wish to go. 
	 */
	private int searchDepth;
	
	/**
	 * Set up Beam Search parameters
	 * @param matrix The adjacency matrix.
	 * @param beamWidth The beam width.
	 * @param depth The search depth.
	 */
	public BeamSearch(AdjacencyMatrix matrix, int beamWidth, int depth) {
		this.beamWidth = beamWidth;
		this.searchDepth = depth;
		this.moveGenerator = new TwoEdgeSwapGenerator(matrix);
		this.initialTours = new ArrayList<Tour>();
		for (int i = 0; i < this.beamWidth; ++i) {
			this.initialTours.add(new NearestNeighbour(matrix).bestTour());
		}
	}

	/* (non-Javadoc)
	 * @see search.TSPRouteSearch#bestTour()
	 */
	@Override
	public Tour bestTour() {
		// Start with the best guess we can make.
		Tour best = Collections.min(this.initialTours);
		
		List<Tour> currentTours = this.initialTours;
		// Visited tours are supposed to be excluded from consideration
		// as the algorithm progresses.
		List<Tour> visitedTours = new ArrayList<Tour>();

		int depth = 0;
		while (depth < this.searchDepth) {
			visitedTours.addAll(currentTours);
			
			// Generate candidates.
			List<Tour> candidates = new ArrayList<Tour>();
			for (Tour t : currentTours) {
				candidates.addAll(this.moveGenerator.generateCandidateList(t));
			}
			
			candidates.removeAll(visitedTours);
			
			// Find the k best candidates, where k is the beam width.
			Collections.sort(candidates);
			currentTours = candidates.subList(0, this.beamWidth);			
			
			Tour bestCurrent = Collections.min(currentTours);
			if (bestCurrent.compareTo(best) < 0) {
				best = bestCurrent;
			}
			
			depth++;
		}
		
		return best;
	}

}
