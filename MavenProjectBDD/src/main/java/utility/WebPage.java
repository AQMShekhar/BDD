package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Hooks;
import net.masterthought.cucumber.json.Hook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public abstract class WebPage extends Hooks{
	protected WebDriver driver;
	protected WebDriverWrapper webDriverWrapper;
	protected String pageName;

	public WebPage(WebDriver driver, String pageName) {
		this.driver = driver;
		this.pageName = pageName;
		webDriverWrapper = new WebDriverWrapper(driver);
	}
	
	///////////////////////////////For Page Elements/////////////////////////////////////////////////
	
	private WebElement waitForElementAndReturnElement(PageElement pageElement) {
		switch (pageElement.getWaitType()) {
		case WAITFORELEMENTTOBECLICKABLE:
			try {
				return webDriverWrapper.waitForElementToBeClickable(pageElement.getBy(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				throw new ScriptExecutionException (pageElement.getName() + " not loaded within specified wait time of "+ pageElement.getTimeOut(), e);
			}

		case WAITFORELEMENTTOBEEENABLED:
			try {
				return webDriverWrapper.waitForElementToBeEnabled(pageElement.getBy(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				throw new ScriptExecutionException (pageElement.getName() + " was disabled beyond specified wait time "+ pageElement.getTimeOut(), e);
			}

		case WAITFORELEMENTTOBEDISPLAYED:
			try {
				return webDriverWrapper.waitForElementToBeDisplayed(pageElement.getBy(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				throw new ScriptExecutionException (pageElement.getName() + " was not displayed in specified wait time "+ pageElement.getTimeOut(), e);
			}

		default:
			return driver.findElement(pageElement.getBy());
		}
	}

	protected void waitForPageElement(PageElement pageElement) {
		boolean isWebElementAvailableInPageElement = isWebElementAvailableInPageElement(pageElement);
		switch (pageElement.getWaitType()) {
		case WAITFORELEMENTTOBECLICKABLE:
			try {
				if (!isWebElementAvailableInPageElement)
					webDriverWrapper.waitForElementToBeClickable(pageElement.getBy(), pageElement.getTimeOut());
				else
					webDriverWrapper.waitForElementToBeClickable(pageElement.getWebElement(), pageElement.getTimeOut());

			} catch (TimeoutException e) {
				throw new ScriptExecutionException (pageElement.getName() + " was not clickable within specified wait time "+ pageElement.getTimeOut(), e);
			}
			break;

		case WAITFORELEMENTTOBEEENABLED:
			try {
				if (!isWebElementAvailableInPageElement)
					webDriverWrapper.waitForElementToBeEnabled(pageElement.getBy(), pageElement.getTimeOut());
				else
					webDriverWrapper.waitForElementToBeEnabled(pageElement.getWebElement(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				throw new ScriptExecutionException (pageElement.getName() + " was not loaded within specified wait time "+ pageElement.getTimeOut(), e);
			}
			break;

		case WAITFORELEMENTTOBEDISPLAYED:
			try {
				if (!isWebElementAvailableInPageElement)
					webDriverWrapper.waitForElementToBeDisplayed(pageElement.getBy(), pageElement.getTimeOut());
				else
					webDriverWrapper.waitForElementToBeDisplayed(pageElement.getWebElement(), pageElement.getTimeOut());

			} catch (TimeoutException e) {
				throw new ScriptExecutionException (pageElement.getName() + " was not displayed within specified wait time "+ pageElement.getTimeOut(), e);
			}
			break;
			
		default:
			break;
		}
	}

	private boolean isWebElementAvailableInPageElement(PageElement pageElement) {
		return !(pageElement.getWebElement() == null);
	}

	protected WebElement getWebElement(PageElement pageElement) {
		if (pageElement.isSlowLoadableComponent()) {
			return waitForElementAndReturnElement(pageElement);
		} else
			return driver.findElement(pageElement.getBy());
	}
	
///////////////////////////////For Key-Board Related Action/////////////////////////////////////////////////
	
	protected void sendKeys (PageElement pageElement, String value) {
		try {
			value = (value == null) ? "" : value;

			if (!isWebElementAvailableInPageElement(pageElement))
				getWebElement(pageElement).sendKeys(value);
			else
				pageElement.getWebElement().sendKeys(value);

			Log.info("Typed Value: " + value + "' in " + pageElement.getName());
		} catch (Exception exception) { 
			throw new ScriptExecutionException ("Failed to type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected void sendKeys (PageElement pageElement, Keys key) {
		try {
			if (!isWebElementAvailableInPageElement(pageElement))
				getWebElement(pageElement).sendKeys(key);
			else
				pageElement.getWebElement().sendKeys(key);

			Log.info("Typed Value: " + key + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to press : '" + key + "' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected void clearAndSendKeys (PageElement pageElement, String value) {
		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			highlightTheElement(element);
			element.clear();
			element.sendKeys(value);
			Log.info("Cleared and Typed Value: " + value + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected void SelectValueAndTypeForTextField (PageElement pageElement, String value) {
		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();

			element.click();
			Actions actionBuilder = new Actions(driver);
			actionBuilder.doubleClick(element).build().perform();
			element.sendKeys(Keys.SHIFT ,Keys.END);
			element.sendKeys(value);
			Log.info("Cleared and Typed Value: " + value + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName , exception);
		} finally {
			pageElement = null;
		}
	}
	protected void SelectValueAndTypeForHomeTextField (PageElement pageElement, String value) {
		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();

			element.click();
			element.sendKeys(Keys.SHIFT ,Keys.HOME);
			element.sendKeys(value);
			Log.info("Cleared and Typed Value: " + value + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void clearAndEnterDate(PageElement pageElement,String date)
	{
		try {
			String[] splitddate=date.split("/");
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			element.clear();
			for(int i=0;i<splitddate.length;i++){
				Thread.sleep(500);
				element.sendKeys(splitddate[i]);
				Thread.sleep(500);
			}
			Log.info("Cleared and Typed Value: " + date + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '" + date + "' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}

	}
	
	protected void enterMessageInAlert(String message) {
		try {
			Alert alert=driver.switchTo().alert();
			String alertMessage=driver.switchTo().alert().getText();
			alert.sendKeys(message);
			alert.accept();
			Log.info("fetched text from Alert: " + alertMessage + " from alert box and entered"+message);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("failed to accept alert on " + pageName, exception);
		}
	}
	
	protected void clearAndEnterTime(PageElement pageElement){
		try {
			RandomCodeGenerator randomCodeGenerator=new RandomCodeGenerator();
			String time=randomCodeGenerator.timeGenerator();
			String[] splitTime=time.split(":");
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			element.clear();
			for(int i=0;i<splitTime.length;i++){
				Thread.sleep(300);
				element.sendKeys(splitTime[i]);
				Thread.sleep(300);
			}
			Log.info("Cleared and Typed Value: " + time + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void clearTextField (PageElement pageElement) {
		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();

			element.clear();
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to Cleared  in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	protected void clearAndEnterForwardTime(PageElement pageElement,String timeOfIntimation){
		try {
			String time=RandomCodeGenerator.timeGeneratorForClaimAdmission(timeOfIntimation);
			String[] splitTime=time.split(":");
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			element.clear();
			for(int i=0;i<splitTime.length;i++){
				Thread.sleep(300);
				element.sendKeys(splitTime[i]);
				Thread.sleep(300);
			}
			Log.info("Cleared and Typed Value: " + time + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '' in " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void clearAndSendAutoPopulateText(PageElement pageElement,String text){
		try{
			Actions actions=new Actions(driver);
			clearAndSendKeys(pageElement, text);
			actions.sendKeys(Keys.TAB).build().perform();
		}catch(ElementNotVisibleException e){
			e.printStackTrace();
		}
	}
	
	public static String dateGenerator(String dateToBeAdded){
		String exp=null;
		String getPortalFormatDate=null;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		cal.setTime(date);
		if(dateToBeAdded.contains("oldDate:")) {
			String[]day=dateToBeAdded.split(":");
			String daytoBeadded=day[1];
			int dayToBeadded=Integer.parseInt(daytoBeadded);	
			cal.add(Calendar.DATE, -dayToBeadded); // add 10 days
			date = cal.getTime();
			exp= dateFormat.format(date).toString();
			getPortalFormatDate=exp.replace("/","");
		}
		else if(dateToBeAdded.contains("futureDate:")) {
			String[]day=dateToBeAdded.split(":");
			String daytoBeadded=day[1];
			int dayToBeadded=Integer.parseInt(daytoBeadded);	
			cal.add(Calendar.DATE,dayToBeadded); // add 10 days
			date = cal.getTime();
			exp= dateFormat.format(date).toString();
			getPortalFormatDate=exp.replace("/","");
		}
		else if(dateToBeAdded.contains("now")) {
			exp= dateFormat.format(date).toString();
			getPortalFormatDate=exp.replace("/","");
		}
		return getPortalFormatDate;
	}
	
	protected void acceptAlertAndReturnConfirmationMessages() throws InterruptedException {
		try {
			Thread.sleep(3000);
			String confirmationMessage = driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException exception) {

		}
	}
	
	public void clickAndIncreaseValueButton(PageElement pageElement,PageElement pageElement1,Double value) {
		try {
			String text = new String();
			WebElement element,element1;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			
			if (!isWebElementAvailableInPageElement(pageElement1))
				element1 = getWebElement(pageElement1);
			else
				element1= pageElement.getWebElement();
			text = element1.getAttribute("value").trim();
			highlightTheElement(element1);
			Float actualValue1=Float.valueOf(text);
			for(Float i=actualValue1;i<value;) { 
				element.click();
				text=element1.getAttribute("value");
				actualValue1=Float.valueOf(text);
				i=actualValue1;
			}
			
		}
		catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName, exception);
		}
	}
	public void clickAndDecreaseValueButton(PageElement pageElement,PageElement pageElement1,Double value) {
		try {
			String text = new String();
			WebElement element,element1;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			
			if (!isWebElementAvailableInPageElement(pageElement1))
				element1 = getWebElement(pageElement1);
			else
				element1= pageElement.getWebElement();
			text = element1.getAttribute("value").trim();
			highlightTheElement(element1);
			Float actualValue1=Float.valueOf(text);
			for(Float i=actualValue1;i>value;) { 
				element.click();
				text=element1.getAttribute("value");
				actualValue1=Float.valueOf(text);
				i=actualValue1;
			}
			
		}
		catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName, exception);
		}
	}
	protected void clearAndSendKeysWithTab (PageElement pageElement, String value) {
		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			highlightTheElement(element);
			element.clear();
			element.sendKeys(value);
			Thread.sleep(5000);
			element.sendKeys(Keys.TAB);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear and type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName , exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void clearKeys (PageElement pageElement) {
		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			highlightTheElement(element);
			element.clear();
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to clear  in " + pageElement.getName() + " on : '" + pageName , exception);
		} finally {
			pageElement = null;
		}
	}
/////////////////////////////For Mouse Related Action/////////////////////////////////////////////////
		
	protected void Scrolldownslidebar(PageElement pageElement) {
			try {
				WebElement element;
				if (!isWebElementAvailableInPageElement(pageElement))
					element = getWebElement(pageElement);
				else
					element = pageElement.getWebElement();
				
				Actions scroll = new Actions(driver);
			scroll.dragAndDropBy(element, 0, 90).build().perform();
			Log.info("Scroll down ' in " + pageElement.getName());
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to Scroll down '' in " + pageElement.getName() + " on : '" + pageName, exception);
			} finally {
				pageElement = null;
			}
		}
	
	protected void click(PageElement pageElement) {
		try {	
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			((JavascriptExecutor)driver).executeScript("window.confirm = function(msg) { return true; }");
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			Log.info("Clicked on: " + pageElement.getName());
			Thread.sleep(1000);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void clickPopup(PageElement pageElement) {
		try {	
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();

			((JavascriptExecutor)driver).executeScript("window.confirm = function(msg) { return true; }");
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			highlightTheElement(element);
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void click1(PageElement pageElement) {
		try {	
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			element.click();
			Log.info("Clicked on: " + pageElement.getName());
			Thread.sleep(3000);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected void check(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			if (!webElement.isSelected())
				webElement.click();

			Log.info("Checked  " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to check: '" + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected void unCheck(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			if (webElement.isSelected())
				webElement.click();

			Log.info("Unchecked  " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to uncheck: '" + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected String acceptAlertAndReturnConfirmationMessage() {
		try {
			Thread.sleep(3000);
			String confirmationMessage = driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();

			Log.info("fetched text: " + confirmationMessage + " from alert box and accepted");
			return confirmationMessage;
		} catch (Exception exception) {
			throw new ScriptExecutionException ("failed to accept alert on " + pageName, exception);
		}
	}

	protected String dismissAlertAndReturnConfirmationMessage() {
		try {
			String confirmationMessage = driver.switchTo().alert().getText();
			driver.switchTo().alert().dismiss();

			Log.info("fetched text: " + confirmationMessage + " from alert box and accepted");
			return confirmationMessage;
		} catch (Exception exception) {
			throw new ScriptExecutionException ("failed to dismiss alert on " + pageName, exception);
		}
	}

	protected void doubleClick(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			Actions actionBuilder = new Actions(driver);
			actionBuilder.doubleClick(webElement).build().perform();

			Log.info("Double Clicked on: " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to doubleclick: '" + "' on " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void mouseOver(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			Actions actionBuilder = new Actions(driver);
			actionBuilder.moveToElement(webElement).build().perform();

			Log.info("Hoverd mouse on: " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to hover mouse: '" + "' on " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void checkOrUncheck(PageElement pageElement,Boolean config) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();
			highlightTheElement(webElement);
			if (!webElement.isSelected()&&config.equals(true)){
				webElement.click();
			}
			else if(webElement.isSelected()&&config.equals(false)){
				webElement.click();
			}
			Log.info("Checked  " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to check: '" + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	public void click(PageElement pageElement,WebDriver driver){

		try {
			WebElement element;
			if (!isWebElementAvailableInPageElement(pageElement))
				element = getWebElement(pageElement);
			else
				element = pageElement.getWebElement();
			element.click();

			Log.info("Clicked on: " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName , exception);
		} finally {
			pageElement = null;
		}

	}

/////////////////////////////For Fetching Values From Application/////////////////////////////////////////////////
	
	protected String getText(PageElement pageElement) {
		String text = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			text = webElement.getText().trim();

			Log.info("Fetched text: " + text + " of " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
		return text;
	}

	protected String getAttribute(PageElement pageElement, String attributeName) {
		String atributeValue = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			atributeValue = webElement.getAttribute(attributeName).trim();

			Log.info("Fetched " + attributeName + ":" + atributeValue + " of " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch '" + attributeName + "' of " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
		return atributeValue;
	}
	
	protected String fetchValueFromFields(PageElement pageElement) {
		String text = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();
				highlightTheElement(webElement);
			text = webElement.getText().trim();

			Log.info("Fetched text: " + text + " of " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
		return text;
	}
	
	protected String fetchValueFromTextFields(PageElement pageElement) {
		String text = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();
			highlightTheElement(webElement);
			text = webElement.getAttribute("value").trim();

			Log.info("Fetched text: " + text + " of " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName , exception);
		} finally {
			pageElement = null;
		}
		return text;
	}
	
	protected String fetchValueFromList(PageElement pageElement){
		String text = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();
			Select select=new Select(webElement);
			highlightTheElement(webElement);
			text=	select.getFirstSelectedOption().getText();

			Log.info("Fetched text: " + text + " of " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
		return text;
	}
	
	protected String getSelectedValueFromList(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			Select selectType = new Select(webElement);
			String selectedValue = selectType.getFirstSelectedOption().getText();

			Log.info("Fetched " + selectedValue + "  " + " of " + pageElement.getName());
			return selectedValue;
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch "+ "Selected Value" + "' of " + pageElement.getName()+ " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}

	protected ArrayList<String> getAllOptionsInList(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			Select selectList = new Select(webElement);
			ArrayList<String> optionList = new ArrayList<String>();

			for (WebElement option : selectList.getOptions()) {
				optionList.add(option.getText().trim());
			}

			Log.info("Fetched " + optionList + "  " + " of " + pageElement.getName());
			return optionList;
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to fetch " + "option Value" + "' of " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}


/////////////////////////////For Checking Element is there or Not/////////////////////////////////////////////////
	protected boolean isElementDisplayed(PageElement pageElement) {
		boolean isElementDisplayed = false;
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			isElementDisplayed = webElement.isDisplayed();

			Log.info("<B>"+pageElement.getName() + " is Displayed </B>");

		} catch (Exception e) {
			throw new ScriptExecutionException ("Failed to display: '" + pageElement.getName() + " on : '" + pageName, e);
		} finally {
			pageElement = null;
		}
		return isElementDisplayed;
	}

	protected boolean isElementSelected(PageElement pageElement) {
		boolean isElementDisplayed = false;
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			isElementDisplayed = webElement.isSelected();

			Log.info(pageElement.getName() + " is Displayed ");
		} catch (Exception e) {
			Log.info(pageElement.getName() + " is not Displayed ");
		} finally {
			pageElement = null;
		}
		return isElementDisplayed;
	}

	protected boolean isElementEnabled(PageElement pageElement) {
		boolean isElementEnabled = false;
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			isElementEnabled = webElement.isEnabled();

			Log.info(pageElement.getName() + " is Enabled ");
		} catch (Exception e) {
			Log.info(pageElement.getName() + " is not Enabled ");
		} finally {
			pageElement = null;
		}
		return isElementEnabled;
	}
	
	protected void bringElementInView (PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);

			Log.info("Brought  " + pageElement.getName() + "  " + " in the current browser view ");
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to bring Element  " + pageElement.getName() + "  " + " in the current browser view " + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	public boolean isAlertPresent(){
		WebDriverWait wait = new WebDriverWait(driver, 3 );
		if(wait.until(ExpectedConditions.alertIsPresent())==null){
			return true;
		}
		else{
			driver.switchTo().alert().accept();
			return false;

		}
	}
	protected void bringWebElementInView (PageElement pageElement) {
		try {
			WebElement webElement = getWebElement(pageElement);
			if (!isWebElementAvailableInPageElement(pageElement))
				((JavascriptExecutor) driver).executeAsyncScript("arguments[0].scrollIntoView(true);", webElement);
			Log.info("Brought  " + pageElement.getName() + "  " + " in the current browser view ");
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to bring Element  " + pageElement.getName() + "  " + " in the current browser view " + " on : '" + pageName , exception);
		} finally {
			pageElement = null;
		}
	}
	
	public  boolean isConfigTrue(String config){
		if(config.equalsIgnoreCase("yes")){
			return true;
		}
		else{
			return false;
		}
	}
	
	protected boolean isWebElementDisplayed(PageElement pageElement){
		try{
			WebElement element;
			element=driver.findElement(pageElement.getBy());
			if(element.isDisplayed()){
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			return false;
		}
	}
	
	protected boolean waitForElement(PageElement pageElement,int timeInMilliSeconds){
		try{
			WebDriverWait wait = new WebDriverWait(driver,timeInMilliSeconds);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(pageElement.getBy())));
			return true;
		}catch(ElementNotVisibleException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void highlightTheElement(WebElement element) {
		try{
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid yellow'", element);

		}catch(Exception e){
			Log.warn("could not highlight the element "+element.toString());
		}

	}
	public void waitFor(int timeunit) {
		try {
			Thread.sleep(timeunit*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
/////////////////////////////For Dropdown Selection Action/////////////////////////////////////////////////
	
	protected void selectValueFromList(PageElement pageElement, String value) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();
			Select select = new Select(webElement);
			select.selectByVisibleText(value);
			highlightTheElement(webElement);

			Log.info("Selected Value: " + value + "' in " + pageElement.getName());
		} catch (Exception exception) {
			throw new ScriptExecutionException("Failed to Select value: '" + value + "' of " + pageElement.getName() + " on : '" + pageName, exception);
		} finally {
			pageElement = null;
		}
	}
	
	protected void selectValueFromIndex(PageElement pageElement) {
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();
			Select select = new Select(webElement);
			select.selectByIndex(2);
		} catch (Exception exception) {
				} finally {
			pageElement = null;
		}
	}
	
	protected void selectDateFromCalender(PageElement pageElement,String dateToBeAdded) throws InterruptedException{
		String date=null;
		RandomCodeGenerator randomCodeGenerator=new RandomCodeGenerator();
		date=randomCodeGenerator.calenderDateGenerator(dateToBeAdded);
		click(pageElement);
		String[] splitddate=date.split("/");
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		switchToWindow("IIMS: Calendar");
		driver.switchTo().defaultContent();
		driver.manage().window().maximize();
		Thread.sleep(1000);
		WebElement year=driver.findElement(By.name("year"));
		year.clear();
		acceptAlertAndReturnConfirmationMessage();
		String yearVal = splitddate[2].trim();
		year.sendKeys(yearVal);
		Thread.sleep(200);
		WebElement month=driver.findElement(By.name("month"));
		month.click();
		Thread.sleep(200);
		Select select = new Select(month);
		select.selectByVisibleText(splitddate[1].trim());
		Thread.sleep(200);
		WebElement dayOfMonth=driver.findElement(By.xpath("//input[@value='"+splitddate[0].trim()+"']"));
		dayOfMonth.click();
		Thread.sleep(200);
		switchToWindow();
	}
	
/////////////////////////////For Switching Tab,Window,Frame And Close window/////////////////////////////////////////////////
	protected void switchToFrame(String frameName){
		try {
			Thread.sleep(1000);
			driver.switchTo().defaultContent();
			Thread.sleep(1000);
			String xpathForFrame="//frameset//frame[@name='"+frameName+"']";
			WebElement frame=driver.findElement(By.xpath(xpathForFrame));
			driver.switchTo().frame(frame);
			Log.info("Switch to frame " + frameName);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to switch to Frame: '" + frameName + "' in on : '" + pageName, exception);
		} finally {
			frameName=null;
		}
	}
	
	protected void switchToWindow(String windowTitle) throws InterruptedException {
		try{
			Thread.sleep(1000);
			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {
				driver.switchTo().window(window);
				driver.manage().window().maximize();
				if (driver.getTitle().contains(windowTitle))
					break;	
			}
		}

		catch(Exception e){
			throw new ScriptExecutionException ("Failed to search Criteria: ", e);
		}
	}

	protected void switchToWindow(String windowTitle, String errorTitleWindow) throws InterruptedException 
	{
		try{
			Thread.sleep(1000);
			ArrayList<String> alltitles = new ArrayList<String>();
			Set<String> windows = driver.getWindowHandles();


			for (String window : windows) {
				driver.switchTo().window(window);
				driver.manage().window().maximize();
				alltitles.add(driver.getTitle());
			}
			if (alltitles.contains(errorTitleWindow)){
				throw new ScriptExecutionException ("Error Window Encountered While Execution.");
			}
			else if(alltitles.contains(windowTitle)){
				switchToWindow(windowTitle);

			}else {
				throw new ScriptExecutionException ("Expected page: "+windowTitle+ " NOT FOUND ");
			}
		}
		catch(Exception e){
			throw new ScriptExecutionException ("Failed to search Criteria: ", e);
		}
	}

	protected void closeWindow(String windowTitle) throws InterruptedException {
		try {
			Thread.sleep(1000);
			driver.close();
		}
		catch (Exception exception) {
			throw new ScriptExecutionException("Failed to Close the Window with title \"" + windowTitle + " \"", exception);
		}
	}
	
	protected void switchToFrame(String frameName,WebDriver driver){
		try {
			Thread.sleep(1000);
			driver.switchTo().defaultContent();
			Thread.sleep(500);
			String xpathForFrame="//frameset//frame[@name='"+frameName+"']";
			WebElement frame=driver.findElement(By.xpath(xpathForFrame));
			driver.switchTo().frame(frame);
		} catch (Exception exception) {
			throw new ScriptExecutionException ("Failed to switch to Frame: '" + frameName + "' in on : '" + pageName , exception);
		} finally {
			frameName=null;
		}
	}
	protected void switchForSearchCriteria(PageElement pageElement,String searchFieldLocator,String searchCriteria,String frameName) throws InterruptedException{
		try {
			click(pageElement);
			Thread.sleep(500);
			switchToWindow();
			PageElement searchCriteriaTextField=new PageElement(By.name(searchFieldLocator), "search FieldLocator Textfield", false, WaitType.WAITFORELEMENTTOBEDISPLAYED, 10);
			clearAndSendKeys(searchCriteriaTextField, searchCriteria);
			Thread.sleep(500);
			PageElement findButton=new PageElement(By.xpath("//input[@name='"+searchFieldLocator+"']/following::a['firstFocus']"), "Party Code Textfield", false, WaitType.WAITFORELEMENTTOBECLICKABLE, 10);
			click(findButton);
			Thread.sleep(500);
			PageElement searchCriteriaLink=new PageElement(By.linkText(searchCriteria), ""+searchCriteria+"Link", false, WaitType.WAITFORELEMENTTOBECLICKABLE, 10);
			click(searchCriteriaLink);
			switchToWindow();
			switchToFrame(frameName);
		} catch (Exception e) {
			throw new ScriptExecutionException ("Failed to search Criteria: ", e);
		}

	}
	protected void switchToWindow() throws InterruptedException {
		String title = null;

		try{
			Thread.sleep(1000);
			Set<String> parentWindow = driver.getWindowHandles();


			for(String winHandle : driver.getWindowHandles()) {
				if (!parentWindow.equals(winHandle)) {
					title=	driver.switchTo().window(winHandle).getTitle();
					driver.manage().window().maximize();
					Dimension dimension2=driver.manage().window().getSize();
					int heightAfterSwitch=dimension2.getHeight();
					int widthAfterSwitch=dimension2.getWidth();
					if(heightAfterSwitch==1000 && widthAfterSwitch==1296 ){
						break;
					}
					Thread.sleep(1000);
				}
			}
		}
		catch(Exception e){
			throw new ScriptExecutionException ("Failed to Switch window: ", e);
		}
	}
	protected void switchToDefaultFrame() throws InterruptedException {
		String title = null;
		try{
			driver.switchTo().defaultContent();
		}
		catch(Exception e){
			throw new ScriptExecutionException ("Failed to search Criteria: ", e);
		}
	}
	
	public void switchToJSPPage() throws InterruptedException{
		for(String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.manage().window().maximize();
			System.out.println(winHandle);
			Thread.sleep(1000);
			switchToDefaultFrame();
		}
	}
	
	protected void switchToMainPage(){
		String windowTitle="Integrated Insurance Management System";
		try{

			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {

				while(!driver.getTitle().contains(windowTitle)){
					closeWindow();
					switchToWindow();
				}
				switchToWindow(windowTitle);
			}
		}
		catch(Exception e){
			throw new ScriptExecutionException ("Failed to Switch"+windowTitle+" window ", e);
		}

	}
	protected void closeWindow() throws InterruptedException {
		try {
			driver.wait(500);
			driver.close();
		}
		catch (Exception exception) {
			throw new ScriptExecutionException("Failed to Close the Window  ", exception);
		}
	}
	protected void switchAndCloseWindow(){
		try {
			String parentWindow= driver.getWindowHandle();
			String winHandleBefore = driver.getWindowHandle();

			for(String winHandle : driver.getWindowHandles()){
				driver.switchTo().window(winHandle);
			}
			driver.close();
			driver.switchTo().window(parentWindow);
		}catch(Exception e){

		}
	}
	
	protected void switchToTab(){
		try{
			ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs2.get(0));
		    driver.close();
		    driver.switchTo().window(tabs2.get(1));
		}catch(Exception e){
			throw new ScriptExecutionException ("Failed to search Criteria: ", e);
		}
	}
	protected void switchToWindow1(String windowTitle) throws InterruptedException {
		try{
			Thread.sleep(1000);
			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {
				driver.switchTo().window(window);
				driver.manage().window().maximize();
				if (driver.getTitle().contains(windowTitle))
					break;	
			}
		}
		catch(Exception e){
			throw new ScriptExecutionException ("Failed to search Criteria: ", e);
		}
	}
/////////////////////////////For Locators Xpaths/////////////////////////////////////////////////
	
	protected String locatorforQuestionAnswerPanel(String title, String type) { 
		String xpath=null;
		if(type.equalsIgnoreCase("input")) {
			xpath="//td//div[@id='"+title+"']/following::"+type+"[1]";
		}
		else if(type.equalsIgnoreCase("select")) {
			xpath="//td//div[@id='"+title+"']/following::"+type+"[1]";
		}
		else if(type.equalsIgnoreCase("textarea")) {
			xpath="//td//div[@id='"+title+"']/following::"+type+"[1]";
		}
		return xpath;
	}
	protected String locatorforPanel(String title, String type) {
		String xpath=null;
		if(type.equalsIgnoreCase("input")) {
			xpath="//td//b[contains(text(),'"+title+"')]/following::"+type;
		}
		else if(type.equalsIgnoreCase("select")) {
			xpath="//td//b[contains(text(),'"+title+"')]/following::"+type;
		}
		else if(type.equalsIgnoreCase("textarea")) {
			xpath="//td//b[contains(text(),'"+title+"')]/following::"+type;
		}
		return xpath;
	}

	protected String genericLocatorforLabel(String formName, String title) {
		String xpath=null;
		xpath="//form[@name='"+formName+"']//td//b[contains(text(),'"+title+"')]/following::td";
		return xpath;
	}
	
	protected String genericLocatorforPrevPageDetails(String title)
	{
		String xpath=null;
		xpath="//td[contains(text(),'"+title+"')]/following::td";
		return xpath;
	}	
}



