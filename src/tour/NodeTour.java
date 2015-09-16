/**
 * 
 */
package tour;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import tspdef.AdjacencyMatrix;

/**
 * @author sg
 *
 * Treats a tour as a sequence of vertices.
 */
public class NodeTour implements Tour {    
    protected List<Short> path;	//  List of vertex numbers.
    protected int cost;    		//  Total cost
    
    /**
     * Creates an empty NodeTour.
     */
    public NodeTour() {
        this.path = null;
        this.cost = 0;
    }
    
    /**
     * @param nodeList List of vertices visited.
     * @param cost The cost of the tour.
     */
    public NodeTour(List<Short> nodeList, int cost) {
        this.path = new ArrayList<Short>(nodeList);
        this.cost = cost;
    }
    
    /**
     * Converts an EdgeTour to a NodeTour.
     * @param tour The tour to convert.
     */
    public NodeTour(EdgeTour tour) {
        this.path = new ArrayList<Short>();
        this.cost = 0;
        for(Edge edge : tour.getPath()) {
            this.path.add(edge.First());
            this.cost += edge.getCost(); 
        }
    }
    
    /**
     * Copy constructor.
     * @param tour The tour to copy.
     */
    public NodeTour(NodeTour tour) {
        this.path = new ArrayList<Short>(tour.getNodeList());
        this.cost = tour.getCost();        
    }
    
    /**
     * Creates a tour out of a sequence of nodes and an adjacency matrix
     * containing all inter-node distances.
     * @param pth The sequence of nodes.
     * @param adjMatrix The adjacency matrix.
     */
    public NodeTour(List<Short> pth, AdjacencyMatrix matrix) {
        //  Explicit constructor; forms a NodeTour from scratch.        
        this.path = new ArrayList<Short>();
        this.cost = 0;
        
        int numCities = pth.size();
        for(int i = 0; i < pth.size(); i++) {
            short L = pth.get(i);
            short R = pth.get((i+1) % numCities);
            int length = matrix.getCost(L, R);
            this.path.add(L);
            this.cost += length;
        }
    }
    
    /* (non-Javadoc)
     * @see tour.Tour#Validate(int)
     * 
	 * Assume a tour contains numbers from 0 to (numCities - 1).
     * If all numbers are distinct, the tour is valid.
     */
    @Override
    public boolean Validate(int numCities) {        
        TreeSet<Short> cities = new TreeSet<Short>();
        cities.addAll(this.path);
        return (cities.size() == numCities) 
        	&& (cities.first() == 0)
        	&& (cities.last() == numCities - 1);
    }

    /* (non-Javadoc)
     * @see tour.Tour#getNodeList()
     * 
     * Returns the list of nodes visited.
     */
    @Override
    public List<Short> getNodeList() {
        return this.path;
    }

    /**
     * @return the cost
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * @return the lengths
     */
    public List<Integer> getLengths(AdjacencyMatrix matrix) {
        List<Integer> lengths = new ArrayList<Integer>();
        for(int i = 0; i < matrix.getNumCities(); i++) {
            short edgeL = this.path.get(i);
            short edgeR = this.path.get((i+1) % matrix.getNumCities());
            int length = matrix.getCost(edgeL, edgeR);
            lengths.add(length);
        }
        
        return lengths;
    }

    /* (non-Javadoc)
     * @see tour.Tour#compareTo(tour.Tour)
     * 
     * Compares this tour's cost with another.
     */
    @Override
    public int compareTo(Tour o) {
        return Integer.compare(getCost(), o.getCost());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     * 
     * Outputs a string containing the sequence of nodes visited.
     */
    @Override    
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(path.get(0));
        for(short node : path.subList(1, path.size())) {
            b.append(" ").append(node);
        }
        
        return b.toString();
    }
}