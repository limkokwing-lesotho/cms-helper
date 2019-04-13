package luct.gradebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import luct.DefaultCredentials;

public class Session {

	public final static String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n" +  
			"          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";  
	public static final Map<String, String> cookies = new HashMap<>();
	private static Response homePage;
	
	public static Connection.Response login() throws IOException {
		if (homePage == null) {
			String loginUrl = "https://cmslesotho.limkokwing.net/campus/lecturer/login.php";
			Connection.Response loginForm = get(loginUrl);
			Document loginDoc = loginForm.parse();
			Element form = loginDoc.getElementsByAttributeValue("action", "login.php").first();
			Map<String, String> formData = new HashMap<>();
			formData.putAll(getDefaultParamMap(form));
			formData.put("username", DefaultCredentials.USERNAME);
			formData.put("password", DefaultCredentials.PASSWORD);
			homePage = post(loginUrl, formData);
		}
		return homePage;
	}

	public static Map<String, String> getDefaultParamMap(Element form) {
		Map<String, String> map = new HashMap<>();
		Elements inputElements = form.getElementsByTag("input");
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			map.put(key, value);
		}
		return map;
	}
	
	public static List<String> getDefaultParamList(Element form) {
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
	
	public static Connection.Response get(String url) {
		Connection.Response response = null;
		try {
			response = Jsoup.connect(url)
					.method(Connection.Method.GET)
					.cookies(cookies) 
					.userAgent(USER_AGENT)
					.execute();
			cookies.putAll(response.cookies());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static Connection.Response post(String url, List<String> formData) {
		Connection connection = Jsoup.connect(url)  
				.cookies(cookies)  
				.data(formData.toArray(new String[0]))  
				.method(Connection.Method.POST)  
				.userAgent(USER_AGENT);
		Connection.Response response = null;
		try {
			response = connection.execute();
			cookies.putAll(response.cookies());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static Connection.Response post(String url, Map<String, String> formData) {
		Connection.Response response = null;
		try {
			response = Jsoup.connect(url)  
					.cookies(cookies)  
					.data(formData)  
					.method(Connection.Method.POST)  
					.userAgent(USER_AGENT)  
					.execute();
			cookies.putAll(response.cookies());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
