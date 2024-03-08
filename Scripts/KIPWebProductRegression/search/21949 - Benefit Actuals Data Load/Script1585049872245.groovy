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
import models.BenefitActual
import models.GenerateBenefitActual
import models.Forecast
import models.GenerateForecast
import models.Project
import models.GenerateProject

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_BenefitActual_DataLoad.xls"
gAct.deleteFile(downloadPath)

String searchItem = "Benefit Actuals"
String searchVal = "Benefit Actual"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String ProjectName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newProject = [ProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project project = GenerateProject.createProject(newProject)
	component.createProject(project)
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	String forecastName = Com.generateRandomText(10)
	String benefitName = Com.generateRandomText(6)
	String benefitNameUpper = benefitName.toUpperCase()
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] newForecast = [forecastName, project.name, "KIPDEV", "KIP1TEST", benefitName, "Reduction in Costs", todaysDate]
	Forecast forecast = GenerateForecast.createForecast(newForecast)
	component.createForecastAndApprove(forecast)
	
	String[] newBenefitActual = [todaysDate, project.code, benefitName, "100"]
	BenefitActual benefitActual = GenerateBenefitActual.createBenefitActual(newBenefitActual)

	
Rep.nextTestStep("Select [Benefit Actuals] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Data Template")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Date")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Project Code")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Benefit Name")
	String colFour = ExportData.getValue(5, 10)
	gAct.compareStringAndReport(colFour, "Currency")
	String colFive = ExportData.getValue(6, 10)
	gAct.compareStringAndReport(colFive, "Amount")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 1, benefitActual.date)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 2, benefitActual.project)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 3, benefitActual.benefit)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 4, "DEFAULT")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 5, benefitActual.amount)
	
	
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
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)
	sVal.searchTableAddedValue(5, benefitActual.benefit)
