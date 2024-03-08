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
import models.Action
import models.GenerateAction

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Action_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "All Actions"
String searchVal = "MEETING (Actions)"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String actionDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	String[] newAction = ["RES053", "TO DO", weekCommence, "PRJ_AUT029", actionDescription]
	Action action = GenerateAction.createAction(newAction)

	
Rep.nextTestStep("Select [All Actions] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Resource")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Customer")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Contact")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Date Logged")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Reference")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Required Date")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Earliest Date Logged")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Meeting Notes")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 1, action.resource)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 2, "CL007")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 3, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 4, weekCommence)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 5, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 6, weekCommence)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 7, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 8, "")

	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	def newExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
	colOne = newExportData.getValue(2, 11)
	gAct.compareStringAndReport(colOne, action.resource)
	colTwo = newExportData.getValue(3, 11)
	gAct.compareStringAndReport(colTwo, "CL007")
	colThree = newExportData.getValue(4, 11)
	gAct.compareStringAndReport(colThree, "")
	colFour = newExportData.getValue(5, 11)
	gAct.compareStringAndReport(colFour, weekCommence)
	colFive = newExportData.getValue(6, 11)
	gAct.compareStringAndReport(colFive, "")
	colSix = newExportData.getValue(7, 11)
	gAct.compareStringAndReport(colSix, weekCommence)
	colSeven = newExportData.getValue(8, 11)
	gAct.compareStringAndReport(colSeven, "")
	colEight = newExportData.getValue(9, 11)
	gAct.compareStringAndReport(colEight, "")