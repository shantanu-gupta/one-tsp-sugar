package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import tour.Edge;
import tspdef.AdjacencyMatrix;
import tspdef.Euc2DPosition;

public class Euc2DTSPParser {
	private AdjacencyMatrix adjacencyMatrix;
	private Euc2DPosition[] nodes;
	
	public Euc2DTSPParser(String filename) {
		try {
			String all = new String(Files.readAllBytes(Paths.get(filename)));
			String startMarker = "NODE_COORD_SECTION";
			String endMarker = "EOF";
			int dataStartIndex = all.lastIndexOf(startMarker)
								 + startMarker.length();
			int dataEndIndex = all.lastIndexOf(endMarker);
			String nodeCoordSection
					= all.substring(dataStartIndex, dataEndIndex).trim();
			
			String[] lines = nodeCoordSection.split("\n");
			nodes = new Euc2DPosition[lines.length];
			String line = null;
			for (int i = 0; i < lines.length; ++i) {
				line = lines[i];
				String[] parts = line.split(" ");
				nodes[i] = new Euc2DPosition(Double.parseDouble(parts[1]),
											 Double.parseDouble(parts[2]));
			}
			
			List<Edge> edges = new LinkedList<Edge>();
			for (short i = 0; i < nodes.length; ++i) {
				/* The input only has edges to other vertices, which makes
				 * sense.
				 * But I am using an adjacency matrix representation.
				 * To keep it simpler elsewhere, I'm adding dummy self-loops
				 * of cost 0.
				 */
				for (short j = 0; j < nodes.length; ++j) {
					int cost = nint(nodes[i].distanceFrom(nodes[j]));
					edges.add(new Edge(i, j, cost));
				}				
			}
			
			Edge[] edgeArray = new Edge[edges.size()];
			edges.toArray(edgeArray);			
			this.adjacencyMatrix 
				= new AdjacencyMatrix((short) nodes.length,
									  edgeArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Nearest integer function
	 * @param f float
	 * @return nearest int to f
	 */
	private int nint(double f) {
		return (int) (f + 0.5);
	}
	
	/**
	 * @return the adjacencyMatrix
	 */
	public AdjacencyMatrix getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	/**
	 * @return the nodes
	 */
	public Euc2DPosition[] getNodes() {
		return nodes;
	}
}
