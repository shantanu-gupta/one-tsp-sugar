/**
 * 
 */
package movegenerator;

import java.util.List;

import tour.Tour;

/**
 * @author sg
 *
 */
public interface MoveGen {
    /**
     * Return a set of candidates for a search algorithm to choose from.
     * Candidates are generated from a given tour by performing some
     * permutation on it, such as a 2-opt swap.
     * 
     * @param currentTour The current tour.
     * @return List of candidate tours.
     */
    public List<Tour> generateCandidateList(Tour currentTour);
    
    /**
     * Samples one candidate from the list of candidates available.
     * Useful for algorithms such as simulated annealing.
     * @param currentTour The given tour.
     * @return A candidate tour.
     */
    public Tour generateCandidate(Tour currentTour);
}