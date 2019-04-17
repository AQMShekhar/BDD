package hooks;

import cucumber.TestContext;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	TestContext testContext;
	 
	 public Hooks(TestContext context) {
	 testContext = context;
	 }
	@Before
	public void beforeSetup() {
		
		
	}
	@After
	public void close_browser(){
		 testContext.getWebDriverManager().quitDriver();
		// driver.close();
		
	 }
	
}