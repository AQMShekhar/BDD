package stepdefination;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import base.Hooks;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utility.ExternalData;


public class RegistrationTest{
	WebDriver driver;
	private Scenario scenario;
	ExternalData data;

	public RegistrationTest(Hooks hook) {
		driver=hook.beforeSetup();
		
		}
	@Before(order=1)
	public void beforeScenario(Scenario scenario) throws IOException{
		this.scenario = scenario;
		data = new ExternalData(scenario.getName());
	} 
	/*@Given("^user already on Login pages$")
	public void user_already_on_login_page(){	
	driver.get("http://selenium4testing.com");
		
	}*/
   @When("^title login page is HMS")
   public void title_of_login_page_is_HMS(){
	String title = driver.getTitle();
	System.out.println(title);
	Assert.assertEquals("User Login Page", title);
}
	
	@Then("^user enter \"(.*)\" and \"(.*)\"$")
	public void user_enters_username_and_password(String username, String password){
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
}
	
	/*@Then("^user enter \"(.*)\" and \"(.*)\"$")
	public void user_Enters_username_and_password(String username, String password){
		driver.findElement(By.name("username")).sendKeys(data.currnData("SecondSheet", "username"));
		driver.findElement(By.name("password")).sendKeys(data.currnData("SecondSheet", "password"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();
	}*/

	
	@Then("^user clicks registration link$")
	public void user_clicks_registration_link() {
	driver.findElement(By.linkText("Registration")).click();
	
	
	}
	
	
	
	@Then("^fill the registration form \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void fill_the_registration_form(String firstname, String lastname, String dob, String age,String panNo, String address1, String mobileno, String zipcode) {
		Select select =new Select(driver.findElement(By.name("PATIENT_CAT")));
		select.selectByVisibleText("Self");
		Select select1 =new Select(driver.findElement(By.name("TITLE")));
		select1.selectByVisibleText("Mr.");
		driver.findElement(By.name("PNT_NAME")).sendKeys(firstname);
		driver.findElement(By.name("LAST_NAME")).sendKeys(lastname);
	    driver.findElement(By.name("DOB")).sendKeys(dob);
	    driver.findElement(By.name("AGE")).sendKeys(age);
	    Select select2 =new Select(driver.findElement(By.name("SEX")));
	    select2.selectByVisibleText("Male");
		Select select3 =new Select(driver.findElement(By.name("MTRL_STATUS")));
		select3.selectByVisibleText("Single");
		Select select4 =new Select(driver.findElement(By.name("RELIGION")));
		select4.selectByVisibleText("Hindu");
		Select select5 =new Select(driver.findElement(By.name("PLANGUAGE")));
		select5.selectByVisibleText("English");
		Select select6 =new Select(driver.findElement(By.name("RELATION")));
		select6.selectByVisibleText("Brother");
		Select select7 =new Select(driver.findElement(By.name("PAT_IDENTITY")));
		select7.selectByVisibleText("PAN Card");
		driver.findElement(By.name("PAT_IDENTITY_PROOF")).sendKeys(panNo);
		Select select8 =new Select(driver.findElement(By.name("NATIONALITY")));
		select8.selectByVisibleText("Indian");
		Select select9 =new Select(driver.findElement(By.name("IS_MLC")));
		select9.selectByVisibleText("No");
		Select select10 =new Select(driver.findElement(By.name("EDUCATION")));
		select10.selectByVisibleText("MCA");
		Select select11 =new Select(driver.findElement(By.name("OCCUPATION")));
		select11.selectByVisibleText("Employee");
		Select select12 =new Select(driver.findElement(By.name("BLOOD_GRP_CODE")));
		select12.selectByVisibleText("A+");
		Select select13 =new Select(driver.findElement(By.name("CITIZENSHIP")));
		select13.selectByVisibleText("Indian");
		Select select14 =new Select(driver.findElement(By.name("SC_PROOF")));
		select14.selectByVisibleText("No");
		
		driver.findElement(By.name("ADDRESS1")).sendKeys(address1);
		driver.findElement(By.name("MOBILE_NO")).sendKeys(mobileno);
		Select select15 =new Select(driver.findElement(By.name("COUNTRY_CODE")));
		select15.selectByVisibleText("India");
		driver.findElement(By.name("ZIP")).sendKeys(zipcode);
		//driver.findElement(By.name("submit")).click();
		
		}
/*	
	@Then("^close the browser$")
	public void closeBrowser() {
		driver.quit();
	}*/
}
