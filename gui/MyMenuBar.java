package gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MyMenuBar extends MenuBar{

	public MyMenuBar(){
		super();
		
		this.getMenus().addAll(new Menu("file"));
	}
}
