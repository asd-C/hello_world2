package gui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MessagePane extends BorderPane{
	private TextArea ta;
	
	public MessagePane(){
		super();
		
		ta = new TextArea();
		
		ta.setEditable(false);
		ta.resize(getWidth(), getHeight());
		
		this.setCenter(ta);
	}
	
	public void addNewMessage(String s){
		ta.setText(ta.getText() + s + "\n");
	}
	
}

