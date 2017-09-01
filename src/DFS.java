import java.util.*;

public class DFS {
	
	static class Node {
		int id;
		List<Node> adjacent = new ArrayList<>();
		Node(int id) {this.id = id;}
	}

	static boolean[] visited;
	
	public static void main(String[] args) {
		int N = 4; // 4 nodes;
		List<Node> nodes = new ArrayList<>();
		for(int i = 0; i < N; i++) nodes.add(new Node(i));
		
		nodes.get(0).adjacent.add(nodes.get(1));
		nodes.get(1).adjacent.add(nodes.get(0)); // 0-1 connected
		nodes.get(0).adjacent.add(nodes.get(2));
		nodes.get(2).adjacent.add(nodes.get(0)); // 0-2 connected
		nodes.get(2).adjacent.add(nodes.get(1));
		nodes.get(1).adjacent.add(nodes.get(2)); // 2-1 connected
		nodes.get(0).adjacent.add(nodes.get(3));
		nodes.get(3).adjacent.add(nodes.get(0)); // 0-3 connected
		
		visited = new boolean[N];
	}
	
	public static void dfs(Node n) {
		visited[n.id] = true;
		for(Node adj : n.adjacent)
			if(!visited[adj.id])
				dfs(adj);
	}

}
