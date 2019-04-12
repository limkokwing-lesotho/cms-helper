package luct.gradebook;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Access(AccessType.PROPERTY)
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

	
	public SimpleStringProperty nameProperty() {
		return this.name;
	}
	public String getName() {
		return this.nameProperty().get();
	}
	public void setName( String name) {
		this.nameProperty().set(name);
	}
	public SimpleStringProperty stdNumberProperty() {
		return this.stdNumber;
	}
	@Id
	public String getStdNumber() {
		return this.stdNumberProperty().get();
	}
	public void setStdNumber( String stdNumber) {
		this.stdNumberProperty().set(stdNumber);
	}
	public SimpleStringProperty stdModuleIDProperty() {
		return this.stdModuleID;
	}
	public String getStdModuleID() {
		return this.stdModuleIDProperty().get();
	}
	public void setStdModuleID( String stdModuleID) {
		this.stdModuleIDProperty().set(stdModuleID);
	}
	public SimpleStringProperty semStatusProperty() {
		return this.semStatus;
	}
	public String getSemStatus() {
		return this.semStatusProperty().get();
	}
	public void setSemStatus( String semStatus) {
		this.semStatusProperty().set(semStatus);
	}
	@Transient
	public SimpleMapProperty<String, Double> getGrades() {
		return grades;
	}
	public void setGrades(SimpleMapProperty<String, Double> grades) {
		this.grades = grades;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", stdNumber=" + stdNumber + ", stdModuleID=" + stdModuleID + ", semStatus="
				+ semStatus + ", grades=" + grades + "]";
	}
}
