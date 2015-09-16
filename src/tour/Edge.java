/**
 * 
 */
package tour;

/**
 * @author sg
 * 
 * Container for an edge. The 2 vertices of the edge are represented by
 * numbers, which can be seen as the indices of the vertices in a list.
 */
public class Edge implements Comparable<Edge> {
	private short node1;
	private short node2;
	private int cost;

	/**
	 * Creates an Edge.
	 * @param p1 The first vertex.
	 * @param p2 The second vertex.
	 * @param c The edge cost.
	 */
	public Edge(short p1, short p2, int c) {
		this.node1 = p1;
		this.node2 = p2;
		this.cost = c;
	}
	
	/**
	 * Copy constructor.
	 * @param e The other Edge.
	 */
	public Edge(Edge e) {
		this.node1 = e.First();
		this.node2 = e.Second();
		this.cost = e.getCost();
	}

	/**
	 * @return The first vertex.
	 */
	public short First() {
		return this.node1;
	}

	/**
	 * @return The second vertex.
	 */
	public short Second() {
		return this.node2;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return this.cost;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * Compare the cost of this edge to that of another.
	 */
	@Override
	public int compareTo(Edge arg0) {
		return Integer.compare(this.cost, arg0.getCost());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * A tuple representation of this edge.
	 */
	@Override
	public String toString() {
		return ("(" + this.node1 + ", " + this.node2 + ", " + this.cost + ")");
	}
}