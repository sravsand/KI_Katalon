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
import models.Role
import models.GenerateRole
import models.CostCentre
import models.GenerateCostCentre
import models.Cost
import models.GenerateCost

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Cost_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Costs"
String searchVal = "Cost"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String costName = Com.generateRandomText(8)
	String costCode = costName.toUpperCase()
	
	String[] newCost = [costName, costCode, "17.00", "", "Default Currency"]
	Cost cost = GenerateCost.createCost(newCost)
	component.createCost(cost)
	
	String newCostName = Com.generateRandomText(8)
	String newCostCode = newCostName.toUpperCase()
	
	String[] newDatLoadCost = [newCostName, newCostCode, "15.00", "", "DEFAULT"]
	Cost dataLoadCost = GenerateCost.createCost(newDatLoadCost)
	

Rep.nextTestStep("Select [Charges] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Select the [Fields] user want to add from [Include Fields]")
	gObj.checkSync(Const.dataLoad + "chkSelectInput_Active")

	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Cost Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Cost Name")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Active")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Cost Rate")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Currency Code")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Applies to standard")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Applies to Overtime")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, dataLoadCost.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, dataLoadCost.name)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, "YES")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, dataLoadCost.rate)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, dataLoadCost.currency)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, "YES")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, "YES")
	
	
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

	
Rep.nextTestStep("Click [Search] from [Template Role] and Select an [Role]")
	gObj.setEditSync(Const.dataLoad + "input_TemplateActivity", cost.code)
	gObj.buttonClickSync(Const.dataLoad + "tab_ImportData")
	
		
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "button_Import")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Close")
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	sVal.searchTableAddedValue(2, dataLoadCost.code)

