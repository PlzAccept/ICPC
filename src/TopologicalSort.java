import java.util.*;

public class TopologicalSort {
	static List<List<Integer>> adj; // adjacency list
	static Stack<Integer> topo; // topological sort stack
	static boolean[] vis; // store whether a node has been visited or not
	
	//topological sort
	static void dfs(int i) {
		vis[i] = true;
		for(int j : adj.get(i)) // for every adjacent node of i
			if(!vis[j]) dfs(j); // if that node hasn't been explored, explore that node
		topo.push(i); // put this node into stack after exploring all its children
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt(), k = in.nextInt();
		
		adj = new ArrayList<>();
		for(int i = 0; i < n; i++)
			adj.add(new ArrayList<>());
		
		//reads the input
		for(int i = 0; i < k; i++) 
			adj.get(in.nextInt()).add(in.nextInt());
		
		vis = new boolean[n];
		topo = new Stack<>();
		for(int i = 0; i < n; i++)
			if(!vis[i]) dfs(i); // only explore i when i hasn't been visited
		
		while(!topo.isEmpty()) System.out.print((char)('m' + topo.pop()) + " ");
		System.out.println();
	}
	
	// input format:
	// first line contains two number n and k, n is the number of vertex, k is the number of edges.
	// next k lines contains two integer v1 and v2, indicate there is an edge from v1 to v2. (directed)
	// v1, v2 < n
	// the graph is assure to be acyclic.
	
	// output the vertices in topological order
	/*
	sample input:
	14 21
0 4
0 5
0 11
1 2
1 4
1 8
2 5
2 6
2 9
3 2
3 6
3 13
4 7
5 8
5 12
6 5
8 7
9 10
9 11
12 9
10 13
	
	sample output:
	p n o s m r y v x w z u q t 
	 */

}
