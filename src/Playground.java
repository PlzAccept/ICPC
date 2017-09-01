import java.util.*;
 
public class Playground {
 
	public static void main(String[] args){
//		for(int i = 0; i < 700; i++) for(int j = 0; j < 700; j++) for(int k = 0; k < 700; k++) {
//			if(6 * i + 4 * j + 5 * k == 695 && 2 * i + 5 * j + 3 * k == 285)
//				System.out.println(i + " " + j + " " + k + " " + (i + j + k));
//		}
		for(int i = 0; i < 100; i++) {
			if((((double)i * 199 + 222) / 98.0) == (i * 199 + 222) / 98)
				System.out.println(i + " " + (i * 199 + 222) / 98);
		}
	}
}