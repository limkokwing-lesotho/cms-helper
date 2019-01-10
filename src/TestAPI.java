import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestAPI {

	final static String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n" +  
			"          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";  
	static Map<String, String> cookies = new HashMap<>();  

	public static void main(String[] args) throws Exception {
		login();

		Connection.Response gradePage = addGrades();
		
		System.out.println(gradePage.parse().html());
	}

	private static Connection.Response addGrades() throws IOException {
		String gradeHomeUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?showmaster=1&ModuleID=1536";
		String gradesFormUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?a=edit&f=A&w=cw3";
		String gradesActionUrl = "https://cmslesotho.limkokwing.net/campus/"
				+ "lecturer/f_breakdownmarksviewlist_new.php?f=A";
		
		Connection.Response gradeHomePage = get(gradeHomeUrl);
		cookies.putAll(gradeHomePage.cookies());
		Connection.Response gradesFormPage = get(gradesFormUrl);
		Document doc = gradesFormPage.parse();

		Element form = doc.getElementById("ff_breakdownmarksviewlist");
		List<String> list = new ArrayList<>(getDefaultParamList(form));
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

		Connection.Response homePage = post(gradesActionUrl, list);
		return homePage;
	}

	private static Connection.Response login() throws IOException {
		String loginUrl = "https://cmslesotho.limkokwing.net/campus/lecturer/login.php";
		Connection.Response loginForm = get(loginUrl);  
		Document loginDoc = loginForm.parse();

		Element form = loginDoc.getElementsByAttributeValue("action", "login.php").first();
		Map<String, String> formData = new HashMap<>();
		formData.putAll(getDefaultParamMap(form));
		formData.put("username", DefaultCredentials.USERNAME);  
		formData.put("password", DefaultCredentials.PASSWORD);

		Connection.Response homePage = post(loginUrl, formData);
		return homePage;
	}

	private static Map<String, String> getDefaultParamMap(Element form) {
		Map<String, String> map = new HashMap<>();
		Elements inputElements = form.getElementsByTag("input");
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			map.put(key, value);
		}
		return map;
	}
	
	private static List<String> getDefaultParamList(Element form) {
		List<String> list = new ArrayList<>();
		Elements inputElements = form.getElementsByTag("input");
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			list.add(key);
			list.add(value);
		}
		return list;
	}
	
	private static Connection.Response get(String loginUrl) throws IOException {
		Connection.Response response = Jsoup.connect(loginUrl)
				.method(Connection.Method.GET)
				.cookies(cookies) 
				.userAgent(USER_AGENT)
				.execute();
		cookies.putAll(response.cookies());
		return response;
	}
	
	private static Connection.Response post(String loginUrl, List<String> formData) throws IOException {
		Connection connection = Jsoup.connect(loginUrl)  
				.cookies(cookies)  
				.data(formData.toArray(new String[0]))  
				.method(Connection.Method.POST)  
				.userAgent(USER_AGENT);
		Connection.Response response =  connection.execute();
		cookies.putAll(response.cookies());
		return response;
	}
	
	private static Connection.Response post(String loginUrl, Map<String, String> formData) throws IOException {
		Connection.Response response = Jsoup.connect(loginUrl)  
				.cookies(cookies)  
				.data(formData)  
				.method(Connection.Method.POST)  
				.userAgent(USER_AGENT)  
				.execute();
		cookies.putAll(response.cookies());
		return response;
	}
}
