package processing;

import testing.*;
import java.io.File;
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
	
	public int[][] getGlmc() {
		return glmc;
	}

	public void setGlmc(int[][] glmc) {
		this.glmc = glmc;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public double getContraste() {
		return contraste;
	}

	public void setContraste(double contraste) {
		this.contraste = contraste;
	}

	public double getHomogeneidade() {
		return homogeneidade;
	}

	public void setHomogeneidade(double homogeneidade) {
		this.homogeneidade = homogeneidade;
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
		
		while(i<255){
			while(j<255){
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
	
	public static void main(String[] args){

//		File file = new File("./");
//		System.out.println(file.getAbsolutePath());

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		String s = "f";
		double[][] x = retrieveData(s);
		//double[][] mean = new double[4][];
		//mean[0] = Coach.coach(x);
//		
//		s = "e";
//		x = retrieveData(s);
//		mean[1] = Coach.coach(x);
//		
//		s = "f";
//		x = retrieveData(s);
//		mean[2] = Coach.coach(x);
//		
//		s = "g";
//		x = retrieveData(s);
//		mean[3] = Coach.coach(x);
//		
//		Input_buf.saveFile(mean);
//		
		//System.out.println("a media: " + mean[0] + ":" + mean[1]);
		
		double[][] mean = Input_buf.readFile();
		int i=0;
		while(i<20){
			System.out.print(Classify.classify(x[i], mean) + " ");
			i++;
		}
//		int i=0, j=0;
//		while(i<mean.length){
//			
//			while(j<mean[0].length){
//				System.out.print(mean[i][j] + "\t");
//				j++;
//			}
//			System.out.println();
//			j=0;
//			i++;
//		}
		
//		s = "e";
//		double[][] y = retrieveData(s);
//		
//		int i=0;
//		while(i<20){
//			System.out.println("distancia euclidiana: " + Test.Euclideana(y[i], mean));
//			
//			i++;
//		}
	}
	
	public static double[][] retrieveData(String s){
		double[][] res = new double[20][4];

		for(int i=0;i<20;i++){
			System.out.print(s+i+" ");
			File file = new File("/Users/chendehua/Downloads/rois/" + s + i + ".png");
			if(file.exists() == false) System.out.println("file does not exist");
			Mat image = Imgcodecs.imread(file.getPath(), Imgcodecs.IMREAD_GRAYSCALE);
			
			CoOccurrenceMatrix com = new CoOccurrenceMatrix(1, image);
			res[i][0] = com.getContraste();
			res[i][1] = com.getEnergy();
			res[i][2] = com.getHomogeneidade();
			res[i][3] = com.getEntropy();
		}
		System.out.println();
		return res;
	}
	
}
