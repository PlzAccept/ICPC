import java.util.*;

public class MaxFlowDFS {
	//C - store the capacity. C[i][j] - capacity i -> j
    //F - store the result
    static int[][] C, F; //C has to be pre-computed before calling flow method

    //adjacent list, this is used when there are large number of vertex,
    //but small number of edges connected to each vertex
    static List<List<Integer>> adj = new ArrayList<>();
    static boolean[] visited;

    //s - source
    //t - destination
    //return the maximum flow
    private static int flow(int s, int t) { 
        // Visited array to perform DFS, initially empty
        visited = new boolean[C.length];

        //initialize adjacent list
        for(int i = 0; i < C.length; i++) adj.add(new ArrayList<>());
        for(int i = 0; i < C.length; i++) for(int j = 0; j < C.length; j++) {
            if(C[i][j] > 0) {
                adj.get(i).add(j);
                adj.get(j).add(i);
            }
        }

        int maxflow = 0, flow = 0;
        // Repeat until there is no path    
        while ((flow = dfs(s, t, Integer.MAX_VALUE)) > 0) {
            visited = new boolean[C.length];
            maxflow += flow;
        }

        return maxflow;
    }

    private static int dfs(int i, int t, int min) {
        // If sink has been reached, terminate
        if (i==t) return min;

        visited[i] = true;
        for (int j : adj.get(i)) {
            if (C[i][j] > 0 && !visited[j]) {
                int v = dfs(j, t, Math.min(min, C[i][j]));
                if (v > 0) {
                    C[i][j] = C[i][j] - v;
                    F[i][j] = F[i][j] + v; 
                    C[j][i] = C[j][i] + v;
                    F[j][i] = F[j][i] - v;
                    return v;
                }
            }
        }   
        // The sink has not been found.
        return 0;
    }
    
    public static void main(String[] args) {
    	C = new int[][]{{0, 1, 1, 0, 0, 0},
    					{0, 0, 0, 1, 1, 0},
    					{0, 0, 0, 1, 0, 0},
    					{0, 0, 0, 0, 0, 1},
    					{0, 0, 0, 0, 0, 1},
    					{0, 0, 0, 0, 0, 0}};
    	F = new int[6][6];
    	System.out.println(flow(0, 5));
    	System.out.println(Arrays.deepToString(F));
    }

}
