import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import luct.gradebook.GradebookTable;


public class Main extends Application {
	
	BorderPane root = new BorderPane();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scene = new Scene(root,600,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			root.setLeft(createNav());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private VBox createNav() {
		Button gradesBtn = new Button("Grades");
		gradesBtn.setMinWidth(100);
		gradesBtn.setMinHeight(100);
		Button studentBtn = new Button("Students");
		studentBtn.setMinWidth(100);
		studentBtn.setMinHeight(100);
		
		gradesBtn.setOnAction(e ->{
			root.setCenter(new HBox());
		});
		GradebookTable students = new GradebookTable();
		studentBtn.setOnAction(e ->{
			root.setCenter(students);
		});
		
		return new VBox(gradesBtn, studentBtn);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
