package processing;

public class Classify {

	
	/**
	 * 
	 * classificar usando distancia euclidiana
	 * 
	 * */
	public static int classify_euclidiana(double[] x, double[][] mean){
		
		int i=0, idx = 0;
		double menor = 0.0;
		while(i<x.length){
			double distance = Test.Euclideana(x, mean[i]);
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
