import java.util.*;

public class LinearRecurrence {

	static int MOD = 1000000007;
	
	public static void main(String[] args) {
		//f(n) = 3f(n - 2) + 2f(n - 1)
		//f(1) = 1, f(2) = 2
		long[][] T = {{0, 1},
					 {3, 2}};
		long[][] F = {{1}, {2}};
		
		//lets say you want to find f(10)
		T = pow(T, 10L);
		F = mul(T, F);
		System.out.println(F[0][0]);
		System.out.println(f(11));
	}
	
	public static long[][] pow(long[][] b, long e) {
		if(e == 1) return b;
		if(e % 2 == 0) {
			long[][] x = pow(b, e / 2);
			return mul(x, x);
		}
		return mul(b, pow(b, e - 1));
	}
	
	public static long[][] mul(long[][] a, long[][] b) {
		long[][] c = new long[a.length][b[0].length];
		for(int i = 0; i < c.length; i++) {
			for(int j = 0; j < c[i].length; j++) {
				for(int k = 0; k < a[i].length; k++) {
					c[i][j] += a[i][k] * b[k][j];
					c[i][j] %= MOD;
				}
			}
		}
		return c;
	}
	
	public static long f(long n) {
		if(n == 1) return 1;
		if(n == 2) return 2;
		return 3 * f(n - 2) + 2 * f(n - 1);
	}

}
