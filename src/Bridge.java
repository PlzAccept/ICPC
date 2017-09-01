import java.util.*;

public class Bridge {
	
	//store graph as Nodes with adjacent list
	static class Node {
		int id;
		List<Node> adjacent = new ArrayList<>();
		Node(int dd) { id = dd; }
	}
	
	static List<Node> nodes;
	static int[] pre, low;
	static int bridgeNum; // store number of bridges
	static int cnt;
	
	static void findBridge() {
		low = new int[nodes.size()];
		pre = new int[nodes.size()];
		Arrays.fill(low, -1);
		Arrays.fill(pre, -1);
		for(Node v : nodes)
			if(pre[v.id] == -1)
				dfs(v, v);
	}
	
	static void dfs(Node u, Node v) {
		pre[v.id] = cnt++;
		low[v.id] = pre[v.id];
		for(Node w : v.adjacent) {
			if(pre[w.id] == -1) {
				dfs(v, w);
				low[v.id] = Math.min(low[v.id], low[w.id]);
				if(low[w.id] == pre[w.id]) {
					//bridge found! v-w edge is a bridge
					System.out.println(v.id + "-" + w.id);
					bridgeNum++;
				}
			} else if(w.id != u.id) {
				low[v.id] = Math.min(low[v.id], pre[w.id]);
			}
		}
	}

	public static void main(String[] args) {
		//example
		nodes = new ArrayList<>();
		for(int i = 0; i < 7; i++) nodes.add(new Node(i));
		int[][] graph = {{1, 2},    //
						 {0, 2},    //      0           4
						 {0, 1, 3}, //     / \         / \
						 {2, 4, 5}, //    /   \       /   \
						 {3, 5},    //   1 --- 2 --- 3 --- 5 --- 6
						 {3, 4, 6}, //
						 {5}};      //
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph[i].length; j++) {
				nodes.get(i).adjacent.add(nodes.get(graph[i][j]));
			}
		}
		
		findBridge(); //bridgeNum = 2, bridge: 2-3, 5-6

	}

}
