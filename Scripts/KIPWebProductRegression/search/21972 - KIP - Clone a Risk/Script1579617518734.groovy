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
import models.Risk
import models.GenerateRisk

String searchItem = "Risks"
String searchVal = "Risks"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	String riskTitle = Com.generateRandomText(10)
	
	String low = "background-color: lightgreen; color: black;"
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String[] newRisk = [riskTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, todaysDate, "Project", "High", "Medium", "High", endDate, "", ""]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	component.createRisk(risk)
	
	String clonedRiskTitle = Com.generateRandomText(10)
	String[] newClonedRisk = [clonedRiskTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, todaysDate, "Project", "Low", "Medium", "Low", endDate, "", ""]
	Risk clonedRisk = GenerateRisk.createNewRisk(newClonedRisk)


Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Risks")
	int rowNo = sVal.searchTableReturnRow(3, risk.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneProject")
	

Rep.nextTestStep("Enter Some text in title textbox]")
	gObj.setEditSync(Const.risks + 'input_Title', clonedRisk.title)
	
	
Rep.nextTestStep("Select [low] from the [Probability] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Probability', clonedRisk.probability)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	String colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Probability', 'style')
	gAct.compareStringAndReport(colourNewTask, low)
	
	
Rep.nextTestStep("Select [Medium] from the [Impact] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Impact', clonedRisk.impact)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Impact', 'style')
	gAct.compareStringAndReport(colourNewTask, medium)
	
	
Rep.nextTestStep("Select [Low] from the [Severity] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Severity', clonedRisk.severity)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Severity', 'style')
	gAct.compareStringAndReport(colourNewTask, low)
	
	
Rep.nextTestStep("Verify the [Risk Value] Field ")
	String riskValue = gObj.getEditTextSync(Const.risks + 'riskValue')
	gAct.findSubstringInStringAndReport(riskValue, "Green")
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, clonedRisk.title)
