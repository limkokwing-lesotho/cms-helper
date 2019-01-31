package luct.gradebook;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

	public static List<Student> get(){
		List<Student> list = new ArrayList<>();
		
		
		Student std1 = new Student();
		std1.setName("Thabo Lebese");
		std1.setSemStatus("Active");
		std1.setStdNumber("901000001");
		std1.setStdModuleID("PROG101");
		list.add(std1);
		
		return list;
	}
}
