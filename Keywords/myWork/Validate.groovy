package myWork

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

import internal.GlobalVariable
import global.WebDriverMethods as WebD
import global.Report as Rep

public class Validate {

	@Keyword
	static def layout_TSplit(){
		String topPane = "//body/div[@class='main-container container-fluid']/div[@class='page-container']/div[@class='page-content']/div[@class='page-partial']/div[@class='FullPage']/div[@class='page-body']/div[@class='dashboard-container edit']/div[" + 1 + "]/div[" + 1 +"]"
		boolean topPaneExists = WebD.elementPresent(topPane)

		String leftPane = "//body/div[@class='main-container container-fluid']/div[@class='page-container']/div[@class='page-content']/div[@class='page-partial']/div[@class='FullPage']/div[@class='page-body']/div[@class='dashboard-container edit']/div[" + 2 + "]/div[" + 1 +"]"
		boolean leftPaneExists = WebD.elementPresent(leftPane)

		String rightPane = "//body/div[@class='main-container container-fluid']/div[@class='page-container']/div[@class='page-content']/div[@class='page-partial']/div[@class='FullPage']/div[@class='page-body']/div[@class='dashboard-container edit']/div[" + 2 + "]/div[" + 2 +"]"
		boolean rightPaneExists = WebD.elementPresent(rightPane)

		if((topPane)&&(leftPaneExists)&&(rightPaneExists)){
			Rep.pass("Layout as expected")
		}else{
			Rep.fail("Layout not as expected")
		}
		Rep.screenshot("The screen layout")
	}


	@Keyword
	static def layout_2equalSplit(){
		String leftPane = "//body/div[@class='main-container container-fluid']/div[@class='page-container']/div[@class='page-content']/div[@class='page-partial']/div[@class='FullPage']/div[@class='page-body']/div[@class='dashboard-container edit']/div[" + 1 + "]/div[" + 1 +"]"
		boolean leftPaneExists = WebD.elementPresent(leftPane)

		String rightPane = "//body/div[@class='main-container container-fluid']/div[@class='page-container']/div[@class='page-content']/div[@class='page-partial']/div[@class='FullPage']/div[@class='page-body']/div[@class='dashboard-container edit']/div[" + 1 + "]/div[" + 2 +"]"
		boolean rightPaneExists = WebD.elementPresent(rightPane)
		if((leftPaneExists)&&(rightPaneExists)){
			Rep.pass("Layout as expected")
		}else{
			Rep.fail("Layout not as expected")
		}
		Rep.screenshot("The screen layout")
	}
}
