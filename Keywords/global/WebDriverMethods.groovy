package global

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.Keys
import global.Action as gAct
import global.Object as gObj
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import internal.GlobalVariable as GVars
import org.openqa.selenium.support.ui.ExpectedConditions

public class WebDriverMethods {

	@Keyword
	static def getObjectAttribute(String object, String attribute){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		String attrText = driver.findElement(By.xpath(object)).getAttribute(attribute)
		return attrText
	}

	@Keyword
	static def setEditText(String object, String text){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		driver.findElement(By.xpath(object)).sendKeys(text)
	}



	@Keyword
	static def clickElement(String object){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(object)))
		driver.findElement(By.xpath(object)).click()
	}


	@Keyword
	static def dblClickElement(String object){
		WebDriver driver = DriverFactory.getWebDriver()
		Actions action = new Actions(driver)
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		WebElement link = driver.findElement(By.xpath(object))
		action.doubleClick(link).perform()
	}


	@Keyword
	static def getElementText(String object){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		String elementText = driver.findElement(By.xpath(object)).getText()
		return elementText
	}


	@Keyword
	static def selectComboboxItem(String object, String item){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath(object)))
		select.selectByValue(item)
	}


	@Keyword
	static def selectComboboxItemByText(String object, String item){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath(object)))
		select.selectByVisibleText(item)
	}


	@Keyword
	static def listObjectChildObjects(String object){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		WebElement elem = driver.findElement(By.xpath(object));
		List<WebElement> childs = elem.findElements(By.xpath("./child::*"));
		return childs
	}


	@Keyword
	static def listObject(String object){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		List<WebElement> elem = driver.findElements(By.xpath(object))
		return elem
	}


	@Keyword
	static def clickSpecificElementFromList(List<WebElement> list, int position){
		list[position].click()
		gAct.Wait(1)
	}


	@Keyword
	static def getSpecificElementTextFromList(List<WebElement> list, int position){
		String text = list[position].getText()
		return text
	}



	@Keyword
	static def switchToiFrameAndEditObject(String iFrame, String object, String text){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().frame(driver.findElement(By.xpath(iFrame)))
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		driver.findElement(By.xpath(object)).clear()
		driver.findElement(By.xpath(object)).sendKeys(text)
	}


	@Keyword
	static def switchToiFrameAndClick(String iFrame, String object){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().frame(driver.findElement(By.xpath(iFrame)))
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		driver.findElement(By.xpath(object)).click()
	}


	@Keyword
	static def switchToiFrameAndGetText(String iFrame, String object){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().frame(driver.findElement(By.xpath(iFrame)))
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)))
		String text = driver.findElement(By.xpath(object)).getText()
		return text
	}


	@Keyword
	static def getTableRowCount(String xpathValue){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement Table = driver.findElement(By.xpath(xpathValue))
		List<WebElement> rows_table = Table.findElements(By.tagName('tr'))
		int rows_count = rows_table.size()
		return rows_count
	}


	@Keyword
	static def getTableRowCountOniFrame(String iFrame, String xpathValue){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().frame(driver.findElement(By.xpath(iFrame)))
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement Table = driver.findElement(By.xpath(xpathValue))
		List<WebElement> rows_table = Table.findElements(By.tagName('tr'))
		int rows_count = rows_table.size()
		return rows_count
	}


	@Keyword
	static def getTableRowCountByObject(String objectName){
		String xpathValue = gObj.getObjectRepositoryIdentifierValue(objectName, 'xpath')
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement Table = driver.findElement(By.xpath(xpathValue))
		List<WebElement> rows_table = Table.findElements(By.tagName('tr'))
		int rows_count = rows_table.size()
		return rows_count
	}


	@Keyword
	static def getTableColumnCount(String xpathValue){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement Table = driver.findElement(By.xpath(xpathValue))
		List<WebElement> col_table = Table.findElements(By.tagName('th'))
		int col_count = col_table.size()
		return col_count
	}


	@Keyword
	static def getAttributeValueByPosition(String classNme, String attrNme, int position){
		WebDriver driver = DriverFactory.getWebDriver()
		def lists1 = driver.findElements(By.className(classNme));
		def value = lists1[position].getAttribute(attrNme)
		return value
	}


	@Keyword
	static def elementPresent(String element){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)))
		boolean exists = driver.findElement(By.xpath(element))
		return exists
	}


	@Keyword
	static def getComboBoxLabelInFrame(String xpathValue, int frameIndex){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().frame(frameIndex)
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		Select comboBox = new Select(driver.findElement(By.xpath(xpathValue)));
		String selectedComboValue = comboBox.getFirstSelectedOption().getText();
		driver.switchTo().defaultContent()

		return selectedComboValue
	}


	@Keyword
	static def getCheckboxValueUsingJavaScript(String xpathValue){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement element = driver.findElement(By.xpath(xpathValue));
		boolean isChecked = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", element);
	}


	@Keyword
	static def clickUsingJavaScript(String xpathValue){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement  element = driver.findElement(By.xpath(xpathValue));
		JavascriptExecutor ex = (JavascriptExecutor)driver;
		ex.executeScript("arguments[0].click()", element);

		//		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathValue)));
		//		element.click();
	}


	@Keyword
	static def setTextJavaScript(String xpathValue, String text){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement  element = driver.findElement(By.xpath(xpathValue));
		JavascriptExecutor ex = (JavascriptExecutor)driver;
		ex.executeScript("arguments[0].setAttribute('value', '" + text +"')", element);
	}


	@Keyword
	static def runJavaScript(String scriptValue){
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver;
		def value = js.executeScript(scriptValue);
		return value
	}


	@Keyword
	static def checkOrUncheckBox(String objectName, boolean check){
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver;
		def value = js.executeScript("\$('" + objectName + "').prop('checked', " + check + ")");
	}


	@Keyword
	static def getCheckBoxState(String xpathValue){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement  element = driver.findElement(By.xpath(xpathValue));
		def attribute = element.getAttribute("checked")
		return attribute
	}


	@Keyword
	static def setCheckBoxState(String xpathValue, boolean state){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, GVars.midWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)))
		WebElement element = driver.findElement(By.xpath(xpathValue));

		boolean chkState = getCheckBoxState(xpathValue)
		if(chkState != state){
			clickElement(xpathValue)
		}
	}


	@Keyword
	static def switchToActive(){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().activeElement()
	}


	@Keyword
	static def clickText(String text){
		WebDriver driver = DriverFactory.getWebDriver()
		driver.findElement(By.linkText(text)).click()
	}
}
