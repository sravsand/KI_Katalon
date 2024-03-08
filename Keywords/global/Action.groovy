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
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.edge.EdgeDriver as EdgeDriver
import org.openqa.selenium.ie.InternetExplorerDriver as IEDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.ie.InternetExplorerOptions as IEOptions

import org.apache.commons.io.FileUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.junit.After

import java.io.File
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.DataFlavor
import java.awt.Toolkit
import java.text.SimpleDateFormat
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys

import internal.GlobalVariable as GVars
import global.Report as Rep
import global.Action as Act
import global.Common as Com
import java.io.IOException;
import java.nio.file.*

public class Action {

	@Keyword
	static def openBrowserOnUrlAndMaximise(String url){
		WebUI.openBrowser(url)
		WebUI.waitForPageLoad(GVars.shortWait)

		Com.edgeSync()
		WebUI.maximizeWindow()

		String browser = Com.getBrowserType()

		if(browser == "IE_DRIVER"){
			WebUI.navigateToUrl(url)
			WebUI.waitForPageLoad(GVars.shortWait)
			//Act.Wait(5)
		}
	}


	@Keyword
	static def openBrowserAtUrlMaximiseChromedriver(String url, String downloadPath){
		def tester = System.getProperty('user.name')
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath())
		ChromeDriver driver = new ChromeDriver(cap);
		DriverFactory.changeWebDriver(driver)

		WebUI.navigateToUrl(url)
		WebUI.maximizeWindow()
	}


	@Keyword
	static def openBrowserAtUrlMaximiseFirefoxdriver(String url, String downloadPath){
		FirefoxProfile profile = new FirefoxProfile()
		profile.setPreference("browser.download.dir", downloadPath)
		profile.setPreference("browser.download.downloadDir", downloadPath)
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
		profile.setPreference("browser.download.manager.useWindow", false)
		profile.setPreference("browser.download.manager.showWhenStarting", false)
		profile.setPreference("browser.helperApps.neverAsk.force", false)
		profile.setPreference("browser.download.manager.focusWhenStarting", false)
		profile.setPreference("browser.download.folderList", 2)
		profile.setPreference("browser.download.defaultFolder", downloadPath)
		profile.setPreference("browser.download.forbid_open_with", true)
		profile.setPreference("browser.download.useDownloadDir", true)
		profile.setPreference("profile.default_content_settings.popups", 0)
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")

		FirefoxOptions options = new FirefoxOptions().setProfile(profile)
		System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverPath())

		WebDriver driver = new FirefoxDriver(options);
		DriverFactory.changeWebDriver(driver)

		WebUI.navigateToUrl(url)
		WebUI.maximizeWindow()
	}


	@Keyword
	static def openBrowserAtUrlMaximiseEdgedriver(String url, String downloadPath){
		EdgeOptions options = new EdgeOptions()
		options.setCapability("download.default_directory", downloadPath)
		System.setProperty("webdriver.edge.driver", DriverFactory.getEdgeDriverPath())

		WebDriver driver = new EdgeDriver(options);
		DriverFactory.changeWebDriver(driver)

		WebUI.navigateToUrl(url)
		WebUI.maximizeWindow()
	}


	@Keyword
	static def openBrowserAtUrlMaximiseIEdriver(String url, String downloadPath){
		IEOptions options = new IEOptions()
		options.setCapability("download.default_directory", downloadPath)
		options.setCapability("profile.default_content_settings.popups", 0)
		options.setCapability("download.prompt_for_download", false)
		System.setProperty("webdriver.ie.driver", DriverFactory.getIEDriverPath())
		WebDriver driver = new IEDriver(options);
		DriverFactory.changeWebDriver(driver)

		WebUI.navigateToUrl(url)
		WebUI.maximizeWindow()
	}


	@Keyword
	static def Wait(int waitTime = GVars.midWait){
		int waitInMilliseconds = waitTime * 1000
		sleep(waitInMilliseconds)
	}

	
	@Keyword
	static def milliWait(int waitInMilliseconds = 3){
		sleep(waitInMilliseconds)
	}
	

	@Keyword
	static def compareStringAndReport(String firstString, String secondString){
		if(firstString == secondString){
			Rep.pass(firstString + " matches as expected.")
		}else{
			Rep.fail(firstString + " and " + secondString + " do not match.")
		}
	}


	@Keyword
	static def cleanUpCompareStringAndReport(String firstString, String secondString){
		String newFirst = firstString.replaceAll("\n", "")
		String newSecond = secondString.replaceAll("\n", "")
		newFirst = newFirst.replaceAll(" ", "")
		newSecond = newSecond.replaceAll(" ", "")

		if(newFirst == newSecond){
			Rep.pass(newFirst + " matches as expected.")
		}else{
			Rep.fail(newFirst + " and " + newSecond + " do not match.")
		}
	}


	@Keyword
	static def comparefloatAndReport(float firstFloat, float secondFloat){
		if(firstFloat == secondFloat){
			Rep.pass(firstFloat + " matches as expected.")
		}else{
			Rep.fail(firstFloat + " and " + secondFloat + " do not match.")
		}
	}


	@Keyword
	static def compareIntAndReport(int firstInt, int secondInt){
		if(firstInt == secondInt){
			Rep.pass(firstInt + " matches as expected.")
		}else{
			Rep.fail(firstInt + " and " + secondInt + " do not match.")
		}
	}


	@Keyword
	static def compareBooleanAndReport(boolean firstBool, boolean secondBool){
		if(firstBool == secondBool){
			Rep.pass("Both values are : " + firstBool)
		}else{
			Rep.fail("Value 1 is " + firstBool +  " and value 2 is " + secondBool)
		}
	}


	@Keyword
	static def findSubstringInStringAndReport(String mainString, String subString){
		if(mainString.contains(subString)){
			Rep.pass(mainString + " contains " + subString + " as expected.")
		}
		else{
			Rep.fail(mainString + " does not contain " + subString + " as expected.")
		}
	}



	@Keyword
	static def cleanUpAndfindSubstringInStringAndReport(String main, String sub){
		String mainString = main.replaceAll("\n", "")
		String subString = sub.replaceAll("\n", "")

		mainString = mainString.replaceAll(" ", "")
		subString = subString.replaceAll(" ", "")

		if(mainString.contains(subString)){
			Rep.pass(mainString + " contains " + subString + " as expected.")
		}
		else{
			Rep.fail(mainString + " does not contain " + subString + " as expected.")
		}
	}


	@Keyword
	static def deleteFile(String filePath)throws IOException{
		Files.deleteIfExists(Paths.get(filePath))
	}


	@Keyword
	static def clearFolder(String folderPath)throws IOException{
		File folderName = new File(folderPath)

		if(folderName.exists()){
			FileUtils.cleanDirectory(folderName)
		}
	}


	@Keyword
	static def increaseDate(String originalDate, String originalFormat, int increaseAmt, String newFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(originalFormat);
		SimpleDateFormat newformatter = new SimpleDateFormat(newFormat);

		Date dt = formatter.parse(originalDate);
		String originalDateIncreased = Com.increaseWorkingCalendarWithFormat(dt, increaseAmt, newFormat)

		Date newDT = formatter.parse(originalDateIncreased)

		String DateIncreased = newformatter.format(newDT);

		return DateIncreased
	}


	@Keyword
	static def decreaseDate(String originalDate, String originalFormat, int decreaseAmt, String newFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(originalFormat);
		SimpleDateFormat newformatter = new SimpleDateFormat(newFormat);

		Date dt = formatter.parse(originalDate);
		String originalDateDecreased = Com.decreaseWorkingCalendarWithFormat(dt, decreaseAmt, newFormat)

		Date newDT = formatter.parse(originalDateDecreased)

		String DateDecreased = newformatter.format(newDT);

		return DateDecreased
	}
}
