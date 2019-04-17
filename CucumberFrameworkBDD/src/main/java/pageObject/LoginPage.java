package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
		@FindBy(how = How.NAME, using = "username") 
		 WebElement username;

		@FindBy(how = How.NAME, using = "password") 
		WebElement password;

		@FindBy(how = How.NAME, using = "//input[@value='Login']") 
	    WebElement loginButton;





		public void enter_UserName(String userName) {
			username.sendKeys(userName);
		}

		public void enter_Password(String Password) {
			password.sendKeys(Password);
		}

		public void loginBTN() {
			loginButton.click();
		}

		








		/*	 
		 public void fill_PersonalDetails() {
		 enter_Name("Aotomation");
		 enter_LastName("Test");
		 enter_Phone("0000000000");
		 enter_Email("Automation@gmail.com");
		 select_Country("India");
		 enter_City("Delhi");
		 enter_Address("Shalimar Bagh");
		 enter_PostCode("110088");
		 select_County("Delhi");

		 }*/
	}
