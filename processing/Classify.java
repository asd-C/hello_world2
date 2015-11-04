package processing;

import java.util.ArrayList;

import org.opencv.core.Mat;

import gui.FeaturesSelectorAndFunctions;

public class Classify {

	
	public static int classify_euclidiana(Mat mat){

		int res = -1;
		ArrayList<Integer> selectedFeatures_tmp = FeaturesSelectorAndFunctions.selectedFeatures;
		int angle_tmp = FeaturesSelectorAndFunctions.angle;
		
		if(Coach.means != null && selectedFeatures_tmp != null && angle_tmp >0 && selectedFeatures_tmp.size() == 2){
			
			CoOccurrenceMatrix com = new CoOccurrenceMatrix(angle_tmp, mat);
			res = classify_euclidiana(com.getFeatures(selectedFeatures_tmp));
		}
		return res;
	}
	/**
	 * 
	 * classificar usando distancia euclidiana
	 * 
	 * */
	public static int classify_euclidiana(double[] x){
		
		int i=0, idx = 0;
		double menor = 0.0;
		while(i<Coach.means.length){
			double distance = Test.Euclideana(x, Coach.means[i]);
			if(i==0) menor = distance;
			if(distance < menor){
				menor = distance;
				idx = i;
			}
			i++;
		}
		return idx;
	}

	
	public static int classify_mahalanobis(Mat mat){

		int res = -1;
		ArrayList<Integer> selectedFeatures_tmp = FeaturesSelectorAndFunctions.selectedFeatures;
		int angle_tmp = FeaturesSelectorAndFunctions.angle;
		
		if(Coach.means != null && selectedFeatures_tmp != null && angle_tmp >0 && selectedFeatures_tmp.size() == 2){
			
			CoOccurrenceMatrix com = new CoOccurrenceMatrix(angle_tmp, mat);
			res = classify_mahalanobis((com.getFeatures(selectedFeatures_tmp)));
		}
		return res;
	}
	
	/**
	 * 
	 * classificar usando distancia Mahalanobis
	 * 
	 * */
	public static int classify_mahalanobis(double[] x){
		
		int i=0, idx = 0;
		double menor = 0.0;
		while(i<Coach.means.length){
			double distance = Test.Mahalanobis(x, Coach.means[i], Coach.invOfCovar[i]);
			if(i==0) menor = distance;
			if(distance < menor){
				menor = distance;
				idx = i;
			}
			i++;
		}
		return idx;
	}
}
