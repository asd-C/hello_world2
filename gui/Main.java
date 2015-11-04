package gui;


import org.opencv.core.Core;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application{

	private MessagePane messagePane;
	private ImagePane imagePane1, imagePane2;
	private MyMenuBar menuBar;
	private FeaturesSelectorAndFunctions featuresSelectorAndFunctions;
	
	
	private Stage stage;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		
		messagePane 					= new MessagePane();
		imagePane1 						= new ImagePane();
		imagePane2 						= new ImagePane();
		menuBar 						= new MyMenuBar();
		featuresSelectorAndFunctions 	= new FeaturesSelectorAndFunctions();
		
		
		stage = primaryStage;
		
		
		
		BorderPane root = new BorderPane();
		
		SplitPane container = new SplitPane(),
				center = new SplitPane(),
				right = new SplitPane();
		
		container.setOrientation(Orientation.VERTICAL);
		center.setOrientation(Orientation.HORIZONTAL);
		right.setOrientation(Orientation.VERTICAL);
		
		
		
		
		container.setDividerPositions(0.75f);
		container.getItems().addAll(center, messagePane);
		
		
		
		
		center.setDividerPositions(0.7f);
		center.getItems().addAll(imagePane1, right);
		
		
		
		
		right.setDividerPositions(0.5f);
		right.getItems().addAll(featuresSelectorAndFunctions, imagePane2);
		
		
		
		
		// MenuBar() a ser feito
		root.setTop(menuBar);
		root.setCenter(container);
		
		
		Scene scene = new Scene(root, 1100, 800);

        
        // Colocar scene no Stage
        // Configurar atributos do Stage
        stage.setTitle("TP_PID");
        stage.setScene(scene);
        stage.show();
		
	}

}
