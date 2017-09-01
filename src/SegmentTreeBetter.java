
public class SegmentTreeBetter {
	
	static class SegTree {
		int[] st;
		int n;
		
		public SegTree(int[] array) {
			n = array.length;
			st = new int[2 * n];
			for(int i = 0; i < n; i++)
				st[n + i] = array[i];
			for(int i = n - 1; i > 0; i--)
				st[i] = Math.min(st[i<<1], st[i<<1|1]);
		}
		
		public int rmq(int l, int r) {
			int min = Integer.MAX_VALUE;
			r++;
			for(l += n, r += n; l < r; l >>= 1, r >>= 1) {
				if((l & 1) == 1) min = Math.min(min, st[l++]);
				if((r & 1) == 1) min = Math.min(min, st[--r]);
			}
			return min;
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SegTree st = new SegTree(new int[] {6, -3, 4, 6, 2, 1, 10});
		System.out.println(st.rmq(5, 6));
	}

}
