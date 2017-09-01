import java.util.*;

public class MaxFlow {

	public static int maxFlow(int cap[][], int src, int dest) {
		int[][] resiCap = new int[cap.length][cap[0].length];
		for(int i = 0; i < cap.length; i++) for(int j = 0; j < cap[0].length; j++)
			resiCap[i][j] = cap[i][j];
		
		Map<Integer, Integer> parent = new HashMap<>();
		List<List<Integer>> augPath = new ArrayList<>();
		
		int maxFlow = 0;
		
		while(bfs(resiCap, parent, src, dest)) {
			List<Integer> augmentedPath = new ArrayList<>();
			int flow = Integer.MAX_VALUE;
			
			int v = dest;
			while(v != src) {
				augmentedPath.add(v);
				int u = parent.get(v);
				flow = Math.min(flow, resiCap[u][v]);
				v = u;
			}
			augmentedPath.add(src);
			Collections.reverse(augmentedPath);
			augPath.add(augmentedPath);
			
			maxFlow += flow;
			
			v = dest;
			while(v != src) {
				int u = parent.get(v);
				resiCap[u][v] -= flow;
				resiCap[v][u] += flow;
				v = u;
			}
		}
		
		return maxFlow;
	}
	
	public static boolean bfs(int[][] resiCap, Map<Integer, Integer> parent, int src, int dest) {
		Set<Integer> visited = new HashSet<>();
		Queue<Integer> queue = new LinkedList<>();
		queue.add(src);
		visited.add(src);
		while(!queue.isEmpty()) {
			int u = queue.poll();
			for(int v = 0; v < resiCap.length; v++) {
				if(!visited.contains(v) && resiCap[u][v] > 0) {
					parent.put(v,  u);
					visited.add(v);
					queue.add(v);
					if(v == dest) return true;
				}
			}
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
