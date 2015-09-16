/**
 * 
 */
package movegenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import tour.NodeTour;
import tour.Tour;
import tspdef.AdjacencyMatrix;

/**
 * @author sg
 * Holds a pair of indices whose corresponding edges can be swapped in a 
 * given tour to generate a new tour. This is an implementation of the 2-opt
 * candidate generator algorithm.
 */
public class TwoEdgeSwap implements Comparable<TwoEdgeSwap> {
    private Pair<Short, Short> swapIndices;
    private int swappedTourCost;
    private Tour swappedTour;

    /**
     * @param indices The pair of indices to swap.
     * @param cost The cost that the modified tour would have.
     */
    public TwoEdgeSwap(Pair<Short, Short> indices,
    				   Tour originalTour,
    				   AdjacencyMatrix matrix) {
        this.swapIndices = indices;
        this.computeSwappedTour(originalTour, matrix);
    }
    
    /**
     * Creates a new permutation of the given tour and calculates its cost
     * using the adjacency matrix provided.
     * 
     * @param originalTour The tour to permute.
     * @param adjacencyMatrix The adjacency matrix
     */
    private void computeSwappedTour(Tour originalTour,
    								AdjacencyMatrix adjacencyMatrix) {
    	List<Short> oldPath = originalTour.getNodeList();    	
    	int numCities = oldPath.size();
    	int oldCost = originalTour.getCost();
    	
    	short i = this.swapIndices.getLeft();
    	short j = this.swapIndices.getRight();
    	
    	/*  Finding the indices of the edges needed to be swapped.  */
        short iL = oldPath.get(i);
        short iR = oldPath.get(i + 1);
        short jL = oldPath.get(j);
        short jR = oldPath.get((j + 1) % numCities);
        
        /* Computing the cost of the new tour. */
        int oldIEdge = adjacencyMatrix.getCost(iL,  iR);
        int oldJEdge = adjacencyMatrix.getCost(jL,  jR);
        int newIEdge = adjacencyMatrix.getCost(iL,  jL);
        int newJEdge = adjacencyMatrix.getCost(jR,  iR);
        this.swappedTourCost = oldCost - (oldIEdge + oldJEdge)
                                	   + (newIEdge + newJEdge);

        // Now compute the new tour.
        // 0 to i remains the same. So does j+1 to the end.
        // Have to reverse in the middle.
        List<Short> newPath = new ArrayList<Short>(oldPath);
    	newPath.set(i+1, jL);
    	newPath.set(j, iR);
        // reverse the part between i + 1 and j
    	short l_start_offset = (short) (i+2);
    	short r_end_offset = (short) (j-1);
    	for(short k = l_start_offset; k <= r_end_offset; k++) {
            newPath.set(k,oldPath.get(r_end_offset - (k - l_start_offset)));
        }
        
    	this.swappedTour = new NodeTour(newPath, this.swappedTourCost);    	
    }
    
    /**
     * @return the swappedTour
     */
    public Tour getSwappedTour() {
    	return this.swappedTour;
    }

    /**
     * @return the swapIndices
     */
    public Pair<Short, Short> getSwapIndices() {
        return this.swapIndices;
    }

    /**
     * @param swapIndices the swapIndices to set
     */
    public void setSwapIndices(Pair<Short, Short> swapIndices) {
        this.swapIndices = swapIndices;
    }

    /**
     * @return the swappedTourCost
     */
    public int getSwappedTourCost() {
        return this.swappedTourCost;
    }

    /**
     * @param swappedTourCost the swappedTourCost to set
     */
    public void setSwappedTourCost(int swappedTourCost) {
        this.swappedTourCost = swappedTourCost;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * 
     * Compares this prospective modified tour to another modified tour.
     */
    @Override
    public int compareTo(TwoEdgeSwap o) {
        return Integer.compare(this.swappedTourCost, o.getSwappedTourCost());
    }
}