
public class GaussianElimination {
	
	static double[] gaussianElimination(double[][] mat) {
		int len = mat.length;
		double[] x = new double[len];
		for(int j = 0; j < len - 1; j++) {
			int l = j;
			for(int i = j + 1; i < len; i++) {
				if(Math.abs(mat[i][j]) > Math.abs(mat[l][j]))
					l = i;
			}
			
			for(int k = j; k <= len; k++) {
				double t = mat[j][k];
				mat[j][k] = mat[l][k];
				mat[l][k] = t;
			}
			
			for(int i = j + 1; i < len; i++) {
				for(int k = len; k >= j; k--) {
					mat[i][k] -= mat[j][k] * mat[i][j] / mat[j][j];
				}
			}
		}
		
		for(int j = len - 1; j >= 0; j--) {
			double t = 0.0;
			for(int k = j + 1; k < len; k++)
				t += mat[j][k] * x[k];
			x[j] = (mat[j][len] - t) / mat[j][j];
		}
		
		return x;
	}

	public static void main(String[] args) {
		/*
		 * e.g.
		 * 1x + 1y + 2z = 9
		 * 2x + 4y - 3z = 1
		 * 3x + 6y - 5z = 0
		 */
		double[] ans = gaussianElimination(new double[][] {{1, 1,  2, 9},
														   {2, 4, -3, 1},
														   {3, 6, -5, 0}});
		for(int i = 0; i < ans.length; i++) 
			System.out.printf("%.4f ", ans[i]);
		
		System.out.println();
		
	}

}
