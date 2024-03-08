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
import models.Supply
import models.GenerateSupply

String searchItem = "Supply"
String searchVal = "Supply"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	def wkCommence = Com.weekStartDate()
	def date = Date.parse(pattern, wkCommence)
	String notes = "Test Delete"
	
	def wk2Commence = Com.increaseWorkingCalendarWithFormat(date, 5, pattern)
	def date2 = Date.parse(pattern, wk2Commence)
	def wk3Commence = Com.increaseWorkingCalendarWithFormat(date2, 5, pattern)
	def date3 = Date.parse(pattern, wk3Commence)
	def wk4Commence = Com.increaseWorkingCalendarWithFormat(date3, 5, pattern)
	
	String[] newSupply = ["Project Innovation_" + GVars.selectedBrowser, GVars.user, "KIP Dev Team 1", "KIP 1 Team Lead", "5", "5", "5", "5"]
	Supply supply = GenerateSupply.createSupply(newSupply)
	component.createSupply(supply)

	
Rep.nextTestStep("Select [Supply] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of an Supply")
	gObj.buttonClickSync(Const.columnPicker + "a_TestSuppy_21942")
	gAct.Wait(1)
	int rowNo = sVal.searchTableReturnRow(6, wkCommence)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "chk_SupplyCell", rowNo, "row")
	int rowNo2 = sVal.searchTableReturnRow(6, wk2Commence)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "chk_SupplyCell", rowNo2, "row")
	int rowNo3 = sVal.searchTableReturnRow(6, wk3Commence)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "chk_SupplyCell", rowNo3, "row")
	int rowNo4 = sVal.searchTableReturnRow(6, wk4Commence)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "chk_SupplyCell", rowNo4, "row")

	
Rep.nextTestStep("Click onto [Delete]")
	gObj.buttonClickSync(Const.columnPicker + "a_DeleteSupply")
	
	
Rep.nextTestStep("Click OK")
	gObj.buttonClickSync(Const.columnPicker + "button_OK")

	
Rep.nextTestStep("Verify that the deleted expense type is not available for selection")
	sVal.searchTableDeletedValue(6, wkCommence)
	sVal.searchTableDeletedValue(6, wk2Commence)
	sVal.searchTableDeletedValue(6, wk3Commence)
	sVal.searchTableDeletedValue(6, wk4Commence)
