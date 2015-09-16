/**
 * 
 */
package movegenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import tour.Tour;
import tspdef.AdjacencyMatrix;

/**
 * @author sg
 *
 */
public class TwoEdgeSwapGenerator implements MoveGen {
    private AdjacencyMatrix adjacencyMatrix;
    private Random rng;
    
    /**
     * Creates 2-opt swaps and attaches cost to the resulting tours.
     * @param matrix The adjacency matrix
     */
    public TwoEdgeSwapGenerator(AdjacencyMatrix matrix) {
        this.adjacencyMatrix = matrix;
        this.rng = new Random();
    }
    
    /* (non-Javadoc)
     * @see movegenerator.MoveGen#moveGen(tour.Tour)
     * 
     * Gives a list of possible permutations of a given tour.
     * Uses 2-opt swaps to create the permutations.
     */
    @Override
    public List<Tour> generateCandidateList(Tour currentTour) {
        //  Generate a collection of 2-edge-exchange tours from currentTour.
        List<Tour> choices = new ArrayList<Tour>();
        int numCities = this.adjacencyMatrix.getNumCities();

        for(short i = 0; i < (numCities + 1)/ 2; i++) { // integer division
            for(short j = (short) (i + 2); j < numCities; j++) {
            	Pair<Short, Short> inds 
            		= new ImmutablePair<Short, Short>(i, j);
            	TwoEdgeSwap swap 
            		= new TwoEdgeSwap(inds, currentTour, this.adjacencyMatrix);
                choices.add(swap.getSwappedTour());
            }
        }
        
        return choices;
    }

	/* (non-Javadoc)
	 * @see movegenerator.MoveGen#generateCandidate(tour.Tour)
	 * 
	 * Samples one candidate tour from the set of permutations of the given
	 * tour.
	 */
	@Override
	public Tour generateCandidate(Tour currentTour) {
		int numCities = currentTour.getNodeList().size();
		short i = (short) this.rng.nextInt((numCities + 1) / 2);
		short j = (short) ((i + 2) + this.rng.nextInt(numCities - (i + 2)));
		TwoEdgeSwap swap 
			= new TwoEdgeSwap(new ImmutablePair<Short, Short>(i, j),
							  currentTour,
							  this.adjacencyMatrix);
		return swap.getSwappedTour();
	}
}