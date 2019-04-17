package base;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.Before;

public class TestBase {
	public WebDriver driver;
	
	public void beforeSetup() {
		//if(driver==null) {
	System.setProperty("webdriver.chrome.driver","D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\driver\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	driver.get("http://selenium4testing.com/hms/");
	driver.manage().window().maximize();
	//DOMConfigurator.configure("D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\Config\\log4j.xml");
			}
		
//	}
}
