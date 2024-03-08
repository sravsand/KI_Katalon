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
import models.Activity
import models.GenerateActivity
import excel.Action as eAct
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Timesheet
import models.GenerateTimesheet
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Timesheet_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Timesheets"
String searchVal = "Filter"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String code = Com.generateRandomText(10)
	code = code.toUpperCase()
	String firstName = Com.generateRandomText(5)
	String surname = Com.generateRandomText(8)
	String name = firstName + " " + surname
	String userNme = firstName.take(1) + surname + "@workflows.com"
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = [name, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", userNme, "", "100", "", "", "", "", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)
	component.createResource(resource)
	
	String[] newLogin = [name, code, userNme, resource.code, "TEst1234"]
	Login login = GenerateLogin.createLogin(newLogin)
	component.createLogin(login, "Administration")
	
	String browser = Com.getBrowserType()
	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 4, "dd/MM/yyyy")
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation_" + browser, "Project Management", "Project Plan"]
	String[] newHours = ["8.00", "Test Notes", "7.50", "", "8.00", "", "8.00", "", "8.00", "Some more notes", "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	
	
Rep.nextTestStep("Select [Timesheets] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	WebUI.click(findTestObject(Const.dataLoad + "a_Create Data Template Timesheet"))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	WebUI.click(findTestObject(Const.dataLoad + "button_Generate Template Spreadsheet"))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click [Close]")
	WebUI.click(findTestObject(Const.dataLoad + "button_Close"))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Resource Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Timesheet Date")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Role Code")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Department Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Project Code")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Activity Code")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Hours (HH:MM)")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Overtime")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Cost Code")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Cost Rate")
	String colEleven = ExportData.getValue(12, 10)
	gAct.compareStringAndReport(colEleven, "Chargeable")
	String colTwelve = ExportData.getValue(13, 10)
	gAct.compareStringAndReport(colTwelve, "Charge Code")
	String colThirteen = ExportData.getValue(14, 10)
	gAct.compareStringAndReport(colThirteen, "Charge Rate")
	String colFourteen = ExportData.getValue(15, 10)
	gAct.compareStringAndReport(colFourteen, "Timesheet Notes")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, resource.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, weekCommence)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, "KIP1TL")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, "KIPDEV1")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, "PRJ005")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, "PROJ_MANAGEMENT")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, "8:00")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 8, "No")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 9, "Yes")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 10, "CHG001")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 11, "100")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 12, "Notes")
	
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)

	def newExportData = eAct.openAndReadFile(downloadPath, searchItem)

	colOne = newExportData.getValue(2, 11)
	gAct.compareStringAndReport(colOne, resource.code)
	colTwo = newExportData.getValue(3, 11)
	gAct.compareStringAndReport(colTwo, weekCommence)
	colThree = newExportData.getValue(4, 11)
	gAct.compareStringAndReport(colThree, "KIP1TL")
	colFour = newExportData.getValue(5, 11)
	gAct.compareStringAndReport(colFour, "KIPDEV1")
	colFive = newExportData.getValue(6, 11)
	gAct.compareStringAndReport(colFive, "PRJ005")
	colSix = newExportData.getValue(7, 11)
	gAct.compareStringAndReport(colSix, "PROJ_MANAGEMENT")
	colSeven = newExportData.getValue(8, 11)
	gAct.compareStringAndReport(colSeven, "8:00")
	colEight = newExportData.getValue(9, 11)
	gAct.compareStringAndReport(colEight, "No")
	colNine = newExportData.getValue(10, 11)
	gAct.compareStringAndReport(colNine, "Yes")
	colTen = newExportData.getValue(11, 11)
	gAct.compareStringAndReport(colTen, "CHG001")
	colEleven = newExportData.getValue(12, 11)
	gAct.compareStringAndReport(colEleven, "100")
	colTwelve = newExportData.getValue(13, 11)
	gAct.compareStringAndReport(colTwelve, "Notes")
	gAct.Wait(1)
	