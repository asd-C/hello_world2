package processing;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import com.sun.glass.ui.Window.Level;
import com.sun.javafx.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImageManager 
{
	
	
	/**
	 * Ler um arquivo (imagem), e converte para objeto Image (para mostrar no ImageView)
	 * 
	 * */
	public static Image readImage(File file){
		Image image = null;
		try {
			
			BufferedImage bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            
		 } catch (IOException ex) {}
		
		return image;
	}
	
	
	
	/**
	 * Converte um arquivo (imagem) para objeto Mat
	 * 
	 * */
	public static Mat file2Mat(File file)
	{
		Mat mat = Imgcodecs.imread(file.getPath(), Imgcodecs.IMREAD_GRAYSCALE);
        
//		
//		if(mat.empty()){
//        	Alert alert = new Alert(AlertType.ERROR);
//        	alert.setTitle("Erro");
//        	alert.setContentText("Ooops, objeto Mat vazio");
//        	alert.showAndWait();
//        }	
		
		return mat;
	}
	
	
	
	/**
	 * Converte objeto Mat para objeto Image (para mostrar no ImageView)
	 * 
	 * */
	public static Image mat2Image(Mat frame){
        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".png", frame, buffer);
        // build and return an Image created from the image encoded in the
        // buffer
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

	

	/**
	 * Selecionar um arquivo(imagem), e retorna um objeto Image(para mostrar no ImageView)
	 * 
	 * */
	public static File fileChooser(Stage mainStage){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.dicom", "*.tiff"));

		File selectedFile = fileChooser.showOpenDialog(mainStage);

        if(selectedFile != null){
        	return selectedFile;
        }
        
		return null;
	}
	
	
	
	/**
	 * Selecionar um arquivo(imagem), e retorna um objeto Image(para mostrar no ImageView)
	 * 
	 * */
	public static Image fileChooserAndConvert(Stage mainStage)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.dicom", "*.tiff"));

		File selectedFile = fileChooser.showOpenDialog(mainStage);

        if(selectedFile != null){
        	return mat2Image(file2Mat(selectedFile));
        }
        
		return null;
	}
	
	/**
	 * Metodo para salvar imagem
	 * @return fileImage
	 */
	public static void saveImage(Stage mainStage, Image image)
	{
	   	FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Save Image");
		fileChooser.getExtensionFilters().addAll(
			         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.dicom", "*.tiff"));
	     File file = fileChooser.showSaveDialog(mainStage);
	     if (file != null) 
	     {
             try 
             {
                 ImageIO.write(SwingFXUtils.fromFXImage(image,
                         null), "png", file);
             } 
             catch (IOException ex) 
             {
                 ex.printStackTrace();
             }
         }
     }
	
	
	/**
	 * Metodo para carregar arquivos de treino e teste
	 */
	public void loadFromTxt()
	{
		
	}
	

	
}
