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
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_Login_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Logins"
String searchVal = "Login"

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
	
	String template = Com.generateRandomText(10)
	templateCode = template.toUpperCase()
	String[] newLoginTemp = [template, templateCode, "", "" , ""]
	Login loginTemp = GenerateLogin.createLogin(newLoginTemp)
	component.createLoginTemplate(loginTemp, "Administration")
	
	String[] newLogin = [name, code, userNme, resource.name, "TEst1234"]
	Login login = GenerateLogin.createLogin(newLogin)
	
	
Rep.nextTestStep("Select [Login] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template_alt")
	
	
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
	gAct.compareStringAndReport(colOne, "Login Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Login Name")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Active")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Customer Code")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "User Id")
	String colSix = ExportData.getValue(7, 10)
	gAct.compareStringAndReport(colSix, "Password")
	String colSeven = ExportData.getValue(8, 10)
	gAct.compareStringAndReport(colSeven, "Login Profile Code")
	String colEight = ExportData.getValue(9, 10)
	gAct.compareStringAndReport(colEight, "Login Group Code")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 1, login.code)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 2, login.name)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 3, "Yes")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 4, "CL007")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 5, login.userId)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 6, login.password)
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 7, "KADMIN")
	eAct.writeToCellInExcelXLS(downloadPath, searchItem, 10, 8, "KADMIN")
	
	
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

	
Rep.nextTestStep("Click [Search] from [Template Skill] and Select an [skill]")
	gObj.setEditSync(Const.dataLoad + "input_TemplateActivity", loginTemp.code)
	gObj.buttonClickSync(Const.dataLoad + "tab_ImportData")
	
		
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "button_Import")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Close")
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the recently upload data is available for selection in Activity table")
	sVal.searchTableAddedValue(2, login.code)
