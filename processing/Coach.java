package processing;

import java.util.ArrayList;

import org.opencv.core.Mat;
import gui.*;

public class Coach {
	
	public static double[][] matToCOM(ArrayList<ArrayList<Mat>> images){
		ArrayList<Integer> selectedFeatures_tmp = FeaturesSelectorAndFunctions.selectedFeatures;
		int angle_tmp = FeaturesSelectorAndFunctions.angle;
		int classes = images.size();
		int	size_class = images.get(0).size();
		double[][] res = new double[classes][];

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
					res[i] = coach(arrayOfImages[i]);
					i++;
				}
			
			}else{
				System.out.println("descritores maior que 2");
			}
		}else{
			System.out.println("descritores null ou angulacao null");
		}
		
		
		return res;
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
	
	public static void main(String[] args){
		double[][] x = new double[][]{
			{1,2},
			{2,1},
			{3,3},
			{2,2}
		};
		
		double[] y = coach(x);
		
		int i=0;
		System.out.print("o valor medio: ");
		while(i<y.length){
			System.out.print(y[i]+"\t");
			i++;
		}
		System.out.println();
		
		double[] example = new double[]{
			10,2
		};
		
		System.out.println("distancia euclidiana: " + Test.Euclideana(y, example));
	}
	
}
