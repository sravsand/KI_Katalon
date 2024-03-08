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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Risk
import models.GenerateRisk
import models.RiskAction
import models.GenerateRiskAction

String searchItem = "Risks"
String searchVal = "Risks"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	String riskTitle = Com.generateRandomText(10)
	String riskActionDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String newRiskType = "Issue Resolution"
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String[] newRisk = [riskTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, todaysDate, "Project", "High", "Medium", "High", endDate, "", ""]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	component.createRisk(risk)
	
	String[] newRiskAction = ["Project Innovation_" + GVars.selectedBrowser, risk.code + " | " + riskTitle, "Mitigation", GVars.user, "Open", todaysDate, "1", riskActionDescription, "Medium"]
	RiskAction riskAction = GenerateRiskAction.createNewRiskAction(newRiskAction)
	component.createRiskAction(riskAction)
	

Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu] of Risks")
	int rowNo = sVal.searchTableReturnRow(3, risk.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Click on the [Mitigation Plan Items] tab")
	gObj.buttonClickSync(Const.columnPicker + "td_MitigationPlanItemsTab")


Rep.nextTestStep("Verify that all the associated [Mitigation Plan Items] are displayed within the table ")
	String owner = gObj.getEditTextSubIntPosSync(Const.columnPicker + "mitPlanTable", 4, "col")
	gAct.compareStringAndReport(owner, riskAction.owner)
	
	String dueDate = gObj.getEditTextSubIntPosSync(Const.columnPicker + "mitPlanTable", 6, "col")
	gAct.compareStringAndReport(dueDate, riskAction.dateDue)
	
	String desc = gObj.getEditTextSubIntPosSync(Const.columnPicker + "mitPlanTable", 7, "col")
	gAct.compareStringAndReport(desc, riskAction.description)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.getEditTextSync(Const.columnPicker + "button_SaveAndClose")
	