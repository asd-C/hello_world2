package processing;

import java.util.ArrayList;

import org.opencv.core.Mat;

import gui.FeaturesSelectorAndFunctions;

public class Classify {


	/**
	 * metodo principal
	 * recebe um objeto Mat de uma imagem e classificar usando distancia euclidiana
	 * 
	 * */
	public static int classify_euclidiana(Mat mat){

		int res = -1;
		
		if(Coach.isMeansReady() && FeaturesSelectorAndFunctions.isFeaturesAndAngleReady()){
			
			CoOccurrenceMatrix com = new CoOccurrenceMatrix(FeaturesSelectorAndFunctions.angle, mat);
			res = classify_euclidiana(com.getFeatures(FeaturesSelectorAndFunctions.selectedFeatures));
			
		}
		return res;
	}
	
	
	
	
	/**
	 * metodo aulixiar
	 * recebe um double[] de descritores da imagem e classificar usando distancia euclidiana
	 * 
	 * */
	private static int classify_euclidiana(double[] x){
		
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

	
	
	/**
	 * metodo principal
	 * 
	 * recebe um objeto Mat de uma imagem e classificar usando distancia Mahalanobis
	 * 
	 * */
	public static int classify_mahalanobis(Mat mat){

		int res = -1;
		
		if(Coach.isMeansAndIncOfCovarReady() && FeaturesSelectorAndFunctions.isFeaturesAndAngleReady()){
			
			CoOccurrenceMatrix com = new CoOccurrenceMatrix(FeaturesSelectorAndFunctions.angle, mat);
			res = classify_mahalanobis((com.getFeatures(FeaturesSelectorAndFunctions.selectedFeatures)));
		
		}
		return res;
	}
	
	
	
	/**
	 * metodo auxiliar
	 * recebe um double[] de descritores da imagem e classificar usando distancia Mahalanobis
	 * 
	 * */
	private static int classify_mahalanobis(double[] x){
		
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
