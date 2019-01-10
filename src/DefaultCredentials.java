

import java.io.IOException;
import java.util.Properties;

public class DefaultCredentials {

	public static String USERNAME;
	public static String PASSWORD;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(DefaultCredentials.class.getResourceAsStream("default_credentials"));
			USERNAME = prop.getProperty("username");
			PASSWORD = prop.getProperty("password");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
