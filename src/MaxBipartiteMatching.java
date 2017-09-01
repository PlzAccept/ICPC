import java.util.*;

public class MaxBipartiteMatching {

	static Vector<Vector<Integer>> adjList = new Vector<>();
	static boolean[] visited;
	static int[] match;
	
	private static int Aug(int left) {
		if(visited[left]) return 0;
		visited[left] = true;
		
		Iterator it = adjList.get(left).iterator();
		while(it.hasNext()) {
			Integer right = (Integer)it.next();
			if(match[right] == -1 || Aug(match[right]) == 1) {
				match[right] = left;
				return 1;
			}
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		int V = 5, V_l = 3; //	V: total vertex, V_l: vertex on left side
		for(int i = 0; i < V; i++) adjList.add(new Vector<>());
		
		adjList.get(1).add(3);
		adjList.get(1).add(4);
		adjList.get(2).add(3);
		
		int MCBM = 0;
		match = new int[V];
		Arrays.fill(match, -1);
		for(int i = 0; i < V_l; i++) {
			visited = new boolean[V_l];
			MCBM += Aug(i);
		}
		System.out.println(MCBM);
	}

}
