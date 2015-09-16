/**
 * 
 */
package tspdef;

import tour.Edge;

/**
 * @author sg
 * Adjacency matrix to hold inter-node distances.
 */
public class AdjacencyMatrix {
	private short numCities;
	private Edge[] edges;

	/**
	 * @param edges 1D array of length NxN, containing edges.
	 */
	public AdjacencyMatrix(short numCities, Edge[] edges) {
		assert (numCities * numCities == edges.length) : "Need all edges";
		
		this.numCities = numCities;
		this.edges = edges;
	}
	
	/**
	 * Return the edge between 2 specified nodes.
	 * @param i The first vertex.
	 * @param j The second vertex.
	 * @return The edge between i and j.
	 */
	public Edge getEdge(int i, int j) {
		return this.edges[i * this.numCities + j];
	}
	
	/**
	 * @param i The first node index.
	 * @param j The second node index.
	 * @return The cost of the edge between i and j.
	 */
	public int getCost(int i, int j) {
		return this.edges[i * this.numCities + j].getCost();
	}
	
	/**
	 * @return the numCities
	 */
	public short getNumCities() {
		return numCities;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();
		if (this.numCities < 20) {
			for (int i = 0; i < this.numCities; ++i) {
				for (int j = 0; j < this.numCities; ++j) {
					sb.append(String.valueOf(this.getCost(i, j)));
					sb.append(" ");
				}
				
				sb.append('\n');
			}
		} else {
			for (int i = 0; i < this.edges.length; ++i) {
				sb.append(edges[i].toString());
				sb.append('\n');
			}
		}
		
		return sb.toString();
	}
}