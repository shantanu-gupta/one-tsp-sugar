/**
 * 
 */
package tour;

import java.util.List;

import tspdef.AdjacencyMatrix;

/**
 * @author sg
 * Common interface for different representations of a tour.
 * 
 * Could be a sequence of vertices, or of edges.
 */
/**
 * @author sg
 *
 */
/**
 * @author sg
 *
 */
/**
 * @author sg
 *
 */
public interface Tour extends Comparable<Tour> {
	/**
	 * @param numCities The total number of cities.
	 * @return Whether this tour is valid or not.
	 * 
	 * The tour should contain exactly numCities vertices, with correct
	 * indices as well.
	 */
	public boolean Validate(int numCities);
	
	/**
	 * @return The total cost of this tour.
	 */
	public int getCost();
	
	/**
	 * @return The order in which the vertices are visited.
	 */
	public List<Short> getNodeList();

	/**
	 * @return The lengths of the edges traversed in the tour.
	 */
	public List<Integer> getLengths(AdjacencyMatrix matrix);
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * Compare the total cost of this tour to that of another.
	 * Basically just one line:
	 * 
	 * return Integer.compare(getCost(), o.getCost());
	 * 
	 * TODO: How to write this in Java?
	 */
	@Override
	public int compareTo(Tour tour);

	/**
	 * @return A string representation of this tour.
	 */
	@Override
	public String toString();
}