package processing;

import testing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


public class CoOccurrenceMatrix {

	private double energy,
			entropy,
			contraste,
			homogeneidade;
	
	private int[][] glmc;
	
	public int[][] getGlmc() { return glmc;}

	public void setGlmc(int[][] glmc) { this.glmc = glmc;}


	public double[] getFeatures(ArrayList<Integer> al){
		
		double[] res = new double[2];
		
		int i=0;
		while(i<2){
			int idx = al.get(i);
			
			switch(idx){
			case 0:
				res[i] = contraste;
				break;
			case 1:
				res[i] = energy;
				break;
			case 2:
				res[i] = entropy;
				break;
			case 3:
				res[i] = homogeneidade;
				break;
			}
			i++;
		}
		return res;
	}
	
	/**
	 * 
	 * Contrutor
	 * 
	 * */
	
	public CoOccurrenceMatrix(int var, Mat image){
		energy = entropy = contraste = homogeneidade = 0.0;
		glmc = new int[256][256];
		
		features(var, image);
	}
	
	
	/**
	 * 
	 * criar matriz de co-ocorrencia
	 * 
	 * */
	public int[][] createMatrix(int var, Mat image){
		
		// convert image to 8 bits
		image.convertTo(image, CvType.CV_8U);
		
		
		double[] pixel, pixel1, pixel2, pixel3, pixel4, pixel5, pixel6, pixel7, pixel8;
		
		for(int i=var; i<image.height()-var; i++){
			for(int j=var; j<image.width()-var; j++){
				pixel = image.get(i, j);

				// recuperar os pixels
				pixel1 = image.get(i-var, j-var);
				pixel2 = image.get(i    , j-var);
				pixel3 = image.get(i+var, j-var);
				pixel4 = image.get(i-var, j    );
				pixel5 = image.get(i+var, j    );
				pixel6 = image.get(i-var, j+var);
				pixel7 = image.get(i    , j+var);
				pixel8 = image.get(i+var, j+var);
				
				// modificar matriz de coocorrencia
				glmc[(int)pixel[0]][(int)pixel1[0]]++;
				glmc[(int)pixel[0]][(int)pixel2[0]]++;
				glmc[(int)pixel[0]][(int)pixel3[0]]++;
				glmc[(int)pixel[0]][(int)pixel4[0]]++;
				glmc[(int)pixel[0]][(int)pixel5[0]]++;
				glmc[(int)pixel[0]][(int)pixel6[0]]++;
				glmc[(int)pixel[0]][(int)pixel7[0]]++;
				glmc[(int)pixel[0]][(int)pixel8[0]]++;
				
			}
		}
		
		return glmc;
	}
	
	
	/**
	 * 
	 * calcular entropia, homogenidade, energia e contraste
	 * 
	 * */
	public void features(int var, Mat image){
		int[][] glmc = createMatrix(var, image);
		
		double total = (image.height()- var*2)*(image.width()-var*2)*8;
		
		int i=0, j=0;

		double elem = 0.0;
		
		while(i<glmc.length){
			while(j<glmc[0].length){
				elem = (double)glmc[i][j]/total;
				energy 			+= (Math.pow(elem, 2));
				entropy 		-= (elem == 0) ? 0 : (elem * Math.log(elem));
				homogeneidade	+= (elem / (1+Math.pow(elem, 2)));
				contraste		+= elem * Math.pow(elem, 2);
				j++;
			}
			
			j=0;
			i++;
		}
	}
	
}
