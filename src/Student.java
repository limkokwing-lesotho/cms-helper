import java.util.List;
import java.util.Map;

public class Student {

	private String name;
	private String stdNumber;
	private String stdModuleID;
	private String semStatus;
	private Map<String, Double> grades;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStdNumber() {
		return stdNumber;
	}
	public void setStdNumber(String stdNumber) {
		this.stdNumber = stdNumber;
	}
	public String getSemStatus() {
		return semStatus;
	}
	public void setSemStatus(String semStatus) {
		this.semStatus = semStatus;
	}
	public Map<String, Double> getGrades() {
		return grades;
	}
	public void setGrades(Map<String, Double> grades) {
		this.grades = grades;
	}
	public String getStdModuleID() {
		return stdModuleID;
	}
	public void setStdModuleID(String stdModuleID) {
		this.stdModuleID = stdModuleID;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", stdNumber=" + stdNumber + ", stdModuleID=" + stdModuleID + ", semStatus="
				+ semStatus + ", grades=" + grades + "]";
	}
	
}
