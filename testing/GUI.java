package testing;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application{

	public static void main(String[] args){
		launch(args);
		
		//Input_buf.saveFile("hello");
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Stage stage;
		
		stage = primaryStage;
		
		FlowPane root = new FlowPane();
		
		Button b = new Button("file");
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				chooseFile();
			}
		});
		
		root.getChildren().add(b);
		
		Scene scene = new Scene(root, 400, 300);
		
		stage.setTitle("Testing");
		stage.setScene(scene);
		stage.show();
	}
	
	public File chooseFile(){
		File res = null;
		
		FileChooser fc = new FileChooser();
		res = fc.showOpenDialog(null);
		
		return res;
	}
	

}
