package gui;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Mat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

	
	
	
	private final Integer CONTRASTE = new Integer(0), 
			ENERGY = new Integer(1), 
			ENTROPY = new Integer(2), 
			HOMOGENEIDADE = new Integer(3);  
	
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

	
	public FeaturesSelectorAndFunctions(){
		super();

		
		this.setSpacing(10);
		
		Button b = new Button("train");
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				ArrayList<ArrayList<Mat>> a = FileManager.readTrainFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/train.txt"));
//				Coach.means = Coach.matToMeans(a);
//				
//				int i=0;
//				while(i<4){
//					System.out.println("A media da class "+i + " e " + Coach.means[i][0] + " : " +Coach.means[i][1] );
//					i++;
//				}
				findBestParameters();
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
		
		while(i<5){
			
			angle = ANGLE[i];
			selectedFeatures.clear();
			selectedFeatures.add(0);
			selectedFeatures.add(1);
			System.out.println("hello world");
			Coach.means = Coach.matToMeans(images);
			accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
			if(best < accuracy){
				best = accuracy;
				res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
				System.out.println(res);
			}
			
			 
			
			
			selectedFeatures.clear();
			selectedFeatures.add(0);
			selectedFeatures.add(2);
			
			Coach.means = Coach.matToMeans(images);
			accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
			if(best < accuracy){
				best = accuracy;
				res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
				System.out.println(res);
			}
			
			 
			
			
			selectedFeatures.clear();
			selectedFeatures.add(0);
			selectedFeatures.add(3);
			
			Coach.means = Coach.matToMeans(images);
			accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
			if(best < accuracy){
				best = accuracy;
				res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
				System.out.println(res);
			}
			
			 
			
			
			selectedFeatures.clear();
			selectedFeatures.add(1);
			selectedFeatures.add(2);
			
			Coach.means = Coach.matToMeans(images);
			accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
			if(best < accuracy){
				best = accuracy;
				res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
				System.out.println(res);
			}
			
			 
			
			
			selectedFeatures.clear();
			selectedFeatures.add(1);
			selectedFeatures.add(3);
			
			Coach.means = Coach.matToMeans(images);
			accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
			if(best < accuracy){
				best = accuracy;
				res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
				System.out.println(res);
			}
			
			 
			
			
			selectedFeatures.clear();
			selectedFeatures.add(2);
			selectedFeatures.add(3);
			
			Coach.means = Coach.matToMeans(images);
			accuracy =Test.readTestFile(new File("/Users/chendehua/Documents/workspace/tp_PID/test_files/test.txt"),0)[0];
			if(best < accuracy){
				best = accuracy;
				res = angle +" : "+ selectedFeatures.get(0) +" : "+selectedFeatures.get(1);
				System.out.println(res);
			}
			System.out.println("best accuracy: " + best);
			System.out.println("best accuracy: " + res);
			
			 
			
			i++;
		}
		
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
				
				if(newValue)	selectedFeatures.add(CONTRASTE);
				else 			selectedFeatures.remove(CONTRASTE);
				
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
				
				if(newValue)	selectedFeatures.add(ENERGY);
				else 			selectedFeatures.remove(ENERGY);

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
				
				if(newValue)	selectedFeatures.add(ENTROPY);
				else 			selectedFeatures.remove(ENTROPY);
				
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
				
				if(newValue)	selectedFeatures.add(HOMOGENEIDADE);
				else 			selectedFeatures.remove(HOMOGENEIDADE);

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
