import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import luct.db.DAO;
import luct.db.HibernateHelper;
import luct.gradebook.ReadModuleService;
import luct.gradebook.Session;
import luct.gradebook.Student;

public class TestAPI {

	public static void main(String[] args) throws Exception {
		HibernateHelper.getSession();
		
		Session.login();
		
//		List<Student> students = readStudents();
//		DAO<Student> dao = new DAO<>(Student.class);
//		for (Student student : students) {
//			dao.save(student);
//		}
		
		
//		System.out.println(gradePage.parse().html());
	}

	private static Connection.Response addGrades() throws IOException {
		String gradeHomeUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?showmaster=1&ModuleID=1536&RecPerPage=500";
		String gradesFormUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?a=edit&f=A&w=cw3";
		String gradesActionUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?f=A";
		
		Connection.Response gradeHomePage = Session.get(gradeHomeUrl);
		Session.cookies.putAll(gradeHomePage.cookies());
		Connection.Response gradesFormPage = Session.get(gradesFormUrl);
		Document doc = gradesFormPage.parse();

		Element form = doc.getElementById("ff_breakdownmarksviewlist");
		List<String> list = new ArrayList<>(Session.getDefaultParamList(form));
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			String item = it.next();
			if(item.equals("x_CW3[]")) {
				it.remove();
				it.next();
				it.remove();
			}
		}
		list.addAll(Arrays.asList("x_CW3[]", "91","x_CW3[]", "92"));

		Connection.Response homePage = Session.post(gradesActionUrl, list);
		return homePage;
	}

	private static List<Student> readStudents(){
		String gradeHomeUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?showmaster=1&ModuleID=2899&RecPerPage=500";
		Response page = Session.get(gradeHomeUrl);
		List<Student> list = new ArrayList<>();
		try {
			Document doc = page.parse();
			Element table = doc.getElementById("ewlistmain");
			for(Element tr: table.getElementsByTag("tr")) {
				Element stdModuleID = tr.getElementById("x_StdModuleID");
				if(stdModuleID != null) {
					Elements data = tr.getElementsByTag("td");
					Student student = new Student();
					student.setStdModuleID(stdModuleID.val());
					student.setName(data.get(3).text());
					student.setStdNumber(data.get(4).text());
					student.setSemStatus(data.get(6).text());
					list.add(student);
					System.out.println(student);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	

}
