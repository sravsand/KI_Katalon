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
import models.Risk
import models.GenerateRisk
import risks.Validate as rVal

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

String searchItem = "Risks"
String searchVal = "Risks"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String browser = Com.getBrowserType()
	String todayDate = Com.todayDate()
	String impDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 1, "dd/MM/yyyy")
	String resDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 10, "dd/MM/yyyy")
	
	String riskName = Com.generateRandomText(10)
	String riskCode = riskName.toUpperCase()
	
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String [] newRisk = [riskName + " " + browser, 'Tech Crunch Web Services Deployment', GVars.user, "Open", todayDate, impDate, "Project", "High", "Medium", "High", resDate, "", riskCode]
	Risk risk = GenerateRisk.createNewRisk(newRisk)


Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")

	
Rep.nextTestStep("Click on [Save] or [Save & Close] button")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	String errorMsg = gObj.getEditTextSync(Const.columnPicker + "addExpenseError")
	gAct.compareStringAndReport(errorMsg, "Unable to Save Risk")
	

Rep.nextTestStep("Enter Some text to provide [Risk Title]")
	gObj.setEditSync(Const.risks + 'input_Title', risk.title)
	

Rep.nextTestStep("Enter [Code]")
	Rep.info("Code is set to (Auto) ")
	
	
Rep.nextTestStep("Click on [Search] icon and select a project from [Project] field ")
	String projectText = gObj.getAttributeSync(Const.risks + 'input_Project', 'value')
	if(projectText == ""){
		gObj.setEditSync(Const.risks + 'input_Project', risk.project)
		gObj.buttonClickSync(Const.risks + 'input_Title')
	}
	

Rep.nextTestStep("Click on [Search] icon and select a owner from [Owner] field ")
	String ownerText = gObj.getAttributeSync(Const.risks + 'input_Owner', 'value')
	if(ownerText == ""){
		gObj.setEditSync(Const.risks + 'input_Owner', risk.owner)
		gObj.buttonClickSync(Const.risks + 'input_Title')
	}
			
	
Rep.nextTestStep("Verify the [Status] ")
	WebUI.verifyOptionSelectedByLabel(findTestObject(Const.risks + 'select_Status'), risk.status, false, 5)
	
	
Rep.nextTestStep("Verify today's date is populated in [Date] field ")
	String dateRaised = gObj.getAttributeSync(Const.risks + 'input_DateIdentified', 'value')
	gAct.compareStringAndReport(dateRaised, risk.dateIdentified)

		
Rep.nextTestStep("Select an [Impact date] (If Required)")
	gObj.setEditSync(Const.risks + 'input_ImpactDate', risk.impactDate)
		
		
Rep.nextTestStep("Select project from the [Publish to] dropdown field ")
	gObj.selectComboByLabelSync(Const.risks + 'select_PublishTo', risk.publishTo)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Select [Medium] from the [Probability] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Probability', "Medium")
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Verify the colour within probability field dropdown ")
	String colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Probability', 'style')
	gAct.compareStringAndReport(colourNewTask, medium)
	
		
Rep.nextTestStep("Select [Medium] from the [Impact] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Impact', risk.impact)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Verify the colour within [Impact] field dropdown ")
	colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Impact', 'style')
	gAct.compareStringAndReport(colourNewTask, medium)
	
	
Rep.nextTestStep("Select [High] from the [Severity] dropdown ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Severity', risk.severity)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	
	
Rep.nextTestStep("Verify the colour within [Severity] field dropdown ")
	colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Severity', 'style')
	gAct.compareStringAndReport(colourNewTask, high)
		
		
Rep.nextTestStep("Select a Future date for [Expected Resolution date] field ")
	gObj.setEditSync(Const.risks + 'input_ResolutionDate', risk.resolutionDate)
	

Rep.nextTestStep("Verify the [Risk Value] Field ")
	String riskValue = gObj.getEditTextSync(Const.risks + 'riskValue')
	gAct.findSubstringInStringAndReport(riskValue, "Amber")
		
		
Rep.nextTestStep("Select the [High] from [Probability] field and verify ")
	gObj.selectComboByLabelSync(Const.risks + 'select_Probability', risk.probability)
	gObj.buttonClickSync(Const.risks + 'input_Title')
	colourNewTask = gObj.getAttributeSync(Const.risks + 'select_Probability', 'style')
	gAct.compareStringAndReport(colourNewTask, high)
	riskValue = gObj.getEditTextSync(Const.risks + 'riskValue')
	gAct.findSubstringInStringAndReport(riskValue, "RED")
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.risks + 'button_SaveAndClose')
	gAct.Wait(1)
	

Rep.nextTestStep("Verify recently added risk within [Risks] table, then Open and verify recently added Risk details")
	rVal.searchRiskDetails(risk, 3)

	