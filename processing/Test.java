package processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Test {

	public static double Euclideana(double[] x, double[] mean){
		
		double res = 0;
		
		int i=0;
		while(i<x.length){
			
			res += Math.pow(x[i] - mean[i], 2);
			
			i++;
		}
		res = Math.sqrt(res);
		
		return res;
	}
	
	public static double[] mean(double[][] x){
		int size = x[0].length;
		int num = x.length;
		
		double[] res = new double[size];
		
		int i=0, j=0, total=0;
		while(i<num){
			while(j<size){
				total += x[i][j];
				j++;
			}
			res[i] = total/size;
			total = j = 0;
			i++;
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * a ser feita
	 * 
	 * */
	public static double Mahalanobis(double[] x, double[] mean){

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat a = Mat.ones(10, 10, CvType.CV_16S);
		
		
		return 0.0;
	}
	
	
}
