import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable as GVars

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import global.Report as Rep
import global.Common as Com
import keyedInProjects.Action as Act
import global.File as gFile
import groovy.util.XmlParser



class TestListener {
	
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext){
		GVars.log = new KeywordLogger('')
		
		String expVersion
		
		if(GVars.ppmUrl.contains("localhost")) {
			expVersion = "KeyedIn Projects v" + gFile.getVersionInfo("C:/inetpub/wwwroot/KIPAutomation/bin/KeyedIn.Projects.dll")
		}else{
			expVersion = "KeyedIn Projects v" + gFile.getVersionInfo('\\\\172.31.30.19\\c$\\inetpub\\wwwroot\\KIP4\\bin\\KeyedIn.Projects.dll')
		}
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("#~~~~~~~~~~~~~TEST SUITE RUN~~~~~~~~~~~~~~~#")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("#   KIP build at test:      ") //+ expVersion + "           #")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("##############################################")
	}
	
	
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext){
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("#~~~~~~~~~~TEST SUITE RUN Complete~~~~~~~~~~~#")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("##############################################")
	}
	
	
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext){
		String browserVer = DriverFactory.getExecutedBrowser()
		GVars.selectedBrowser = DriverFactory.getExecutedBrowser().getName()
		
		if (browserVer == "Chrome (headless)"){
			GVars.selectedBrowser = "CHROME_DRIVER"
		}else if(browserVer == "Firefox (headless)"){
			GVars.selectedBrowser = "FIREFOX_DRIVER"
		}
		
		GVars.configFilePath = GVars.configFilePath.replace("browserName", GVars.selectedBrowser)
		GVars.configFileName = 	"katalonConfigDefault.xml"
		Act.changeUserCredentials()
		
		GVars.log = new KeywordLogger('')
		
		GVars.currentTestCaseId = testCaseContext.getTestCaseId()
		Rep.testCaseTitle()
		
		String[] testCasePathSplit = GVars.currentTestCaseId.split('/')
		String testCase = testCasePathSplit[testCasePathSplit.length - 1]
		GVars.testStep = 0
			
		String userDir = System.getProperty("user.dir")
		String dateTimeStamp = Com.getTimeDateStamp()
		GVars.screenShotPath = "C:\\AutomationReports\\ScreenShots\\" + testCase + "\\" + dateTimeStamp + "\\"
		
		File newDir = new File(GVars.screenShotPath)
		newDir.mkdirs()
		String expVersion
		
		if(GVars.ppmUrl.contains("localhost")) {
			expVersion = "KeyedIn Projects v" + gFile.getVersionInfo("C:/inetpub/wwwroot/KIPAutomation/bin/KeyedIn.Projects.dll")
		}else{
			expVersion = "KeyedIn Projects v" + gFile.getVersionInfo('\\\\172.31.30.19\\c$\\inetpub\\wwwroot\\KIP4\\bin\\KeyedIn.Projects.dll')
		}
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("#~~~~~~~~~~~~~~~~TEST RUN~~~~~~~~~~~~~~~~~~#")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("#   KIP build at test:      " + expVersion + "           #")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("##############################################")
		Rep.info("##############################################")
	}
	
	
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		println testCaseContext.getTestCaseStatus()	
			
		if(GVars.browser == "MicrosoftEdge"){	
			Runtime.getRuntime().exec("TASKKILL /F /IM MicrosoftEdge.exe");
		}else{
			WebUI.closeBrowser()
		}		
		Rep.testCaseEnd()
		Rep.info("##############################################")
		Runtime.getRuntime().gc()
		Rep.info("##############################################")
		
	}
}