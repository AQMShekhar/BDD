package stepdefination;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.cucumber.listener.Reporter;
import base.Hooks;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utility.Log;

public class LoginTest {
	 WebDriver driver;
	
	public LoginTest(Hooks hook) {
		driver=hook.beforeSetup();
		
			}
	
		@Given("^user already on Login Page$")
		public void user_already_on_login_page(){	
		driver.get("http://selenium4testing.com/hms/");
		Log.info("Lunching the Url");
		
		}
	@When("^title login page HMS")
	public void title_of_login_page_is_HMS(){
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals("User Login Page", title);
		Log.info("Verify the Title");
	}
	
	@Then("^user enters username and password$")
	public void user_enters_username_and_password(DataTable credentials) {
		for (Map<String, String> data : credentials.asMaps(String.class, String.class)) {
			driver.findElement(By.name("username")).sendKeys(data.get("username"));
			driver.findElement(By.name("password")).sendKeys(data.get("password"));
			
		}
		}
	@Then("^user clicks login button$")
	public void user_clicks_on_login_button() {
		WebElement loginBtn =
		driver.findElement(By.xpath("//input[@value='Login']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", loginBtn);
		 Log.warn("User is on home page");
	}
	@Then("^user clicks registration links$")
	public void user_clicks_registration_link() {
	driver.findElement(By.linkText("Registration")).click();
	
	}
	@After
	 public void afterScenario(Scenario scenario) {
	 if (scenario.isFailed()) {
	
	 File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	 try {
		 File destinationPath =new File("D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\Screenshot\\ScreenShotfail.png");
	     FileUtils.copyFile(sourcePath, destinationPath);   
		 Reporter.addScreenCaptureFromPath(destinationPath.toString());
	 } catch (IOException e) {
	 } 
	 }}
	
	 
	@After(order=1)
	public void closeBrowser(){
		
	      driver.quit();
		
	}
	
		}
	
	
	
	
	


