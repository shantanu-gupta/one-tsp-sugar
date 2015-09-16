/**
 * 
 */
package tour;

import java.util.ArrayList;
import java.util.List;

import tspdef.AdjacencyMatrix;

/**
 * @author sg
 * An EdgeTour class to contain a path and its cost.
 * Insanely memory-inefficient : DO NOT USE!!!
 */
public class EdgeTour implements Tour {
    private List<Edge> path;
	private int cost;
        
    /**
     * Creates an EdgeTour out of a list of Edges.
     * @param tour
     */
    public EdgeTour(List<Edge> tour) {
        this.path = new ArrayList<Edge>(tour);
        
        this.cost = 0;
        for (Edge e : tour) {
        	this.cost += e.getCost();
        }
    }

    /**
     * Create an EdgeTour out of a NodeTour (list of vertices visited), and
     * an adjacency matrix that holds inter-node distances.
     * @param tour The NodeTour to copy.
     * @param matrix The adjacency matrix.
     */
    public EdgeTour(NodeTour tour, AdjacencyMatrix matrix) {
        this.path = new ArrayList<Edge>();

        List<Short> nodeList = tour.getNodeList();
        int numCities = nodeList.size();
        
        int i;
        for (i = 0; i < numCities; i++) {
            short edgeL = nodeList.get(i);
            short edgeR = nodeList.get((i + 1) % numCities);
            Edge e = new Edge(matrix.getEdge(edgeL, edgeR)); // copy
            this.path.add(e);
            this.cost += e.getCost();
        }        
    }

	/**
     * @return the path.
     */
    public List<Edge> getPath() {
		return path;
	}

    /**
     * @return the cost
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    /* (non-Javadoc)
     * @see tour.Tour#getNodeList()
     * 
     * Sequence of vertices visited.
     */
    @Override
    public List<Short> getNodeList() {
        List<Short> nodeList = new ArrayList<Short>();
        for(Edge e : path) {
            nodeList.add(e.First());
        }
        
        return nodeList;
    }
    
	/* (non-Javadoc)
	 * @see tour.Tour#getLengths(tour.AdjacencyMatrix)
	 * 
	 * Return the lengths of each edge in the path.
	 */
	@Override
	public List<Integer> getLengths(AdjacencyMatrix matrix) {
		List<Integer> lengths = new ArrayList<Integer>();
		for (Edge e : this.path) {
			lengths.add(e.getCost());
		}
		
		return lengths;
	}    

	/* (non-Javadoc)
     * @see tour.Tour#Validate(int)
     * 
     * Check the validity of this tour.
     */
    @Override
    public boolean Validate(int numCities) {        
        //easy checks early
    	boolean isValid = true;
        if(this.path.size() != numCities) isValid = false;        
        if(this.path.get(numCities - 1).Second() != this.path.get(0).First()) {
            isValid = false;
        }
        
        // Go through the whole list now.
        for(int i = 0; i < numCities - 1; i++) {
        	short outNode = this.path.get(i).Second();
        	short inNode  = this.path.get(i+1).First();
        	if ( (outNode != inNode)
        		|| (outNode < 0 || outNode >= numCities)
        		|| (inNode < 0 || inNode >= numCities) ) {
        		isValid = false;
        	}
        }
        
        return isValid;
    }
   
    /* (non-Javadoc)
     * @see tour.Tour#compareTo(tour.Tour)
     * 
     * Compare this tour to another.
     */
    @Override
    public int compareTo(Tour arg0) {
        return Integer.compare(this.cost, arg0.getCost());
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     * 
     * Outputs a string containing the sequence of nodes visited.
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(path.get(0).First());
        for(Edge e : path.subList(1, path.size())) {
            b.append(" ").append(e.First());
        }
        
        return b.toString();
    }
}