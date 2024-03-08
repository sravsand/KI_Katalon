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
import com.kms.katalon.core.webui.driver.DriverFactory

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
import models.Action
import models.GenerateAction

def pattern = "dd/MM/yyyy"
Date today = new Date()
String todaysDate = today.format(pattern)
String actionDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)

String[] newAction = [GVars.user, "TO DO", todaysDate, "Project Innovation_" + GVars.selectedBrowser, actionDescription]
Action action = GenerateAction.createAction(newAction)

String searchItem = "All Actions"
String searchVal = "All Actions"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [All Actions] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add]")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Search & Select [Resource]")
	gObj.clearSetEditSync(Const.columnPicker + "input_Resource", action.resource)
	gObj.buttonClickSync(Const.columnPicker + "tab_ToDo")
	

Rep.nextTestStep("Select [Action Type] from dropdown list")
	gObj.buttonClickSync(Const.columnPicker + "select_ActionType")
	gObj.selectComboByValueSync(Const.columnPicker + "select_ActionType", action.actionType)
	gObj.buttonClickSync(Const.columnPicker + "tab_ToDo")
		
	
Rep.nextTestStep("Select [Required Date] from calendar")
	gObj.setEditSync(Const.columnPicker + "input_RequiredDate", action.date)


Rep.nextTestStep("Search & Select [Project]")
	gObj.setEditSync(Const.columnPicker + "input_Project", action.project)
	gObj.buttonClickSync(Const.columnPicker + "tab_ToDo")
	gObj.setEditSync(Const.columnPicker + "action_Details", action.description)


Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")


Rep.nextTestStep("Verify recently added Action is available for selection in the table")
	sVal.searchTableAddedValue(8, action.description)

