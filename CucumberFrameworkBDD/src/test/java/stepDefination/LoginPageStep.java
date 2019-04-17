package stepDefination;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import hooks.Hooks;

public class LoginPageStep{
	WebDriver driver;
	 
		public LoginPageStep(Hooks hook) {
			hook.beforeSetup();
		//	hook.close_browser();
		}
		
	//LoginPage login=new LoginPage(driver);
	@Given("^user already on Login Page$")
	public void user_already_on_login_page(){	
	//driver.getApplicationUrl() ;
		
	}
@When("^title login page HMS")
public void title_of_login_page_is_HMS(){
	
	String title = driver.getTitle();
	System.out.println(title);
	Assert.assertEquals("User Login Page", title);
}
@Then("^user enter \"(.*)\" and \"(.*)\"$")
public void user_enters_username_and_password(String username, String password){
	driver.findElement(By.name("username")).sendKeys(username);
	driver.findElement(By.name("password")).sendKeys(password);
}
/*@Then("^user enters username and password$")
public void user_enters_username_and_password(DataTable credentials) {
	for (Map<String, String> data : credentials.asMaps(String.class, String.class)) {
	
		driver.findElement(By.name("username")).sendKeys(data.get("username"));
		driver.findElement(By.name("password")).sendKeys(data.get("password"));
		
	}*/
//	}
@Then("^user clicks login button$")
public void user_clicks_on_login_button() {
	WebElement loginBtn =
	driver.findElement(By.xpath("//input[@value='Login']"));
	JavascriptExecutor js = (JavascriptExecutor)driver;
	js.executeScript("arguments[0].click();", loginBtn);
}
@Then("^user clicks registration links$")
public void user_clicks_registration_link() {
driver.findElement(By.linkText("Registration")).click();
}




}
