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
import models.Contract
import models.GenerateContract

String searchItem = "Contract"
String searchVal = "Contract"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String contractName = Com.generateRandomText(10)
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] newContract = ["Draft", "Project Innovation_" + GVars.selectedBrowser, contractName, "", todaysDate, false, GVars.user, "Days", "8.00", ""]
	Contract contract = GenerateContract.createContract(newContract)
	component.createContract(contract)
	
	String clonedContractName = Com.generateRandomText(10)
	
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	
	def weekstart = Com.increaseWorkingCalendarWithFormat(date, 4, pattern)
	String[] newClonedContract = ["Draft", "Project Innovation_" + GVars.selectedBrowser, clonedContractName, "", weekstart, false, GVars.user, "Days", "8.00", ""]
	Contract clonedContract = GenerateContract.createContract(newClonedContract)


Rep.nextTestStep("Select [Contract] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Contract")
	int rowNo = sVal.searchTableReturnRow(3, contract.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(2)
	

Rep.nextTestStep("Amend text in [Name] field")
	gObj.setEditSync(Const.columnPicker + "input_Name", clonedContract.name)
	

Rep.nextTestStep("Amend [Date]")
	gObj.setEditSync(Const.columnPicker + "input_ContractDate", clonedContract.date)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.columnPicker + "nextPage")
	gObj.buttonClickSync(Const.columnPicker + "previousPage")
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, clonedContract.name)
