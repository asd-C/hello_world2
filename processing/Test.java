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
	
	public final static int EUCLIDIANA = 0;
	public final static int MAHALANOBIS = 1;
	public final static int EUCLIDIANA_AND_MAHALANOBIS = 2;
	
	private final static int N_CLASSIFIER = 2; 
	
	public static double[] readTestFile(File file, int classifier){
		
		double[] res = new double[N_CLASSIFIER];
		
		int correct_class = 0;
		int[] pred_class = new int[N_CLASSIFIER];
		int[] counter = new int[N_CLASSIFIER];

		
		if(Coach.isMeansAndIncOfCovarReady() && FeaturesSelectorAndFunctions.isFeaturesAndAngleReady()){
			int i = 0, size = -1;
			
			try(BufferedReader br = new BufferedReader(new FileReader(file)))
			{
				
				size = Integer.parseInt(br.readLine());			
				while(i < size)
				{
					String line = br.readLine();
					File image = new File(line);
					
				    correct_class = Integer.parseInt(br.readLine());
				    
				    if(classifier == Test.EUCLIDIANA){
				    	pred_class[Test.EUCLIDIANA] = Classify.classify_euclidiana(ImageManager.file2Mat(image));				    
					    if(pred_class[Test.EUCLIDIANA] == correct_class){
					    	counter[Test.EUCLIDIANA]++;
					    }
				    }else if(classifier == Test.MAHALANOBIS){
				    	pred_class[Test.MAHALANOBIS] = Classify.classify_mahalanobis(ImageManager.file2Mat(image));				    
					    if(pred_class[Test.MAHALANOBIS] == correct_class){
					    	counter[Test.MAHALANOBIS]++;
					    }
				    }
				    
					i++;
				}
				
				
			}
			catch (Exception e) { e.printStackTrace();}
			
			i = 0;
			while(i < N_CLASSIFIER){

				res[i] = (double)counter[i]/size;
				i++;
			}
			
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * calcular distancia euclidiana da imagem
	 * 
	 * */
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
	 * calcular distancia Mahalanobis da imagem
	 * 
	 * */
	public static double Mahalanobis(double[] x, double[] mean, double[][] covariance){
		double res = 0.0;
		
		if(Coach.isMeansAndIncOfCovarReady()){
			Array2DRowRealMatrix x_v = new Array2DRowRealMatrix(new double[][]{x}),
					mean_v = new Array2DRowRealMatrix(new double[][]{mean});
			Array2DRowRealMatrix tmp = x_v.subtract(mean_v);
			RealMatrix covar_m = MatrixUtils.createRealMatrix(covariance);
			RealMatrix tmp2 = tmp.multiply(covar_m);
			tmp2 = tmp2.multiply(tmp.transpose());
			res = Math.sqrt(tmp2.getEntry(0, 0));
		}
		
		return res;
	}
	
	
}
