package gui;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Mat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import processing.Coach;
import processing.FileManager;
import processing.Test;

public class FeaturesSelectorAndFunctions extends HBox {
	
	// selecao de caracteristicas
	public static ArrayList<Integer> selectedFeatures = new ArrayList<Integer>();
	public static int angle = -1;

	
	
	// CONTRASTE = 0, ENERGY = 1, ENTROPY = 2, HOMOGENEIDADE = 3
	private final Integer[] FEATURES = new Integer[]{
			new Integer(0), 
			new Integer(1), 
			new Integer(2), 
			new Integer(3)
			};  
	
	private final int[] ANGLE = {1,2,4,8,16};
	
	

	private final Insets insets_vbox = new Insets(10,10,10,10);
	private final int spacing_vbox = 10;
	
	public static boolean isFeaturesAndAngleReady(){
		if(angle != -1){
			if(selectedFeatures.size() == 2){
				return true;
			}else{
				System.out.println("erro: o numero de descritores selecionados diferente de 2");
				return false;
			}
		}else{
			System.out.println("erro: nao selecionou distancia de matrix de coocorrencia");
			return false;
		}
	}

	public static ProgressIndicator bar = new ProgressIndicator();
	
	public FeaturesSelectorAndFunctions(){
		super();

		this.setSpacing(10);
		//this.getChildren().add(bar);
		Button b = new Button("train");
//		b.setOnMouseClicked(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event event) {
//				// TODO Auto-generated method stub
//				Main.messagePane.addNewMessage("Training classfier...\n");
//				findBestParameters();
//
//			}
//
//		});
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FeaturesSelectorAndFunctions.bar = new ProgressIndicator();
				
				Main.messagePane.addNewMessage("Training classfier...\n");
//				new Thread(new Runnable() {
//					@Override 
//					public void run() {
//						for (int i=1; i<=100; i++) {
//							final int counter = i;
//							Platform.runLater(new Runnable() {
//								@Override 
//								public void run() {
//									//bar.setProgress(counter/100.0);
//								}
//							});
//						}
//					}}).start();
				
				ArrayList<ArrayList<Mat>> a = FileManager.readTrainFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/train.txt"));
				Coach.means = Coach.matToMeans(a);
				int i=0;
				while(i<a.size()){
					Main.messagePane.addNewMessage("A media do descritor "+i + " e " + Coach.means[i][0] + " : " +Coach.means[i][1] );
					System.out.println("A media da class "+i + " e " + Coach.means[i][0] + " : " +Coach.means[i][1] );
					i++;
				}
//				findBestParameters();
			}
			//2 : 0 : 2
		});
		
		Button b2 = new Button("Test");
		b2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println(Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0]);
				
			}
		});
		
		this.getChildren().addAll(addRadioButtom(),addCheckBox(),b,b2);
	}
	
	
	private String findBestParameters(){
		double best = 0;
		String res ="";
		ArrayList<ArrayList<Mat>> images = FileManager.readTrainFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/train.txt"));
		int i=0;
		double accuracy=0.0;
		
		System.out.println("Finding the best parameter...");
		
		while(i<ANGLE.length){
			
			angle = ANGLE[i];
			for(int k1 = 0; k1<FEATURES.length; k1++){
				for(int k2 = 0; k2<FEATURES.length; k2++){
					if(k1 != k2){
						selectedFeatures.clear();
						selectedFeatures.add(k1);
						selectedFeatures.add(k2);
						Coach.means = Coach.matToMeans(images);
						accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
						if(best < accuracy){
							best = accuracy;
							res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
						}
						System.out.println("accuracy: "+ accuracy + "\t\tparameter: angle = " + angle 
								+ ", feature 1 = " + selectedFeatures.get(0) 
								+ ", feature 2 = " + selectedFeatures.get(1) );
					}
				}
			}
			
			i++;
		}
		
		System.out.println("best accuracy: " + best);
		System.out.println("best parameter: " + res);
		
		return res;
	}
	
	private VBox addRadioButtom(){
		
		
		ToggleGroup tg = new ToggleGroup();

		RadioButton r1 = new RadioButton("C1");
		r1.setUserData("1");
		r1.setToggleGroup(tg);
		
		RadioButton r2 = new RadioButton("C2");
		r2.setUserData("2");
		r2.setToggleGroup(tg);

		RadioButton r3 = new RadioButton("C4");
		r3.setUserData("4");
		r3.setToggleGroup(tg);
		
		RadioButton r4 = new RadioButton("C8");
		r4.setUserData("8");
		r4.setToggleGroup(tg);
		
		RadioButton r5 = new RadioButton("C16");
		r5.setUserData("16");
		r5.setToggleGroup(tg);
		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				System.out.println("distancia selecionada e " + tg.getSelectedToggle().getUserData().toString());
				
				angle = Integer.parseInt(tg.getSelectedToggle().getUserData().toString()); 
			}
		});
		
		VBox vbox = new VBox();
		vbox.setPadding(insets_vbox);
		vbox.setSpacing(spacing_vbox);
		vbox.getChildren().addAll(r1,r2,r3,r4,r5);
		return vbox;
	}
	
	private VBox addCheckBox(){
		
		CheckBox contraste = new CheckBox("Contraste");
		contraste.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				
				if(newValue)	selectedFeatures.add(FEATURES[0]);
				else 			selectedFeatures.remove(FEATURES[0]);
				
				if(selectedFeatures.size() > 2){
					Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
		            dialogoAviso.setHeaderText("Por favor, selecione so duas caracteristicas!");
		            dialogoAviso.showAndWait();
				}
				
				for(Integer i: selectedFeatures) System.out.println("caracteristcas selecionadas: "+i);
			}
		});

		CheckBox energy = new CheckBox("Energy");
		energy.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				
				if(newValue)	selectedFeatures.add(FEATURES[1]);
				else 			selectedFeatures.remove(FEATURES[1]);

				if(selectedFeatures.size() > 2){
					Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
		            dialogoAviso.setHeaderText("Por favor, selecione so duas caracteristicas!");
		            dialogoAviso.showAndWait();
				}

				for(Integer i: selectedFeatures) System.out.println("caracteristcas selecionadas: "+i);
				
			}
		});
		
		
		CheckBox entropy = new CheckBox("Entropy");
		entropy.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				
				if(newValue)	selectedFeatures.add(FEATURES[2]);
				else 			selectedFeatures.remove(FEATURES[2]);
				
				if(selectedFeatures.size() > 2){
					Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
		            dialogoAviso.setHeaderText("Por favor, selecione so duas caracteristicas!");
		            dialogoAviso.showAndWait();
				}

				for(Integer i: selectedFeatures) System.out.println("caracteristcas selecionadas: "+i);
			}
		});
		
		CheckBox homogeneidade = new CheckBox("Homogeneidade");
		homogeneidade.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				
				if(newValue)	selectedFeatures.add(FEATURES[3]);
				else 			selectedFeatures.remove(FEATURES[3]);

				if(selectedFeatures.size() > 2){
					Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
		            dialogoAviso.setHeaderText("Por favor, selecione so duas caracteristicas!");
		            dialogoAviso.showAndWait();
				}

				for(Integer i: selectedFeatures) System.out.println("caracteristcas selecionadas: "+i);
			}
		});
		
		VBox vbox = new VBox();
		vbox.setPadding(insets_vbox);
		vbox.setSpacing(spacing_vbox);
		vbox.getChildren().addAll(contraste, energy, entropy, homogeneidade);
		return vbox;
	} 
}
