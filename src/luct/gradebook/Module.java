package luct.gradebook;

import javax.persistence.Entity;
import javax.persistence.Id;

import javafx.beans.property.SimpleStringProperty;

@Entity
public class Module {

	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty code;
	
	public Module() {
		id = new SimpleStringProperty();
		name = new SimpleStringProperty();
		code = new SimpleStringProperty();
	}
	
	public SimpleStringProperty idProperty() {
		return this.id;
	}
	@Id
	public String getId() {
		return this.idProperty().get();
	}
	
	public void setId(final String id) {
		this.idProperty().set(id);
	}
	
	public SimpleStringProperty nameProperty() {
		return this.name;
	}
	
	public String getName() {
		return this.nameProperty().get();
	}
	
	public void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	public SimpleStringProperty codeProperty() {
		return this.code;
	}
	
	public String getCode() {
		return this.codeProperty().get();
	}
	
	public void setCode(final String code) {
		this.codeProperty().set(code);
	}
	
	@Override
	public String toString() {
		return name.get() +" ("+code.get()+") ";
	}
}
