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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Activity
import models.GenerateActivity
import excel.Action as eAct
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String searchItem = "Resources"
String searchVal = "Resources"

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Resource_DataLoad.xls"
gAct.deleteFile(downloadPath)

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = ["Dave_" + ResourceName, "Employee",  "KIPDEV1", "KIP1TEST", "CLECK", "FULLTIME", costCentreNew, "Martin Phillips", "AutoTest@Workflows.com", "", "100", "CST001", "CST002", "CHG001", "CHG001", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)


Rep.nextTestStep("Select [Projects] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Select the [Fields] user want to add from [Include Fields]")
	gObj.buttonClickSync(Const.dataLoad + "chkSelectInput_Active")

	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Name")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Active")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Standard Cost Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Standard Charge Code")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Overtime Cost Code")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Overtime Charge Code")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Department Code")
	String colNine = ExportData.getValue(10, 10)
	gAct.compareStringAndReport(colNine, "Location Code")
	String colTen = ExportData.getValue(11, 10)
	gAct.compareStringAndReport(colTen, "Primary Role")
	String colEleven = ExportData.getValue(12, 10)
	gAct.compareStringAndReport(colEleven, "Working Time Code")
	String colTwelve = ExportData.getValue(13, 10)
	gAct.compareStringAndReport(colTwelve, "Cost Centre Code")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, resource.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, resource.name)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, "YES")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, resource.cost_std)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, resource.charge_std)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, resource.cost_ovr)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, resource.charge_ovr)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 8, resource.department)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 9, resource.location)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 10, resource.role)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 11, resource.workingTime)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 12, resource.costCentre)
	
	
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

	
Rep.nextTestStep("Click [Search] from [Template Customer] and Select an [Customer]")
	gObj.setEditSync(Const.dataLoad + "input_TemplateActivity", "RES062")
	gObj.buttonClickSync(Const.dataLoad + "tab_ImportData")
		
	
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "button_Import")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Close")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	sVal.searchTableAddedValue(3, resource.name)

