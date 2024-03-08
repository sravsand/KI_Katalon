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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn as component
import models.Activity
import models.GenerateActivity
import excel.Action as eAct
import models.Project
import models.GenerateProject

String searchItem = "Projects"
String searchVal = "Project"

String projectName = Com.generateRandomText(5)
String projectCode = projectName.toUpperCase()

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\Project-DataloadTemplate.xlsx"
gAct.deleteFile(downloadPath)

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	String ProjectName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newProject = [ProjectName, "CL007", "FULLTIME" , "Project", "PPM SOLUTIONS", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project project = GenerateProject.createProject(newProject)
	

Rep.nextTestStep("Select [Projects] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template Project")
	
	
Rep.nextTestStep("Select [Project Type]")
	gObj.buttonClickSync(Const.dataLoad + "projectDropDown")
	gObj.buttonClickSubSync(Const.dataLoad + "select_Project", "Project", "project")

	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "btn_GenTemplateSheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Cancel")
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Name")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Code")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Active")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Status")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Customer")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Working Time")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Level")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Parent Project")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Available for Time and Expense tracking")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Allow posts")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 1, project.name)
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 2, project.code)
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 3, "Yes")
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 4, "1 | Default")
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 5, project.customer)
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 6, project.workingTime)
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 7, project.projectLevel)
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 8, project.parent)
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 9, "Yes")
	eAct.writeToCellInExcelColNo(downloadPath, searchVal, 10, 10, "Yes")
	
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Import Data]")
	gObj.buttonClickSync(Const.columnPicker + "a_Import Data Project")
	gAct.Wait(GVars.midWait)
	

Rep.nextTestStep("Select the file saved at Step 11 and click Open")
	eAct.uploadFile(downloadPath)


Rep.nextTestStep("Click [Search] from [Template Customer] and Select an [Customer]")
	gObj.buttonClickSync(Const.dataLoad + "projectTemplateDropDown")
	gObj.buttonClickSync(Const.dataLoad + "input_Project")
	gObj.setEditSync(Const.dataLoad + "input_Project", "KIP Strategic Project")
	gObj.buttonClickSubSync(Const.dataLoad + "select_Project", "KIP Strategic Project", "project")
	
	
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "a_ImportProject")
	
	
Rep.nextTestStep("Click [OK]")
	gObj.buttonClickSync(Const.dataLoad + "btn_OK")
	gAct.Wait(GVars.midWait)
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	WebUI.waitForElementPresent(findTestObject(Const.columnPicker + "a_Search"), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	sVal.searchTableAddedValue(4, project.name)

