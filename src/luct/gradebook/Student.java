package luct.gradebook;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

	private SimpleStringProperty name;
	private SimpleStringProperty stdNumber;
	private SimpleStringProperty stdModuleID;
	private SimpleStringProperty semStatus;
	private SimpleMapProperty<String, Double> grades;
	
	public Student() {
		name = new SimpleStringProperty();
		stdNumber = new SimpleStringProperty();
		stdModuleID = new SimpleStringProperty();
		semStatus = new SimpleStringProperty();
		grades = new SimpleMapProperty<>();
	}
	public final SimpleStringProperty nameProperty() {
		return this.name;
	}
	public final String getName() {
		return this.nameProperty().get();
	}
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	public final SimpleStringProperty stdNumberProperty() {
		return this.stdNumber;
	}
	public final String getStdNumber() {
		return this.stdNumberProperty().get();
	}
	public final void setStdNumber(final String stdNumber) {
		this.stdNumberProperty().set(stdNumber);
	}
	public final SimpleStringProperty stdModuleIDProperty() {
		return this.stdModuleID;
	}
	public final String getStdModuleID() {
		return this.stdModuleIDProperty().get();
	}
	public final void setStdModuleID(final String stdModuleID) {
		this.stdModuleIDProperty().set(stdModuleID);
	}
	public final SimpleStringProperty semStatusProperty() {
		return this.semStatus;
	}
	public final String getSemStatus() {
		return this.semStatusProperty().get();
	}
	public final void setSemStatus(final String semStatus) {
		this.semStatusProperty().set(semStatus);
	}
	public SimpleMapProperty<String, Double> getGrades() {
		return grades;
	}
	public void setGrades(SimpleMapProperty<String, Double> grades) {
		this.grades = grades;
	}
}
