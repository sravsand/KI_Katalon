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
import buildingBlocks.createComponentsLogedIn as component
import models.ActionType
import models.GenerateActionType

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

String searchItem = "Action Types"
String searchVal = "Action Type"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String actionName = Com.generateRandomText(10)
	String actionCode = actionName.toUpperCase()
	
	String[] newActionType = [actionName, actionCode]
	ActionType actionType = GenerateActionType.createActionType(newActionType)
	component.createActionType(actionType)


Rep.nextTestStep("Select [Action Types] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of an Action type")
	int rowNo = sVal.searchTableReturnRow(2, actionType.code)	
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	

Rep.nextTestStep("Untick [Active] through checkbox")
	gObj.uncheckSync(Const.columnPicker + "chk_Active")


Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(3)
	
	
Rep.nextTestStep("Verify that the changes have been updated in expense type table")
	String Active_new = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 4]))
	gAct.compareStringAndReport(Active_new, "No")
