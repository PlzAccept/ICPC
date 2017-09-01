import java.math.*;
import java.util.*;

public class Mathematics {

	public static void main(String[] args) {
		//take a string, read as any base
		BigInteger a = new BigInteger("fff", 16); //read "fff" as hex.
		String binary = a.toString(2); //convert this number to binary.
		
		//modulo power
		BigInteger x = new BigInteger("2");
		BigInteger y = new BigInteger("1000000000");
		BigInteger n = new BigInteger("1000000007");
		//x^y mod n. Calculated in O(logn) time, where n is the exponent
		BigInteger ans = x.modPow(y, n);
		
		sieve(1000000000);
		System.out.println(primes.get(2));
	}
	
	static List<Integer> primes = new ArrayList<>();
	static boolean[] bs;

	//generate all prime number that is less than or equal to upper
	//pretty fast if upper <= 10^7
	//call this method only once in main method, use 10^7 is enough for most of the cases
	//a lot of following methods need prime list
	public static void sieve(int upper) {
		int size = upper + 1;
		boolean[] bs = new boolean[size];
		Arrays.fill(bs, true);
		bs[0] = bs[1] = false;
		for(long i = 2; i < size; i++) if(bs[(int)i]) {
			for(long j = i * i; j < size; j+= i) bs[(int)j] = false;
			primes.add((int)i);
		}
	}
	
	//return true if the number N is a prime number
	//***IMPORTANT*** only works if N <= (the largest prime in the prime list)^2
	public static boolean isPrime(long N) {
		if(N < bs.length) return bs[(int)N];
		for(Integer p : primes)
			if(N % p == 0) return false;
		return true;
	}
	
	//return a list of prime factors of N
	//e.g. N = 1000 => {2, 2, 2, 5, 5, 5}
	//use set instead of list if you don't want duplications
	public static List<Integer> primeFactor(long N) {
		List<Integer> factors = new ArrayList<>();
		int i = 0;
		long pf = primes.get(i);
		while(N != 1 && (pf * pf) <= N) {
			while(N % pf == 0) {
				N /= pf;
				factors.add((int)pf);
			}
			pf = primes.get(++i);
		}
		//special case: N is a prime
		if(N != 1) factors.add((int)N);
		return factors;
	}
	
	//return the number of divisor that N has
	public static long numDiv(long N) {
		int i = 0;
		long pf = primes.get(i), ans = 1;
		while(N != 1 && (pf * pf) <= N) {
			long pwr = 0;
			while(N % pf == 0) {
				N /= pf;
				pwr++;
			}
			ans *= (pwr + 1);
			pf = primes.get(++i);
		}
		if(N != 1) ans *= 2;
		return ans;
	}
	
	//return the number of positive integer <N that are relatively prime to N
	public static long EulerPhi(long N) {
		int i = 0;
		long pf = primes.get(i), ans = N;
		while(N != 1 && (pf * pf) <= N) {
			if(N % pf == 0) ans -= ans / pf;
			while(N % pf == 0) N /= pf;
			pf = primes.get(++i);
		}
		if(N != 1) ans -= ans / N;
		return ans;
	}
	
	static int x, y, d;
	public static void extendedEuclid(int a, int b) {
		if(b == 0) {
			x = 1;
			y = 0;
			d = a;
			return;
		}
		extendedEuclid(b, a % b);
		int x1 = y;
		int y1 = x - (a / b) * y;
		x = x1;
		y = y1;
	}
	
	public static long fib(long n, long b) {
		long[][] f = {{1, 1}, {1, 0}};
		f = pow(f, n, b);
		return f[0][1];
	}
	
	public static long[][] mul(long[][] A, long[][] B, long mod) {
		long[][] C = new long[2][2];
		for(int i = 0; i < 2; i++) for(int j = 0; j < 2; j++) for(int k = 0; k < 2; k++)
            C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % mod;
        return C;
	}
	
	public static long[][] pow(long[][] A, long n, long mod) {
		if(n == 1) return A;
		if((n & 1) == 1) return mul(A, pow(A, n - 1, mod), mod);
		long[][] B = pow(A, n / 2, mod);
		return mul(B, B, mod);
	}
	
}
