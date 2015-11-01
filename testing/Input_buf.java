package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Input_buf {

	public static void saveFile(String s) {

		try(BufferedWriter bw = new BufferedWriter(new FileWriter("./test_files/mean.txt"))){
		
			bw.write(s);
			bw.close();
			
		}catch(IOException e){e.printStackTrace();};
	}

	public static void saveFile(double[][] x){
		int n_class = x.length;
		int n_feature = x[0].length;
		
		String res = "";
		res += n_class + "\n" + n_feature + "\n";
		
		int i=0, j=0;
		while(i<n_class){
			while(j<n_feature){
				
				res += x[i][j] + " ";
				
				j++;
			}
			res += "\n";
			j=0;
			i++;
		}
	
		
		saveFile(res);
	}
	
	public static double[][] readFile() {
		double [][] res = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader("./test_files/mean.txt"))){

			int n_class = Integer.parseInt(br.readLine());
			int n_feature = Integer.parseInt(br.readLine());
			
			res = new double[n_class][n_feature];
			
			int i=0, j=0;
			while(i<n_class){
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				while(j<n_feature){
					res[i][j] = Double.parseDouble(st.nextToken());
					j++;
				}
				j=0;
				i++;
			}
			br.close();
			
		}catch(IOException e){e.printStackTrace();};
		
		return res;
	}
	
}
