package processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import gui.FeaturesSelectorAndFunctions;

public class Test {

	public static double[][] means;
	
	public static double readTestFile(File file){
		
		int size    = 0;
		int x       = 0;
		File f = null;
		int c = 0;
		int counter = 0;

		ArrayList<Integer> selectedFeatures_tmp = FeaturesSelectorAndFunctions.selectedFeatures;
		int angle_tmp = FeaturesSelectorAndFunctions.angle;
		
		if(means != null && selectedFeatures_tmp != null && angle_tmp >0 && selectedFeatures_tmp.size() == 2){
			try(BufferedReader br = new BufferedReader(new FileReader(file)))
			{
				
				size    = Integer.parseInt(br.readLine());			
				
				while(x < size)
				{
					String a = br.readLine();
					System.out.println("file read: " + a);
				    f = new File(a);
				    c = Integer.parseInt(br.readLine());
				    CoOccurrenceMatrix com = new CoOccurrenceMatrix(angle_tmp, ImageManager.file2Mat(f));
				    int var = Classify.classify_euclidiana(com.getFeatures(selectedFeatures_tmp), means) ;
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
	public static double Mahalanobis(double[] x, double[] mean){

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat a = Mat.ones(10, 10, CvType.CV_16S);
		
		
		return 0.0;
	}
	
	
}
