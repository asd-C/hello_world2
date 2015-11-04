package processing;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.opencv.core.Mat;
import gui.*;

public class Coach {

	public static double[][] means = null;
	public static double[][][] invOfCovar = null;
	
	
	public static double[][] matToMeans(ArrayList<ArrayList<Mat>> images){
		ArrayList<Integer> selectedFeatures_tmp = FeaturesSelectorAndFunctions.selectedFeatures;
		int angle_tmp = FeaturesSelectorAndFunctions.angle;
		int classes = images.size();
		int	size_class = images.get(0).size();
		means = new double[classes][];
		invOfCovar = new double[classes][][];

		double[][][] arrayOfImages = new double[classes][size_class][2];
		
		if(selectedFeatures_tmp != null && angle_tmp > 0){
			if(selectedFeatures_tmp.size() == 2 ){
				
				int i = 0, j = 0;
				while(i<classes){
					while(j<size_class){
						CoOccurrenceMatrix com = new CoOccurrenceMatrix(angle_tmp, images.get(i).get(j));
						arrayOfImages[i][j] = com.getFeatures(selectedFeatures_tmp);
						j++;
					}
					i++;
					j=0;
				}
				i=0;
				while(i<classes){
					Coach.means[i] = coach(arrayOfImages[i]);
					Coach.invOfCovar[i] = invOfCovar(arrayOfImages[i]);
					i++;
				}
			
			}else{
				System.out.println("descritores maior que 2");
			}
		}else{
			System.out.println("descritores null ou angulacao null");
		}
		
		
		return means;
	}
	
	
	public static double[][] invOfCovar(double [][] input){
		return MatrixUtils.inverse(new Covariance(input).getCovarianceMatrix()).getData();
	}
	
	
	/**
	 * 
	 * calcular a media de cada descritor
	 * 
	 * input:	x[n_images][n_features]
	 * output:	res[n_features]		the mean of features
	 * 
	 * */
	public static double[] coach(double[][] x){
		int n_images 	= x.length;
		int n_features	= x[0].length;
		
		int i,j;
		
		double[] res = new double[n_features];
		
		i = j = 0;
		while(i < n_features){
			while(j < n_images){
				
				res[i] += x[j][i];
				
				j++;
			}
			
			res[i] = res[i] / n_images;
			j=0;
			i++;
		}
		
		return res;
	}
	
	
	public static boolean isMeansAndCovarReady(){
		if(Coach.means != null && Coach.invOfCovar != null){
			if(Coach.means.length != 0 && Coach.invOfCovar.length != 0){
				return true;
			}else{
				System.out.println("Means zero or Covar zero");
				return false;
			}
		}else{
			System.out.println("Means null or Covar null");
			return false;
		}
	}
	
}
