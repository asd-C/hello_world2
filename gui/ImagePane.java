package gui;

import org.opencv.core.Mat;

import processing.ImageManager;
import processing.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ImagePane extends ScrollPane implements EventHandler<KeyEvent>{
	public Group group;
	public Mat image;
	
	public ImageSelection imageSelection;
	
	private ImageView imageView;
	private DoubleProperty zoomProperty = new SimpleDoubleProperty(100);
	private EventHandler<MouseEvent> getSelectedArea;
	private int image_width;
	private int image_height;
	
	public ImagePane(){
		group = new Group();

		image_height = image_width = 0;
		
		imageView = new ImageView();
		imageView.preserveRatioProperty().set(true);
		group.getChildren().add(imageView);
		
		
		this.setContent(group);
	}
	
	public void loadImage(Mat image){
		this.image = image;
		
		image_width = image.width();
		image_height = image.height();
		
		zoomProperty.set(image.width());
		zoomProperty.addListener(new InvalidationListener() 
		{
            @Override
            public void invalidated(Observable arg0) 
            {            
                imageView.setFitWidth(zoomProperty.get());
            }
        });
		
		imageView.setImage(ImageManager.mat2Image(image));
		
	}
	
	public void setImageSelection(){
		imageSelection = new ImageSelection(group);
		getSelectedArea = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	
                if (event.isSecondaryButtonDown()) {
                	System.out.println("get click");
                    Bounds bounds = imageSelection.getBounds();
//                    if((event.getX()<bounds.getMaxX()) 
//                    		&& (event.getX()>bounds.getMinX()) 
//                    		&& (event.getY()<bounds.getMaxY()) 
//                    		&& (event.getY()>bounds.getMinY())){
                    	Mat sub_image = image.submat(
                    			(int)(Math.max(bounds.getMinY(),0)), 
                    			(int)(Math.min(bounds.getMaxY(),image_height)), 
                    			(int)(Math.max(bounds.getMinX(),0)), 
                    			(int)(Math.min(bounds.getMaxX(),image_width))
                    			);
                    	Main.imagePane2.loadImage(sub_image);
                    	System.out.println(bounds);
//                    	group.getChildren().remove(1);

//                    }
                }
            }
        };
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, getSelectedArea);
	}
	
	
	public void setScalable(){
		this.addEventHandler(KeyEvent.KEY_PRESSED, this);
	} 
	
	
	public void unsetScalable(){
		this.removeEventHandler(KeyEvent.KEY_PRESSED, this);
	} 
	
	
	public boolean isImageReady(){
		if(image != null){
			if(image.empty() == false){
				return true;
			}else{
				System.out.println("Erro: imagem vazia");
				return false;
			}
		}else{
			System.out.println("Erro: imagem null");
			return false;
		}
	}
	
	
	
	public boolean isGroupReady(){
		if(group.getChildren().size() != 0){
			return true;
		}else{
			System.out.println("Erro: grupo vazio");
			return false;
		}
	}

	
	
	@Override
	public void handle(KeyEvent event) {
		
		// TODO Auto-generated method stub
		if(event.getCode()== KeyCode.C) 
    	{
            zoomProperty.set(zoomProperty.get() * 1.1);
        }
    	else if(event.getCode() == KeyCode.Z)
        {
            zoomProperty.set(zoomProperty.get() / 1.1);
        }
		
	}
}
