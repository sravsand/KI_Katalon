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
import models.Deliverable
import models.GenerateDeliverable

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Deliverable_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Deliverables"
String searchVal = "Assignment"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	String DeliverableName = Com.generateRandomText(8)
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	String[] newDeliverable = [DeliverableName, GVars.user, "Project Innovation_" + selectedBrowser, "BILLING", endDate]
	Deliverable deliverable = GenerateDeliverable.createDeliverable(newDeliverable)
	component.createDeliverable(deliverable)

	
Rep.nextTestStep("Select [Deliverables] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Name")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Delivery Date")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Project Code")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Owner Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Status")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Deliverable Type Code")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Publish To")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Description")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, deliverable.name)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, deliverable.deliveryDate)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, deliverable.project)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, deliverable.owner)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, "Provisional")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, "BILLING")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, "Project")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 8, "Some text here")
	
	
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
	gAct.Wait(1)
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	sVal.searchTableAddedValue(5, deliverable.name)
