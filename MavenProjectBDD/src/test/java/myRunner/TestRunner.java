package myRunner;
import java.io.File;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;


	@CucumberOptions(
	        features = ("D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\src\\main\\resources\\features"),
	        glue = {"stepdefination"},
	        tags = {"@Important"},
	       plugin = {"pretty","html:target/cucumber-reports/report.html", 
	    		   "com.cucumber.listener.ExtentCucumberFormatter:D:target/cucumber-reports/Extentreport.html",
	    		   "json:target/cucumber-reports/report.json"
	        		},
	       monochrome = true
			)

	public class TestRunner {
	    private TestNGCucumberRunner testNGCucumberRunner;
	 	    @BeforeClass(alwaysRun = true)
	    public void setUpClass() throws Exception {
	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	    }
	 
	    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	    public void feature(CucumberFeatureWrapper cucumberFeature) {
	        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	    }
	 
	    @DataProvider
	    public Object[][] features() {
	        return testNGCucumberRunner.provideFeatures();
	    }
	 
	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() throws Exception {
	    Reporter.loadXMLConfig(new File("D:\\Users\\Temp\\git\\TestGit\\MavenProjectBDD\\Config\\extent-config.xml"));
	    Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	    Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	    Reporter.setSystemInfo("Machine", "Windows 10, " + "64 Bit");
	    Reporter.setSystemInfo("Selenium", "3.11.0");
	    Reporter.setSystemInfo("Maven", "3.5.2");
	    Reporter.setSystemInfo("Java Version", "1.8.0_121");
	    testNGCucumberRunner.finish();
	    
	    }
	   
	}
	   
	    
