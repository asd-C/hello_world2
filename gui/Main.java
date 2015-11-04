package gui;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import processing.Coach;
import processing.FileManager;
import processing.Test;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

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
//		new Main().testCommonsMath();
	}
	
	public void testCommonsMath(){
//		Covariance a = new Covariance(new double[][]{{1,5,0},{10,3,2}});
//		RealMatrix matrix = a.getCovarianceMatrix();
//		matrix = MatrixUtils.inverse(matrix);
//		double[][] m = matrix.getData();
//		int i=0, j=0;
//		while(i < m.length){
//			while(j < m[0].length){
//				
//				System.out.print(m[i][j]+" ");
//				j++;
//			}
//			System.out.println();
//			j=0;
//			i++;
//		}
		
		//System.out.println(Test.Mahalanobis(new double[]{2,1}, new double[]{0.5873,0.3978}, new double[][]{{0.0861,0.0406},{0.0406,0.1301}}));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		testCommonsMath();
		
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
