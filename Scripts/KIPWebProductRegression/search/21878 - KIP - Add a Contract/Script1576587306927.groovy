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

import models.Contract
import models.GenerateContract

String contractName = Com.generateRandomText(10)
def pattern = "dd/MM/yyyy"
Date today = new Date()
String todaysDate = today.format(pattern)

String[] newContract = ["Draft", "Project Innovation_" + GVars.selectedBrowser, contractName, "", todaysDate, false, GVars.user, "Days", "8.00", ""]
Contract contract = GenerateContract.createContract(newContract)

String searchItem = "Contract"
String searchVal = "Contract"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Contract] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	
	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")

	
Rep.nextTestStep("Select [Draft] from [Status] dropdown")
	gObj.selectComboByLabelSync(Const.addContract + "select_Status", contract.status)
	
	
Rep.nextTestStep("Search & Select [Project]")
	gObj.setEditSync(Const.addContract + "input_Project", contract.project)
	

Rep.nextTestStep("Check [Name] field is populated with Project name")
	gObj.setEditSync(Const.addContract + "input_Name", contract.name)
	
	
Rep.nextTestStep("Select [Days] from [Bill Time In] dropdown list")
	gObj.selectComboByLabelSync(Const.addContract + "select_TimeUnit", contract.billTimeIn)
	
	
Rep.nextTestStep("Enter [8.00] in [Days Length] field")
	gObj.setEditSync(Const.addContract + "input_DayLength", contract.dayLength)


Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.addContract + "button_Save and Close")
	Act.verifySavePopUpText()
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)

		
Rep.nextTestStep("Verify the recently added Contract details are visible in table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	sVal.searchTableAddedValue(3, contract.name)
