import java.util.*;

public class ArticulationPoints {
	
	//need some global variables
	static boolean[] visited;
	static int[] parent, lowTime, visitedTime;
	static int time;
	
	//graph - adjacent list
	//return a set of articulation point
	public static Set<Integer> findArticulationPoints(List<List<Integer>> graph) {
		time = 0;
		visited = new boolean[graph.size()];
		parent = new int[graph.size()];
		lowTime = new int[graph.size()];
		visitedTime = new int[graph.size()];
		
		Arrays.fill(parent, -1);
		Set<Integer> artiPoint = new HashSet<>();
		dfs(0, graph, artiPoint); // assume the graph initially is all connected
		return artiPoint;
	}
	
	public static void dfs(int v, List<List<Integer>> graph, Set<Integer> artiPoint) {
		visited[v] = true;
		visitedTime[v] = time;
		lowTime[v] = time;
		time++;
		int childCount = 0;
		boolean isArtiPoint = false;
		for(int adj : graph.get(v)) {
			if(adj == parent[v]) continue;
			if(!visited[adj]) {
				parent[adj] = v;
				childCount++;
				dfs(adj, graph, artiPoint);
				
				if(visitedTime[v] <= lowTime[adj]) isArtiPoint = true;
				else lowTime[v] = Math.min(lowTime[v], lowTime[adj]);
			} else {
				lowTime[v] = Math.min(lowTime[v], lowTime[adj]);
			}
		}
		if((parent[v] == -1 && childCount >= 2) || (parent[v] != -1 && isArtiPoint))
			artiPoint.add(v);
	}

	public static void main(String[] args) {
		int[][] adjList = {{1, 2, 3}, /**/
						   {0, 2},    /*									*/
						   {0, 1},    /*	  1 				6			*/
						   {0, 4},    /*     / \			   / \			*/
						   {3, 5, 6}, /*    /   \		      /	  \			*/
						   {4, 6, 7}, /*   2 --- 0 --- 3 --- 4 --- 5 --- 7 	*/
						   {4, 5},    /* 									*/
						   {5}};      /**/
		List<List<Integer>> adj = new ArrayList<>();
		for(int i = 0; i < adjList.length; i++) adj.add(new ArrayList<>());
		for(int i = 0; i < adjList.length; i++) for(int j : adjList[i])
			adj.get(i).add(j);
		Set<Integer> articulationPoints = findArticulationPoints(adj);
		articulationPoints.forEach(p -> System.out.print(p + " "));
		//output is {0, 3, 4, 5}
	}

}
