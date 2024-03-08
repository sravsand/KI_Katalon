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
import models.Charge
import models.GenerateCharge

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Charge_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Charges"
String searchVal = "Charge"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String chargeName = Com.generateRandomText(8)
	String chargeCode = chargeName.toUpperCase()
	
	String[] newCharge = [chargeName, chargeCode, "5.00", "Default Currency"]
	Charge charge = GenerateCharge.createCharge(newCharge)
	component.createCharge(charge)
	
	String chargeNameDataLoad = Com.generateRandomText(8)
	String chargeCodeDataLoad = chargeNameDataLoad.toUpperCase()
	
	String[] newChargeDataLoad = [chargeNameDataLoad, chargeCodeDataLoad, "6.00", "DEFAULT"]
	Charge newDataLoadCharge = GenerateCharge.createCharge(newChargeDataLoad)
	

Rep.nextTestStep("Select [Charges] from search filter")
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
	gAct.compareStringAndReport(colOne, "Charge Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Charge Name")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Active")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Charge Rate")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Currency Code")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, newDataLoadCharge.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, newDataLoadCharge.name)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, "YES")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, newDataLoadCharge.rate)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, newDataLoadCharge.currency)
	
	
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
	WebUI.setText(findTestObject(Const.dataLoad + "input_TemplateActivity"), charge.code)
	gObj.buttonClickSync(Const.dataLoad + "tab_ImportData")
	
		
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
	sVal.searchTableAddedValue(2, charge.code)

