package luct.gradebook;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class ClassesPane extends BorderPane {

	private ComboBox<Module> module = new ComboBox<>();
	private ComboBox<String> className = new ComboBox<>();
	
	public ClassesPane() {
		module.setPromptText("Module");
		className.setPromptText("Class Name");
		ToolBar toolBar = new ToolBar(module, className);
		
		ReadModuleService rm = new ReadModuleService();
		new Thread(rm).start();
		rm.setOnSucceeded( ev->{
			module.getItems().addAll(rm.getValue());
		});
		
		setTop(toolBar);
	}
}
