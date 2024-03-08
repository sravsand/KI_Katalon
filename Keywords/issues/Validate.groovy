package issues

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

import internal.GlobalVariable as GVars
import models.Issue
import models.Risk
import keyedInProjects.Constants as Const
import global.Action as gAct
import global.Validate as gVal
import global.Object as Obj
import global.WebDriverMethods as WebD
import global.Report as Rep

public class Validate {

	@Keyword
	static def issueDetails(Issue issue){
		gAct.Wait(1)
		gVal.objectAttributeValue(Const.issues + 'input_Title', 'value', issue.title)
		gVal.objectAttributeValue(Const.issues + 'input_Project', 'value', issue.project)
		gVal.objectAttributeValue(Const.issues + 'input_Owner', 'value', issue.owner)
		Obj.verifySelectedComboBoxValueInFrame(Const.issues + 'select_Status', issue.status, 'xpath', 1)
		Obj.verifySelectedComboBoxValueInFrame(Const.issues + 'select_Impact', issue.impact, "xpath", 1)
		Obj.verifySelectedComboBoxValueInFrame(Const.issues + 'select_ResolutionProgress', issue.resolutionProgress, "xpath", 1)
	}
}
