package luct.gradebook;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GradebookTable extends TableView<Student> {

	public ObservableList<Student> list = FXCollections.observableArrayList(StudentDAO.get());
	
	public GradebookTable() {
		getColumns().addAll(createColumns());
		setItems(list);
	}
	
	public static List<TableColumn<Student, ?>> createColumns(){
		List<TableColumn<Student, ?>> cols = new ArrayList<>();
		TableColumn<Student, String> name = new TableColumn<Student, String>("Names");
		name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		TableColumn<Student, String> stdNumber = new TableColumn<Student, String>("Student Number");
		stdNumber.setCellValueFactory(new PropertyValueFactory<Student, String>("stdNumber"));
		TableColumn<Student, String> stdModuleID = new TableColumn<Student, String>("Module ID");
		stdModuleID.setCellValueFactory(new PropertyValueFactory<Student, String>("stdModuleID"));
		TableColumn<Student, String> semStatus = new TableColumn<Student, String>("Status");
		semStatus.setCellValueFactory(new PropertyValueFactory<Student, String>("semStatus"));
		
		cols.add(name);
		cols.add(stdNumber);
		cols.add(stdModuleID);
		cols.add(semStatus);
		
		return cols;
	}

}
