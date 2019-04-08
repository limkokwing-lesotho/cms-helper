package luct.gradebook;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class StudentsPane extends BorderPane {

	private ComboBox<String> module = new ComboBox<>();
	private ComboBox<String> className = new ComboBox<>();
	
	public StudentsPane() {
		module.setPromptText("Module");
		className.setPromptText("Class Name");
		ToolBar toolBar = new ToolBar(module, className);
		
		setTop(toolBar);
	}
}
