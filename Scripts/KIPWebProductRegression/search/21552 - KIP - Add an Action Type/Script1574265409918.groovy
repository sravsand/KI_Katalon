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

import models.ActionType
import models.GenerateActionType

String actionName = Com.generateRandomText(10)
String actionCode = actionName.toUpperCase()

String[] newActionType = [actionName, actionCode]
ActionType actionType = GenerateActionType.createActionType(newActionType)

String searchItem = "Action Types"
String searchVal = "Action Type"

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Action Types] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	
	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")

	
Rep.nextTestStep("Click on [Save] or [Save & Close] button")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	String errorMsg = gObj.getEditTextSync(Const.columnPicker + "addExpenseError")
	gAct.compareStringAndReport(errorMsg, "Unable to Save Action Type")
	
	
Rep.nextTestStep("Add text in [Name] field")
	gObj.setEditSync(Const.columnPicker + "input_Name", actionType.name)
	
	
Rep.nextTestStep("Add [Code]")
	gObj.setEditSync(Const.columnPicker + "input_Code", actionType.code)
	
	
Rep.nextTestStep("Select [Active] through checkbox")
	boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
	if(!active){
		gObj.checkSync(Const.columnPicker + "chk_Active")
	}

	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")

	
Rep.nextTestStep("Verify the newly added Expense Type is added onto the table")
	sVal.searchTableAddedValue(2, actionType.code)
	
