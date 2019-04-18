package base;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;

import cucumber.api.java.Before;

public class Hooks {
	public WebDriver driver;

	@Before
	public WebDriver beforeSetup() {
		webdriverconfig obj = new webdriverconfig();
		return obj.intilizeWebDriversetup();
	}

	public WebDriver setup(String browser) throws Exception {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		DOMConfigurator.configure("D:\\BDD\\BDD\\MavenProjectBDD\\Config\\log4j.xml");
		return driver;
		
	}

	/*
	 * @After public WebDriver afterScenario(Scenario scenario) { if
	 * (scenario.isFailed()) {
	 * 
	 * File sourcePath = ((TakesScreenshot)
	 * driver).getScreenshotAs(OutputType.FILE); try { File destinationPath =new
	 * File(
	 * "D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\Screenshot\\ScreenShotfail.png"
	 * ); FileUtils.copyFile(sourcePath, destinationPath);
	 * Reporter.addScreenCaptureFromPath(destinationPath.toString()); } catch
	 * (IOException e) { } } return driver; }
	 * 
	 * @ After(order=1) public WebDriver tearDown(){ if(driver== null) {
	 * driver.quit(); } return driver;
	 * 
	 * }
	 */
}
