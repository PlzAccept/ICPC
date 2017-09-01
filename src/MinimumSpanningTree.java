import java.util.*;

public class MinimumSpanningTree {

	//MST Kruskal's
	
	//you can find the Union-Find code on Page 8
	static class UF {

		private Vector<Integer> p, rank, setSize;
		private int numSets;

		public UF(int N) {
			p = new Vector<Integer>(N);
			rank = new Vector<Integer>(N);
			setSize = new Vector<Integer>(N);
			numSets = N;
			for (int i = 0; i < N; i++) {
				p.add(i);
				rank.add(0);
				setSize.add(1);
			}
		}

		public int findSet(int i) {
			if (p.get(i) == i)
				return i;
			else {
				int ret = findSet(p.get(i));
				p.set(i, ret);
				return ret;
			}
		}

		public Boolean isSameSet(int i, int j) {
			return findSet(i) == findSet(j);
		}

		public void unionSet(int i, int j) {
			if (!isSameSet(i, j)) {
				numSets--;
				int x = findSet(i), y = findSet(j);
				// rank is used to keep the tree short
				if (rank.get(x) > rank.get(y)) {
					p.set(y, x);
					setSize.set(x, setSize.get(x) + setSize.get(y));
				} else {
					p.set(x, y);
					setSize.set(y, setSize.get(y) + setSize.get(x));
					if (rank.get(x) == rank.get(y))
						rank.set(y, rank.get(y) + 1);
				}
			}
		}

		public int numDisjointSets() {
			return numSets;
		}

		public int sizeOfSet(int i) {
			return setSize.get(findSet(i));
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int v1, v2, weight;
		Edge(int vv1, int vv2, int w) {
			v1 = vv1;
			v2 = vv2;
			weight = w;
		}
		public int compareTo(Edge e) {return Integer.compare(weight, e.weight);}
	}
	
	static int mstWeight;
	//edges is the graph, n is the number of vertex
	//return the MST
	public static List<Edge> getMST(List<Edge> edges, int n) {
		Collections.sort(edges);
		UF uf = new UF(n);
		
		mstWeight = 0;
		List<Edge> result = new ArrayList<>();
		for(Edge e : edges) {
			if(!uf.isSameSet(e.v1, e.v2)) {
				result.add(e);
				uf.unionSet(e.v1, e.v2);
				mstWeight += e.weight;
			}
		}
		
		return result;
	}
	
	//example of MST
	public static void main(String[] args) {
		List<Edge> e = new ArrayList<>();
		e.add(new Edge(0, 1, 3)); //      1     6
		e.add(new Edge(0, 3, 1)); //  0 ---- 3 ---- 4
		e.add(new Edge(1, 3, 3)); //  |    / |    / |
		e.add(new Edge(1, 2, 1)); // 3|   /  |1  /  |2
		e.add(new Edge(3, 2, 1)); //  |  /3  |  /5  |
		e.add(new Edge(3, 4, 6)); //  | /    | /    |
		e.add(new Edge(2, 4, 5)); //  1 ---- 2 ---- 5
		e.add(new Edge(2, 5, 4)); //     1       4
		e.add(new Edge(4, 5, 2));
		List<Edge> result = getMST(e, 6);
		//output: mstWeight = 9, result = {0-3, 1-2, 3-2, 4-5, 2-5}
	}

}
