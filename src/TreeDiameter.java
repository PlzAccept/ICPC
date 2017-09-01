import java.util.*;

public class TreeDiameter {

	//O(n)
	//acyclic graph
	static List<List<Integer>> adj = new ArrayList<>();
	static int mx, mxn;
	
	static void dfs(int n, int p, int l) {
		if(l > mx) {
			mx = l;
			mxn = n;
		}
		for(int v : adj.get(n))
			dfs(v, n, l + 1);
	}
	
	public static void main(String[] args) {
		//some graph setup
		// .........
		
		dfs(0, 0, 0);
		mx = 0;
		dfs(mxn, mxn, 0);
		
		//diameter now stored in mx
	}

}
