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
import models.Risk
import models.GenerateRisk
import risks.Validate as rVal

String searchItem = "Risks"
String searchVal = "Risks"

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String browser = Com.getBrowserType()
	String todayDate = Com.todayDate()
	String impDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 1, "dd/MM/yyyy")
	String resDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 10, "dd/MM/yyyy")
	
	String riskName = Com.generateRandomText(10)
	String riskCode = riskName.toUpperCase()
	
	String clonedRiskName = Com.generateRandomText(10)
	String clonedRiskCode = riskName.toUpperCase()
	
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String [] newRisk = [riskName + " " + browser, 'Tech Crunch Web Services Deployment', GVars.user, "Open", todayDate, impDate, "Project", "High", "Medium", "High", resDate, "", riskCode]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	component.createRisk(risk)
	
	String [] clonedRisk = [clonedRiskName + " " + browser, 'Tech Crunch Web Services Deployment', GVars.user, "Open", todayDate, impDate, "Project", "Low", "Medium", "Low", resDate, "", riskCode]
	Risk newrisk = GenerateRisk.createNewRisk(clonedRisk)


Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of an Risks")
	int rowNo = sVal.searchTableReturnRow(3, risk.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneItem")
	

Rep.nextTestStep("Edit data in [Title] textbox")
	gObj.setEditSync(Const.risks + "input_Title", newrisk.title)

	
Rep.nextTestStep("Enter [Code]")
	Rep.info("Code is set to (Auto) ")

	
Rep.nextTestStep("Select [Low] from the [Probability] dropdown")
	gObj.selectComboByLabelSync(Const.risks + 'select_Probability', newrisk.probability)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Select [Medium] from the [Impact] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Impact', newrisk.impact)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Select [Low] from the [Severity] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Severity', newrisk.severity)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Verify the [Risk Value] Field ")
	String riskValue = gObj.getEditTextSync(Const.risks + 'riskValue')
	gAct.findSubstringInStringAndReport(riskValue, "Green")

	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.risks + 'button_SaveAndClose')
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(1)

	
Rep.nextTestStep("Verify recently added risk within [Risks] table")
	sVal.searchTableAddedValue(3, newrisk.title)
	