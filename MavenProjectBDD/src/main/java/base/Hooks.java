package base;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;



public class Hooks {
 public WebDriver driver;
	@Before
	public WebDriver beforeSetup() {
		if(driver==null) {
	System.setProperty("webdriver.chrome.driver","D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\driver\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	driver.manage().window().maximize();
	DOMConfigurator.configure("D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\Config\\log4j.xml");
			}
		return driver;
	}
	/*@After
	 public WebDriver afterScenario(Scenario scenario) {
	 if (scenario.isFailed()) {
	
	 File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	 try {
		 File destinationPath =new File("D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\Screenshot\\ScreenShotfail.png");
		 FileUtils.copyFile(sourcePath, destinationPath);  
		 Reporter.addScreenCaptureFromPath(destinationPath.toString());
	 } catch (IOException e) {
	 } 
	 }
	return driver;
	 }
	@ After(order=1)
	public WebDriver tearDown(){
        if(driver== null) {	
        	 driver.quit();
       }
		return driver; 	      
	
		}*/
}