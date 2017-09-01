import java.util.*;

public class Geometry {
	//use integer version of points in most of the situations
	//if the input data is floating number, let's say up to two decimal places
	//just multiply every x and y by 100.
	static class Point_i implements Comparable<Point_i>{
		long x, y;
		Point_i(long xx, long yy) {
			x = xx;
			y = yy;
		}
		
		Point_i minus(Point_i a) { return new Point_i(x - a.x, y - a.y); }
		long dot(Point_i a) 	 { return x * a.x + y * a.y;			 }
		long cross(Point_i a) 	 { return x * a.y - y * a.x;			 }
		
		public int compareTo(Point_i p) {
			if(x != p.x) return Long.compare(x, p.x);
			return Long.compare(y, p.y);
		}
	}

	//use this version of point if the answer required is really precise
	//something like up to 5 decimal places
	static class Point_f {
		double x, y;
		Point_f(double xx, double yy) {x = round(xx); y = round(yy);}
		
		//java didn't handle precision well, 
		//this method is just round the number to 5 decimal places
		//change the number if you need more precise number
		private double round(double a) {return (double)Math.round(a * 100000) / 100000;}
		
		Point_f minus(Point_f a) { return new Point_f(x - a.x, y - a.y); }
		double cross(Point_f a)	 { return x * a.y - y * a.x;			 }
	}
	
	//rotate point p counter clock wise by rad respect to pivot
	//this is the place where you want to use floating points instead of integer points
	static Point_f rotate(Point_f pivot, Point_f p, double rad) {
		double x = p.x - pivot.x, y = p.y - pivot.y;
		double newx = x * Math.cos(rad) - y * Math.sin(rad);
		double newy = x * Math.sin(rad) + y * Math.cos(rad);
		return new Point_f(newx + pivot.x, newy + pivot.y);
	}
	
	//return a positive number if a->b->c is a left turn
	//negative if a->b->c is a right turn
	//the absolute value of the number returned 
	//is the area of the parallelogram with the side AB and AC
	static long ccw(Point_i a, Point_i b, Point_i c) {
		return b.minus(a).cross(c.minus(a));
	}
	
	// ***IMPORTANT*** return 2x the area
	static long triangleArea(Point_i a, Point_i b, Point_i c) {
		return (-b.y*c.x + a.y*(-b.x + c.x) + a.x*(b.y - c.y) + b.x*c.y);
	}
	
	//return true if point C lies on line AB
	static boolean inIntSegment(Point_i a, Point_i b, Point_i c) {
		if(ccw(a, b, c) != 0) return false; //if a->b->c is a left turn or right turn
		Point_i tmp = b.minus(a);
		long dotProduct = c.minus(a).dot(tmp);
		long sqrLength = tmp.dot(tmp);
		return !(dotProduct < 0 || dotProduct > sqrLength);
	}
	
	//return true if point P is inside or on the triangle ABC
	static boolean inTriangle(Point_i a, Point_i b, Point_i c, Point_i p) {
		//remove this check if only want points that is inside the triangle
		if(inIntSegment(a, b, p) || inIntSegment(a, c, p) || inIntSegment(b, c, p))
			return true;
		long area = triangleArea(a, b, c); //2x area works in this case
		if(area == 0) return false;
		double s = (double)(a.y*c.x - a.x*c.y + (c.y - a.y)*p.x + (a.x - c.x)*p.y) / area;
		if(s <= 0) return false;
		double t = (double)(a.x*b.y - a.y*b.x + (a.y - b.y)*p.x + (b.x - a.x)*p.y) / area;
		return t > 0 && 1 - s - t > 0;
	}
	
	//return true if the point p is inside or on the polygon
	//O(logn) approach!! Using the idea of binary search
	static boolean inPolygon(Point_i p, List<Point_i> hull) {
		int left = 1, right = hull.size() - 3;
		Point_i first = hull.get(0);
		while(left <= right) {
			final int mid = (left + right) / 2;
			long cross1 = ccw(first, hull.get(mid), p);
			long cross2 = ccw(first, p, hull.get(mid + 1));
			if(cross1 >= 0 && cross2 >= 0 && inTriangle(first, hull.get(mid), hull.get(mid + 1), p))
				return true;
			else if(cross1 < 0)
				right = mid - 1;
			else
				left = mid + 1;
		}
		return false;
	}
	
	//return the convex hull given a set of points. 
	//note that the returned convex hull's first and last point are the same.
	static List<Point_i> convexHull1(List<Point_i> p) {
		int i, t, k = 0, n = p.size();
		Point_i[] H = new Point_i[n * 2];
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
	
	//return the area of a polygon
	static double polygonArea(List<Point_i> list) {
        double area = 0;
        for(int i = 0; i < list.size(); i++) {
            Point_i p1 = list.get(i);
            Point_i p2 = list.get((i + 1) % list.size());
            area += p1.cross(p2);
        }
        return Math.abs(area) / 2;
    }
	
	//same as ccw, this just return true or false
	static boolean leftTurn(Point_i a, Point_i b, Point_i c) {
		return b.minus(a).cross(c.minus(a)) > 0;
	}
	
	//return true if line AB intersect with line CD
	//***IMPORTANT*** does not include the case that one end of a line in on another line
	static boolean doIntersect(Point_i a, Point_i b, Point_i c, Point_i d) {
		return leftTurn(a, b, c) != leftTurn(a, b, d) && leftTurn(c, d, a) != leftTurn(c, d, b);
	}

	//return true if it is a simple polygon
	static boolean isSimplePolygon(List<Point_i> list) {
		for(int i = 0; i < list.size(); i++) {
            for(int j = 0; j < list.size(); j++) {
                if(Math.abs(i - j) <= 1 || Math.abs(i - j) >= list.size() - 1)
                    continue;
                if(doIntersect(list.get(i), list.get((i + 1) % list.size()), list.get(j), list.get((j + 1) % list.size())))
                    return false;
            }
        }
        return true;
	}
	
	
	final static double EPS = 1e-9;
	
	//cuts polygon Q along the line formed by point a -> point b
	//***IMPORTANT*** the last point must be the same as the first point
	static List<Point_f> cutPolygon(Point_f a, Point_f b, List<Point_f> Q) {
		List<Point_f> P = new ArrayList<Point_f>();
		for (int i = 0; i < (int)Q.size(); i++) {
			double left1 = b.minus(a).cross(Q.get(i).minus(a)), left2 = 0;
			if (i != (int)Q.size()-1) left2 = b.minus(a).cross(Q.get(i + 1).minus(a));
			if (left1 > -EPS) P.add(Q.get(i)); // Q[i] is on the left of ab
			if (left1 * left2 < -EPS) // edge (Q[i], Q[i+1]) crosses line ab
				P.add(lineIntersectSeg(Q.get(i), Q.get(i+1), a, b));
		}
		if (!P.isEmpty() && !(P.get(P.size() - 1).x == P.get(0).x && P.get(P.size() - 1).y == P.get(0).y))
			P.add(P.get(0)); // make P's first point = P's last point
		return P; 
	}
	
	//line segment p-q intersect with line A-B.
	static Point_f lineIntersectSeg(Point_f p, Point_f q, Point_f A, Point_f B) {
		double a = B.y - A.y;
		double b = A.x - B.x;
		double c = B.x * A.y - A.x * B.y;
		double u = Math.abs(a * p.x + b * p.y + c);
		double v = Math.abs(a * q.x + b * q.y + c);
		return new Point_f((p.x * v + q.x * u) / (u+v), (p.y * v + q.y * u) / (u+v)); 
	}

}
