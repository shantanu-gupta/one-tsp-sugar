/**
 * 
 */
package search;

import java.util.Random;

import movegenerator.MoveGen;
import movegenerator.TwoEdgeSwapGenerator;
import tour.Tour;
import tspdef.AdjacencyMatrix;

/**
 * @author sg
 *
 */
public class SimulatedAnnealingSearch implements TSPRouteSearch {
	/**
	 * The initial temperature in the schedule.
	 */
	private float initialTemp;
	
	/**
	 * The candidate generator. 
	 */
	private MoveGen moveGenerator;
	
	/**
	 * The seed tour. 
	 */
	private Tour initialTour;
	
	/**
	 * Random number generator.
	 */
	private Random rng;
	
	/**
	 * The multiplication factor in the temperature update.
	 */
	private final double ALPHA = 0.99999;
	/**
	 * Initialise the search parameters.
	 */
	public SimulatedAnnealingSearch(AdjacencyMatrix matrix) {
		this.initialTemp = (float) 1e5;
		this.moveGenerator = new TwoEdgeSwapGenerator(matrix);
		this.initialTour = new NearestNeighbour(matrix).bestTour();
		this.rng = new Random();
	}

	private float getNewTemp(float temp) {
		return (float) (temp * this.ALPHA);
	}	
	
	/* (non-Javadoc)
	 * @see search.TSPRouteSearch#bestTour()
	 */
	@Override
	public Tour bestTour() {
		Tour currentTour = this.initialTour;
		Tour bestSoFar = currentTour;
		float temp = this.initialTemp;
		while (temp > Float.MIN_NORMAL) {
			Tour candidateTour 
				= this.moveGenerator.generateCandidate(currentTour);
			
			// Metropolis sampling criterion.
			float delta 
				= (float) (candidateTour.getCost() - currentTour.getCost());
			if (delta < 0) {
				currentTour = candidateTour;
			} else {
				float p = this.rng.nextFloat();
				float p_threshold = (float) Math.exp(-delta / temp);
				if (p < p_threshold) {
					currentTour = candidateTour;
				} else {
					// do nothing. currentTour doesn't change.
				}
			}
			
			if (currentTour.getCost() < bestSoFar.getCost()) {
				bestSoFar = currentTour;
			}
			
			temp = this.getNewTemp(temp);
		}
		
		return bestSoFar;
	}
}