package utility;

	import org.openqa.selenium.*;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.interactions.SendKeysAction;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;

	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Calendar;
	import java.util.Date;
	import java.util.Set;
	import utility.WebDriverWrapper;
	public class WebPage {	
		protected WebDriver driver;
		protected WebDriverWrapper webDriverWrapper;
		protected String pageName;

		public WebPage(WebDriver driver, String pageName) {
			this.driver = driver;
			this.pageName = pageName;
			webDriverWrapper = new WebDriverWrapper(driver);
		}
		public WebPage(){

		}
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

		protected void sendKeys (PageElement pageElement, String value) {
			try {
				value = (value == null) ? "" : value;

				if (!isWebElementAvailableInPageElement(pageElement))
					getWebElement(pageElement).sendKeys(value);
				else
					pageElement.getWebElement().sendKeys(value);
			} catch (Exception exception) { 
				throw new ScriptExecutionException ("Failed to type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to press : '" + key + "' in " + pageElement.getName() + " on : '" + pageName , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to clear and type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to clear and type value: '" + value + "' in " + pageElement.getName() + " on : '" + pageName , exception);
			} finally {
				pageElement = null;
			}
		}
			protected void Scrolldownslidebar(PageElement pageElement) {
				try {
					WebElement element;
					if (!isWebElementAvailableInPageElement(pageElement))
						element = getWebElement(pageElement);
					else
						element = pageElement.getWebElement();
					
					Actions scroll = new Actions(driver);
				scroll.dragAndDropBy(element, 0, 90).build().perform();
				} catch (Exception exception) {
					throw new ScriptExecutionException ("Failed to Scroll down '' in " + pageElement.getName() + " on : '" + pageName , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName , exception);
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
				throw new ScriptExecutionException ("Failed to click on : '" + pageElement.getName() + "' on : '" + pageName , exception);
			} finally {
				pageElement = null;
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
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to doubleclick: '" + "' on " + pageElement.getName() + " on : '" + pageName, exception);
			} finally {
				pageElement = null;
			}
		}

		protected String getText(PageElement pageElement) {
			String text = new String();
			try {
				WebElement webElement;
				if (!isWebElementAvailableInPageElement(pageElement))
					webElement = getWebElement(pageElement);
				else
					webElement = pageElement.getWebElement();

				text = webElement.getText().trim();
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName , exception);
			} finally {
				pageElement = null;
			}
			return text;
		}


		protected boolean isElementDisplayed(PageElement pageElement) {
			boolean isElementDisplayed = false;
			try {
				WebElement webElement;
				if (!isWebElementAvailableInPageElement(pageElement))
					webElement = getWebElement(pageElement);
				else
					webElement = pageElement.getWebElement();

				isElementDisplayed = webElement.isDisplayed();

			} catch (Exception e) {
				throw new ScriptExecutionException ("Failed to display: '" + pageElement.getName() + " on : '" + pageName , e);
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
			} catch (Exception e) {
				
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
			} catch (Exception e) {
				
			} finally {
				pageElement = null;
			}
			return isElementEnabled;
		}

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
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to Select value: '" + value + "' of " + pageElement.getName() + " on : '" + pageName , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to check: '" + pageElement.getName() + " on : '" + pageName , exception);
			} finally {
				pageElement = null;
			}
		}
		protected String genericLocatorforPrevPageDetails(String title)
		{
			String xpath=null;
			xpath="//td[contains(text(),'"+title+"')]/following::td";
			return xpath;
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
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() , exception);
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
			} catch (Exception exception) {
				throw new ScriptExecutionException("Failed to fetch text: '" + "' of " + pageElement.getName() + " on : '" + pageName , exception);
			} finally {
				pageElement = null;
			}
			return text;
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
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to clear  in " + pageElement.getName() + " on : '" + pageName ,exception);
			} finally {
				pageElement = null;
			}

		}
		protected void switchToFrame(String frameName){
			try {
				Thread.sleep(1000);
				driver.switchTo().defaultContent();
				Thread.sleep(1000);
				String xpathForFrame="//frameset//frame[@name='"+frameName+"']";
				WebElement frame=driver.findElement(By.xpath(xpathForFrame));
				driver.switchTo().frame(frame);
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to switch to Frame: '" + frameName + "' in on : '" + pageName, exception);
			} finally {
				frameName=null;
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
				throw new ScriptExecutionException ("Failed to clear  in "+ pageName ,exception);
			} finally {
				frameName=null;
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

		public void click(PageElement pageElement,WebDriver driver){

			try {
				WebElement element;
				if (!isWebElementAvailableInPageElement(pageElement))
					element = getWebElement(pageElement);
				else
					element = pageElement.getWebElement();
				element.click();
			} catch (Exception exception) {
				throw new ScriptExecutionException ("Failed to clear  in " + pageElement.getName() + " on : '" + pageName ,exception);
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
		public void switchToJSPPage() throws InterruptedException{
			for(String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				driver.manage().window().maximize();
				System.out.println(winHandle);
				Thread.sleep(1000);
				switchToDefaultFrame();
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
		protected void clearAndSendAutoPopulateText(PageElement pageElement,String text){
			try{
				Actions actions=new Actions(driver);
				clearAndSendKeys(pageElement, text);
				actions.sendKeys(Keys.TAB).build().perform();
			}catch(ElementNotVisibleException e){
				e.printStackTrace();
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
		public void highlightTheElement(WebElement element) {
			try{
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid yellow'", element);

			}catch(Exception e){
				Log.warn("could not highlight the element "+element.toString());
			}

		}
		protected void acceptAlertAndReturnConfirmationMessages() throws InterruptedException {
			try {
				Thread.sleep(3000);
				String confirmationMessage = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();
			} catch (NoAlertPresentException exception) {
				
			}
		}
		public void waitFor(int timeunit) {
			try {
				Thread.sleep(timeunit*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
				throw new ScriptExecutionException ("Failed to clear  in " + pageElement.getName() + " on : '" + pageName ,exception);
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
				throw new ScriptExecutionException ("Failed to clear  in " + pageElement.getName() + " on : '" + pageName ,exception);
			} finally {
				pageElement = null;
			}
		}
		
	}
