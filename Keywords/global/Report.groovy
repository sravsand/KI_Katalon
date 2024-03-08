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
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GVars
import global.Common as Com

public class Report {

	static def pass(String message){
		GVars.log.logPassed("PASS : " + message)
	}


	static def fail(String message){
		GVars.log.logFailed("FAIL : " + message)
		screenshot(message)
		KeywordUtil.markFailed()
	}


	static def warning(String message){
		GVars.log.logWarning("WARNING : " + message)
	}


	static def error(String message){
		GVars.log.logError("ERROR : " + message)
		screenshot(message)
		KeywordUtil.markError()
	}


	static def info(String message){
		GVars.log.logInfo("INFO : " + message)
	}


	static def nextTestStep(String testStepDetails){
		GVars.testStep ++
		info("---------------------------------------------------------------------------------")
		info("Test Step : " + GVars.testStep + " - " + testStepDetails)
	}


	static def testCaseTitle(){
		String[] testCasePathSplit = GVars.currentTestCaseId.split('/')
		info("---------------------------------------------------------------------------------")
		info("---------------------------------------------------------------------------------")
		info("---------------------------------------------------------------------------------")
		info("Start Test Case : " + testCasePathSplit[testCasePathSplit.length - 1])
		info("---------------------------------------------------------------------------------")
		info("---------------------------------------------------------------------------------")
		info("---------------------------------------------------------------------------------")
	}


	static def testCaseEnd(){
		String[] testCasePathSplit = GVars.currentTestCaseId.split('/')
		info("---------------------------------------------------------------------------------")
		info("End Test Case : " + testCasePathSplit[testCasePathSplit.length - 1])
		info("---------------------------------------------------------------------------------")
	}


	static def screenshot(String message){
		String dateTimeStamp = Com.getTimeDateStamp()
		String savePath = GVars.screenShotPath + "testStep_" + GVars.testStep + "_" + dateTimeStamp + "_" + message + ".png"
		WebUI.takeScreenshot(savePath)
	}
}
