
import java.util.*;

public class GeoRevised {
	
	static class Point implements Comparable<Point> {
		long x, y;
		Point(long xx, long yy) { x=xx; y=yy; }
		Point minus(Point b) { return new Point(b.x - x, b.y - y); }
		long cross(Point b)  { return x * b.y - y * b.x; }
		long dot(Point b)    { return x * b.x + y * b.y; }
		public int compareTo(Point b) { return x != b.x ? Long.compare(x, b.x) : Long.compare(y, b.y); }
		public String toString() { return "(" + x + ", " + y + ")"; }
	}

	static long ccw(Point a, Point b, Point c) {
		return b.minus(a).cross(c.minus(a));
	}
	
	static long magSqr(Point vec) {
		return vec.x * vec.x + vec.y + vec.y;
	}
	
	static double angle(Point a, Point o, Point b) {
		Point oa = a.minus(o), ob = b.minus(b);
		return Math.acos(oa.dot(ob) / Math.sqrt(magSqr(oa) * magSqr(ob)));
	}
	
	
	//return the convex hull given a set of points. 
	//note that the returned convex hull's first and last point are the same.
	static List<Point> convexHull1(List<Point> p) {
		int i, t, k = 0, n = p.size();
		Point[] H = new Point[n * 2];
		Collections.sort(p);
		for(i = 0; i < n; i++) {
			while(k > 1 && ccw(H[k - 2], H[k - 1], p.get(i)) <= 0) k--;
			H[k++] = p.get(i);
		}
		for(i = n - 2, t = k; i >= 0; i--) {
			while(k > t && ccw(H[k - 2], H[k - 1], p.get(i)) <= 0) k--;
			H[k++] = p.get(i);
		}
		return Arrays.asList(H).subList(0, k);
	}
	
	
	
	public static void main(String[] args) {

	}


}
