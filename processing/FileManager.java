package processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


import org.opencv.core.Mat;


public class FileManager {

	
	public static ArrayList<ArrayList<Mat>> readTrainFile(File file){
		
		ArrayList<ArrayList<Mat>> listImages = new ArrayList<ArrayList<Mat>>();
		
		int classes = 0;
		int size    = 0;
		int x       = 0;
		File f = null;
		int c = 0;

		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			
			classes = Integer.parseInt(br.readLine());
			size    = Integer.parseInt(br.readLine());			
			
			int i = 0;
			while(i<classes){
				listImages.add(new ArrayList<Mat>());
				i++;
			}
			
			while(x < size)
			{
				String a = br.readLine();
				System.out.println("file read: " + a);
			    f = new File(a);
			    c = Integer.parseInt(br.readLine());
			    listImages.get(c).add(ImageManager.file2Mat(f));
				x++;
			}
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return listImages;
	}
	
	
}
