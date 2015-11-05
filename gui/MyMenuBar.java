package gui;

import java.io.File;

import processing.Classify;
import processing.ImageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class MyMenuBar extends MenuBar{
	
	public MyMenuBar(){
		super();
		
		this.getMenus().addAll(
				menuFile(), 
				menuView(), 
				menuClassification(), 
				menuHelp()
				);
	}
	
	/**
	 * Menu para carregar arquivos de treino e testes
	 */
	private Menu menuClassification()
	{
		Menu classifMenu = new Menu("Classification");
		MenuItem toTrain = new MenuItem("Coach");
		MenuItem toTest = new MenuItem("Test");
		MenuItem toClassify = new MenuItem("Classify");
		toClassify.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(Main.imagePane2.isImageReady()){
					Main.messagePane.addNewMessage("Image classificada como tipo "+ Classify.classify_mahalanobis(Main.imagePane2.image));
					System.out.println(Classify.classify_mahalanobis(Main.imagePane2.image));
				}
			}
		});
//		toTrain.setOnAction(coachHandler);
//		toTest.setOnAction(testHandler);
		classifMenu.getItems().addAll(toTrain, toTest, toClassify);
		return classifMenu;
	}
	
	
	
	/**
	 * Metodo auxiliar(na construcao interface)
	 * Montar o menuView
	 * 
	 * */
	private Menu menuView()
	{
		Menu menuView = new Menu("View");

		RadioMenuItem selectImage = new RadioMenuItem("Cut");
		selectImage.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
		imageSelection(selectImage);
		
		

		RadioMenuItem zoomItem = new RadioMenuItem("Zoom");
		zoomItem.setSelected(false);
		zoomItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
		zoom(zoomItem);
		
		
		menuView.getItems().add(zoomItem);
		menuView.getItems().add(selectImage);
		return menuView;
	}
	
	private void imageSelection(RadioMenuItem selectImage){
		selectImage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(selectImage.isSelected()){
					Main.imagePane1.setImageSelection();
				}else{
//					Main.imagePane1.unsetImageSelection();
				}
			}
		});
	}
	
	
	private void zoom (RadioMenuItem zoomItem){
		zoomItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                System.out.println("Zoom");
                if(zoomItem.isSelected())
                {
                	Main.imagePane1.setScalable();
                	
                }
                else
                {
                	Main.imagePane1.unsetScalable();
                }
            }
		});
	}
	
	
	/**
	 * Metodo auxiliar(na construcao interface)
	 * Montar o menuFile
	 * 
	 * */
	private Menu menuFile()
	{
		Menu menuFile = new Menu("File");
        
        MenuItem loadImage = new MenuItem("Open image");
        menuFile.getItems().addAll(loadImage);
       
        
        // Configurar funcao e shortcut do menuItem
        loadImage.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        loadImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                
                File file = ImageManager.fileChooser(null);
                Main.imagePane1.loadImage(ImageManager.file2Mat(file));
                
                
            }
        });
        
        return menuFile;
	}

	
	
	/**
	 * Metodo de auxilio ao software
	 */
	private Menu menuHelp()
	{
		Menu menuHelp = new Menu("Help");
		MenuItem aboutSoftware = new MenuItem("Software");
		
		Alert alert = new Alert(AlertType.INFORMATION);

		menuHelp.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
		
		aboutSoftware.setOnAction((event) -> 
		{
			alert.setTitle("Keys");
	    	alert.setContentText("Ctrl+O para abrir imagem\n\n"
	    		+"Ctrl+S para salvar uma imagem editada\n\n"
				+ "Ctrl+Shift+C para cortar imagem\n\n"
				+"Ctrl+Shift+Z para zoom\n\n"
				+"No modo de Zoom, use teclas Z e C\n"
				+"Para aumentar e diminuir a imagem\n\n"
				+"No modo de cut, apos selecionar,\n"
				+"Use rightclick para finalizar\n");
	    	alert.showAndWait();	
		});	
		menuHelp.getItems().add( aboutSoftware);

		return menuHelp;
	}
	
	
	/**
	 * Metodo auxiliar(na construcao interface)
	 * Montar o menuFile
	 * 
	 * */
}
