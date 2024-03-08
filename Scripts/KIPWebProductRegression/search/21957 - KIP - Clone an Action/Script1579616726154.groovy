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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Action
import models.GenerateAction

String searchItem = "All Actions"
String searchVal = "All Actions"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String actionDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	
	String[] newAction = [GVars.user, "TO DO", todaysDate, "Project Innovation_" + GVars.selectedBrowser, actionDescription]
	Action action = GenerateAction.createAction(newAction)
	component.createAction(action)
	
	String clonedActionDescription = Com.generateRandomText(12) + " " + Com.generateRandomText(2) + " " + Com.generateRandomText(25)
	String[] newClonedAction = [GVars.user, "MEETING", todaysDate, "Project Innovation_" + GVars.selectedBrowser, clonedActionDescription]
	Action clonedAction = GenerateAction.createAction(newClonedAction)


Rep.nextTestStep("Select [All Actions] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Actions")
	int rowNo = sVal.searchTableReturnRow(8, action.description)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneProject")
	

Rep.nextTestStep("Amend text in [Action type]")
	gObj.buttonClickSync(Const.columnPicker + "select_ActionType")
	gObj.selectComboByValueSync(Const.columnPicker + "select_ActionType", clonedAction.actionType)
	gObj.buttonClickSync(Const.columnPicker + "tab_ToDo")
		
	gObj.setEditSync(Const.columnPicker + "action_Details", clonedAction.description)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(1)
	WebUI.refresh()
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(8, clonedAction.description)
