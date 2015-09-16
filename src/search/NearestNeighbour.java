/**
 * 
 */
package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tour.NodeTour;
import tour.Tour;
import tspdef.AdjacencyMatrix;

/**
 * @author sg
 *
 */
public class NearestNeighbour implements TSPRouteSearch {
	/**
	 * The adjacency matrix for the graph. 
	 */
	private AdjacencyMatrix adjacencyMatrix;
	
	/**
	 * The starting node index.
	 */
	private short initNode;
	
	/**
	 * Creates a nearest-neighbour route searcher.
	 * @param adjacencyMatrix The adjacency matrix.
	 */
	public NearestNeighbour(AdjacencyMatrix adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		Random rng = new Random();
		this.initNode 
			= (short) rng.nextInt(this.adjacencyMatrix.getNumCities());
	}

	/* (non-Javadoc)
	 * @see search.TSPRouteSearch#bestTour()
	 * 
	 * Returns a nearest-neighbour tour.
	 */
	@Override
	public Tour bestTour() {
		/*
		 * Form a tour with the initial node selected in the constructor.
		 * Keep looking for the next nearest neighbour to the latest node
		 * selected.
		 */
		short numCities = this.adjacencyMatrix.getNumCities();
		List<Short> currentPath = new ArrayList<Short>();
		currentPath.add(this.initNode);
         
        short currentNode = this.initNode;
        short nextPoint = currentNode;
        while(currentPath.size() < numCities) {
            //  Get next nearest neighbour.
            int minLen = Integer.MAX_VALUE;
            for(short i = 0; i < numCities; i++) {
                if(!currentPath.contains(i)) {
                	int len = this.adjacencyMatrix.getCost(currentNode, i);
                    if(len < minLen) {                        
                        minLen = len;
                        nextPoint = i;
                    }
                }
            }
            
            currentPath.add(nextPoint);
            currentNode = nextPoint;
        }
        
        return new NodeTour(currentPath, this.adjacencyMatrix);
	}
}