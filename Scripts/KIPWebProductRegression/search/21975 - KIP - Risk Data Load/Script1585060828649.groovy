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
import models.Activity
import models.GenerateActivity
import excel.Action as eAct
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Risk
import models.GenerateRisk

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Risk_DataLoad.xls"
gAct.deleteFile(downloadPath)

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
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	String[] newRisk = [riskTitle, "PRJ_AUT029", "RES053", "Open", todaysDate, todaysDate, "Project", "High", "Medium", "High", endDate, "", ""]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	
	
Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template_alt")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Risk Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Title")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Project Code")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Owner Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Status")	
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Date Identified")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Impact Date")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Description")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Contingency")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Probability")
	String colEleven = ExportData.getValue(12, 10)
	gAct.compareStringAndReport(colEleven, "Impact")
	String colTwelve = ExportData.getValue(13, 10)
	gAct.compareStringAndReport(colTwelve, "Severity")
	
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 1, risk.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 2, risk.title)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 3, risk.project)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 4, risk.owner)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 5, "Open")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 6, risk.dateIdentified)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 7, risk.impactDate)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 8, risk.description)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 9, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 10, risk.probability)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 11, risk.impact)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 12, risk.severity)
	
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Import Data]")
	gObj.buttonClickSync(Const.dataLoad + "a_Import")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click on [Browse]")
	WebUI.focus(findTestObject(Const.columnPicker + "input_DLoadFile"))
	WebUI.clickOffset(findTestObject(Const.columnPicker + "input_DataLoadFile"), 0, 0)

	
Rep.nextTestStep("Select the file saved at Step 11 and click Open")
	eAct.uploadFile(downloadPath)
	
	
Rep.nextTestStep("Click [Upload]")
	gObj.buttonClickSync(Const.columnPicker + "a_Upload")
	
		
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "button_Import")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Close")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	sVal.searchTableAddedValue(3, risk.title)


