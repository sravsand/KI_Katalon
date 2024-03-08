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
import buildingBlocks.createComponents
import models.Activity
import models.GenerateActivity
import excel.Action as eAct

String searchItem = "Contacts"
String searchVal = "Contact"

String contactName = Com.generateRandomText(5)
String contactCode = "XXTESTCOMPANY1"
String surname = Com.generateRandomText(8)
String firstname = Com.generateRandomText(6)
String middlename = Com.generateRandomText(7)

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Contact_DataLoad.xls"
gAct.deleteFile(downloadPath)

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Contacts] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Select the [Fields] user want to add from [Include Fields]")
	gObj.checkSync(Const.dataLoad + "chkSelectInput_Active")

	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchItem)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Customer Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Surname")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "First Name")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Middle Name(s)")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Title")

	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, contactCode)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, surname)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, firstname)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, middlename)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, "Mr")
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Import Data]")
	gObj.buttonClickSync(Const.columnPicker + "a_Import Data")
	gAct.Wait(4)
	
	
Rep.nextTestStep("Click on [Browse]")
	WebUI.focus(findTestObject(Const.columnPicker + "input_DLoadFile"))
	WebUI.clickOffset(findTestObject(Const.columnPicker + "input_DataLoadFile"), 0, 0)

	
Rep.nextTestStep("Select the file saved at Step 11 and click Open")
	eAct.uploadFile(downloadPath)
	
	
Rep.nextTestStep("Click [Upload]")
	gObj.buttonClickSync(Const.columnPicker + "a_Upload")
	

Rep.nextTestStep("Click [Search] from [Template Customer] and Select an [Customer]")
	gObj.buttonClickSync(Const.columnPicker + "a_Select")

	WebUI.setText(findTestObject(Const.dataLoad + "SelectContact"), "Jeff Biscuits")
	gObj.buttonClickSync(Const.dataLoad + "btn_SelectContact")
	gObj.buttonClickSubSync(Const.dataLoad + "selection", "Jeff Biscuits", "name")
	
	
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "radio_FullName")
	gObj.buttonClickSync(Const.dataLoad + "button_Import")

	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Close")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in contact table")
	sVal.searchTableAddedValue(3, firstname + " " + middlename + " " + surname)

