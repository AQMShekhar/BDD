package base;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class webdriverconfig {
	public Properties p;
	public WebDriver intilizeWebDriversetup() {
		WebDriver driver = null;
		Properties p = null;
		try {
			FileReader reader = new FileReader("D:\\BDD\\BDD\\MavenProjectBDD\\configs\\config.properties");
			p = new Properties();
			p.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String browsername = p.getProperty("browser");
		if (browsername.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", p.getProperty("driverPath.chrome"));
			driver = new ChromeDriver();
		}
		if (browsername.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", p.getProperty("driverPath.firefox"));
			driver = new FirefoxDriver();

		}
		if (browsername.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", p.getProperty("driverPath.ie"));
			driver = new InternetExplorerDriver();
		}
		return driver;
	
	}
	
}
