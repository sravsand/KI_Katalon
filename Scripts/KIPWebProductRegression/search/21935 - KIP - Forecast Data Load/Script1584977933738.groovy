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
import global.File as gFile
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
import models.Forecast
import models.GenerateForecast


String dloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\"
String dloadFile = "KIP_Forecast_DataLoad.xls"

String downloadPath = dloadPath + dloadFile

gAct.deleteFile(downloadPath)

String searchItem = "Forecasts"
String searchVal = "Forecasts"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String description = Com.generateRandomText(15)
	String expenditureDesc = Com.generateRandomText(15)
	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	String forecastName = Com.generateRandomText(10)
	
	String[] newForecast = [forecastName, "PRJ_AUT029", "KIPDEV", "KIP1TEST", "", "", ""]
	Forecast forecast = GenerateForecast.createForecast(newForecast)
	
	
Rep.nextTestStep("Select [Forecasts] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	gAct.Wait(GVars.shortWait)
	
	boolean match = gFile.checkFile(dloadPath, dloadFile)
	
	if(match == "false"){
		Rep.fail(dloadFile + " does not exist.")
		gAct.Wait(1)
	}
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Project Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Forecast Description")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Data Type")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Week Start Date")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Role Code")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Department Code")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Resource Code")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Demand Days")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Expense Type Code")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Expenditure Description")
	String colEleven = ExportData.getValue(12, 10)
	gAct.compareStringAndReport(colEleven, "Expenditure Cost")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, forecast.project)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, description)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, "Expenditure")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, weekCommence)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, forecast.role)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, forecast.department)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 8, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 9, "MEALS")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 10, expenditureDesc)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 11, "")
	
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Import Data]")
	gObj.buttonClickSync(Const.columnPicker + "a_Import Data")
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
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)
	sVal.searchTableAddedValue(2, description)
