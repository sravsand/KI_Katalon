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
import models.Issue
import models.GenerateIssue

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Issue_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Issues"
String searchVal = "Issues"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String issueTitle = Com.generateRandomText(10)
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String issueDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String newIssueDesc = Com.generateRandomText(15) + "_" + Com.generateRandomText(4)
	String[] newIssue = [issueTitle, "PRJ_AUT029", "RES053", "Open", todaysDate, "", "Project", "Medium", "Improving", issueDescription, ""]
	Issue issue = GenerateIssue.createNewIssue(newIssue)
	
	
Rep.nextTestStep("Select [Issues] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template_alt")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Issue Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Title")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Project Code")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Owner Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Status")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Created On")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Impact Date")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Description")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Solution")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Impact")
	String colEleven = ExportData.getValue(12, 10)
	gAct.compareStringAndReport(colEleven, "Resolution Progress")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 1, issue.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 2, issue.title)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 3, issue.project)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 4, issue.owner)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 5, "Open")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 6, issue.dateCreated)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 7, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 8, issue.description)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 9, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 10, issue.impact)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 11, issue.resolutionProgress)
	
	
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
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	sVal.searchTableAddedValue(3, issue.title)

