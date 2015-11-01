package processing;

public class Coach {
	
	
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
