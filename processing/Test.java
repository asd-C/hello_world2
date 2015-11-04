package processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import gui.FeaturesSelectorAndFunctions;

public class Test {

	
	public static double readTestFile(File file){
		
		int size    = 0;
		int x       = 0;
		File f = null;
		int c = 0;
		int counter = 0;

		ArrayList<Integer> selectedFeatures_tmp = FeaturesSelectorAndFunctions.selectedFeatures;
		int angle_tmp = FeaturesSelectorAndFunctions.angle;
		
		if(Coach.means != null && selectedFeatures_tmp != null && angle_tmp >0 && selectedFeatures_tmp.size() == 2){
			try(BufferedReader br = new BufferedReader(new FileReader(file)))
			{
				
				size    = Integer.parseInt(br.readLine());			
				
				while(x < size)
				{
					String a = br.readLine();
					//System.out.println("file read: " + a);
				    f = new File(a);
				    c = Integer.parseInt(br.readLine());
				    int var = Classify.classify_euclidiana(ImageManager.file2Mat(f));
				    if(var == c){
				    	counter++;
				    }
				    System.out.println(var +" : "+c);
					x++;
				}
				
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}else{
			System.out.println("erro: nao treinou classificador, ou nao selecionou angulacao e descritor");
		}
		return (double)counter/size;
	}
	
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
	

	
	
	/**
	 * 
	 * a ser feita
	 * 
	 * */
	public static double Mahalanobis(double[] x, double[] mean, double[][] covariance){
		double res = 0.0;
		
		//if(Coach.isMeansAndCovarReady()){
			Array2DRowRealMatrix x_v = new Array2DRowRealMatrix(new double[][]{x}),
					mean_v = new Array2DRowRealMatrix(new double[][]{mean});
			Array2DRowRealMatrix tmp = x_v.subtract(mean_v);
			RealMatrix covar_m = MatrixUtils.createRealMatrix(covariance);
			RealMatrix tmp2 = tmp.multiply(covar_m);
			tmp2 = tmp2.multiply(tmp.transpose());
			res = Math.sqrt(tmp2.getEntry(0, 0));
		//}
		
		
		return res;
	}
	
	
}
