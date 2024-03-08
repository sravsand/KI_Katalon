import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Action as sAct
import search.Validate as sVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.ActionView
import models.GenerateActionView

String searchItem = "Action Views"
String searchVal = "Action View"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String actionName = Com.generateRandomText(10)
	String clonedActionName = Com.generateRandomText(11)
	
	String[] newActionView = [actionName, "This is a test for script 21573", "Meeting Notes"]
	ActionView actionView = GenerateActionView.createActionView(newActionView)
	component.createActionView(actionView)
	
	String[] clonedActionView = [clonedActionName, "This is a test for script 21574", "Meeting Notes"]
	ActionView newClonedactionView = GenerateActionView.createActionView(clonedActionView)
	

Rep.nextTestStep("Select [Action Views] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of an Action type")
	int rowNo = sVal.searchTableReturnRow(2, actionView.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneItem")
	

Rep.nextTestStep("Amend text in [Name] field")
	gObj.setEditSync(Const.columnPicker + "input_ActionName", newClonedactionView.name)


Rep.nextTestStep("Select [Active] through checkbox")
	boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
	if(!active){
		gObj.checkSync(Const.columnPicker + "chk_Active")
	}

	
Rep.nextTestStep("Verify the [Default Action Type]")
	String defaultActType = gObj.getAttributeSync(Const.columnPicker + "input_DefaultActionType", 'value')
	gAct.compareStringAndReport(defaultActType, actionView.defaultActionType)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(1)
	WebUI.refresh()
	WebUI.waitForElementPresent(findTestObject(Const.columnPicker + "a_Search"), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
	
	
Rep.nextTestStep("Verify that the changes have been updated in expense type table")
	sVal.searchTableAddedValue(2, newClonedactionView.name)

