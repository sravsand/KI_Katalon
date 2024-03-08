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
import models.Expense
import models.GenerateExpense
import models.Category
import models.GenerateCategory
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Expense_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Expenses"
String searchVal = "Expenses"


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
	
	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String expenseNotes = Com.generateRandomText(16)
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	
	component.createCategory(category)
	
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "CC_001", "PRJ017", "MEALS", category.code, true, "16.00", "", "16.00", false, expenseNotes]
	Expense expense = GenerateExpense.createExpense(newExpense)
	
	
Rep.nextTestStep("Select [Expenses] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	WebUI.click(findTestObject(Const.dataLoad + "a_Create Data Template"))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	WebUI.click(findTestObject(Const.dataLoad + "button_Generate Template Spreadsheet"))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click [Close]")
	WebUI.click(findTestObject(Const.dataLoad + "button_Close"))
	gAct.Wait(3)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Resource Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Expense Date")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Department Code")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Cost Centre Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Project Code")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Expense Type Code")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Category Code")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Currency Code")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Units")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Gross Amount")
	String colEleven = ExportData.getValue(12, 10)
	gAct.compareStringAndReport(colEleven, "VAT")
	String colTwelve = ExportData.getValue(13, 10)
	gAct.compareStringAndReport(colTwelve, "Chargeable")
	String colThirteen = ExportData.getValue(14, 10)
	gAct.compareStringAndReport(colThirteen, "Reimbursable")
	String colFourteen = ExportData.getValue(15, 10)
	gAct.compareStringAndReport(colFourteen, "Expense Notes")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, resource.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, expense.date)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, expense.department)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, expense.costCentre)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, expense.project)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, expense.expenseType)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, expense.category)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 8, "DEFAULT")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 9, "")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 10, expense.gross)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 11, expense.vat)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 12, "Yes")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 13, "Yes")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 14, expense.notes)
	
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	def newExportData = eAct.openAndReadFile(downloadPath, searchItem)
	colOne = newExportData.getValue(2, 11)
	gAct.compareStringAndReport(colOne, resource.code)
	colTwo = newExportData.getValue(3, 11)
	gAct.compareStringAndReport(colTwo, expense.date)
	colThree = newExportData.getValue(4, 11)
	gAct.compareStringAndReport(colThree, expense.department)
	colFour = newExportData.getValue(5, 11)
	gAct.compareStringAndReport(colFour, expense.costCentre)
	colFive = newExportData.getValue(6, 11)
	gAct.compareStringAndReport(colFive, expense.project)
	colSix = newExportData.getValue(7, 11)
	gAct.compareStringAndReport(colSix, expense.expenseType)
	colSeven = newExportData.getValue(8, 11)
	gAct.compareStringAndReport(colSeven, expense.category)
	colEight = newExportData.getValue(9, 11)
	gAct.compareStringAndReport(colEight, "DEFAULT")
	colNine = newExportData.getValue(10, 11)
	gAct.compareStringAndReport(colNine, "")
	colTen = newExportData.getValue(11, 11)
	gAct.compareStringAndReport(colTen, expense.gross)
	colEleven = newExportData.getValue(12, 11)
	gAct.compareStringAndReport(colEleven, expense.vat)
	colTwelve = newExportData.getValue(13, 11)
	gAct.compareStringAndReport(colTwelve, "Yes")
	colThirteen = newExportData.getValue(14, 11)
	gAct.compareStringAndReport(colThirteen, "Yes")
	colFourteen = newExportData.getValue(15, 11)
	gAct.compareStringAndReport(colFourteen, expense.notes)
	
	gAct.Wait(1)