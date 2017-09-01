import java.util.*;

public class MSTPrim {

	//Prim's
	static class Vertex {
		int id;
		List<Edge> adj = new ArrayList<>();
		Vertex(int a) {id = a;}
	}
	
	static class Edge implements Comparable<Edge> {
		int weight;
		Vertex target;
		Vertex src;
		Edge(Vertex s, Vertex t, int w) {weight = w; target = t; src = s;}
		public int compareTo(Edge e) {return Integer.compare(weight, e.weight);}
	}
	
	//this class is to store the edges in MST
	//if only need the MST weight, then ignore this
	static class IntPair {
		int v1, v2;
		IntPair(int a, int b) {v1 = a; v2 = b;}
	}
	
	static int mstWeight;
	//took a graph and return the MST, or don't return anything if just need the min cost
	public static List<IntPair> mst(List<Vertex> vertices) {
		mstWeight = 0;
		List<IntPair> result = new ArrayList<>(); //store the MST
		boolean[] visited = new boolean[vertices.size()];
		visited[0] = true;
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		for(Edge e : vertices.get(0).adj)
			pq.offer(e);
		while(!pq.isEmpty()) {
			Edge u = pq.poll();
			if(!visited[u.target.id]) {
				mstWeight += u.weight;
				result.add(new IntPair(u.src.id, u.target.id)); //store the MST
				visited[u.target.id] = true;
				for(Edge e : u.target.adj) {
					if(!visited[e.target.id]) pq.offer(e); 
				}
			}
		}
		return result; //return MST
	}
	
	
	public static void main(String[] args) {
		List<Vertex> vertices = new ArrayList<>();
		for(int i = 0; i < 6; i++) vertices.add(new Vertex(i));
		/*
		 * input: 
		 * 
		 * 0 3 1			//      1     6
		 * 0 1 3			//  0 ---- 3 ---- 4
		 * 1 2 1			//  |    / |    / |
		 * 1 3 3			// 3|   /  |1  /  |2
		 * 2 3 1			//  |  /3  |  /5  |
		 * 2 4 5			//  | /    | /    |
		 * 2 5 4			//  1 ---- 2 ---- 5
		 * 3 4 6			//     1       4
		 * 4 5 2
		 * 
		 */
		Scanner in = new Scanner(System.in);
		for(int i = 0; i < 9; i++) {
			int a = in.nextInt(), b = in.nextInt(), w = in.nextInt();
			vertices.get(a).adj.add(new Edge(vertices.get(a), vertices.get(b), w));
			vertices.get(b).adj.add(new Edge(vertices.get(b), vertices.get(a), w));
		}
		
		List<IntPair> result = (mst(vertices));
		// mstWeight = 9, result = {0-3, 3-2, 2-1, 2-5, 5-4}
	}
}
