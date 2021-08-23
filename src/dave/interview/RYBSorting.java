package dave.interview;

import java.util.Arrays;

public class RYBSorting {
	// TODO Auto-generated method stub
	final static String B = "B";
	final static String Y = "Y";
	final static String R = "R";

	public static void main(String[] args) {
			
		String[] rybR = {B,Y,R,B,Y,R,Y,Y,R,R,B,B,Y,Y,R,B,Y,R,B,Y,R,Y,Y,R,R,B,B,Y,Y,R,B,Y,R,B,Y,R,Y,Y,R,R,B,B,Y,Y,R};
		int i=0;
		int c=0;
		while ( i<rybR.length-1){
			if(compare(rybR[i],rybR[i+1])==-1){
				String tmp = rybR[i];
				rybR[i]=rybR[i+1];
				rybR[i+1]=tmp;
				if(i>0 && compare(rybR[i-1],rybR[i])==-1) i--;
			}else{
				i++;
			}
			
			c++;
		}
		
		System.out.println(Arrays.toString(rybR) + " by interactions: " + c);
	}
	
	public static int compare(String ball1, String ball2){
		if(ball1==ball2) return 0;
		if(ball1 == R) return 1;
		if(ball2 == R) return -1;
		if(ball1 == B) return 1;
		if(ball2 == B) return -1;
		return -2;
	}
}
