
public class AllPairShortestPath {
	
	public static void main(String[] args) {

	}
	
	public static int[][] APSP(int[][] dist) {
		int n = dist.length;
		for(int k = 0; k < n; k++) for(int i = 0; i < n; i++) for(int j = 0; j < n; j++)
			if(dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE)
				dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
		return dist;
	}

}
