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
import models.BenefitActual
import models.Department
import models.GenerateBenefitActual
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Forecast
import models.GenerateForecast
import models.Project
import models.GenerateProject

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
	component.createForecast(forecast)
	
	String[] newBenefitActual = [todaysDate, project.name, benefitName + " | Reduction in Costs" , "100"]
	BenefitActual benefitActual = GenerateBenefitActual.createBenefitActual(newBenefitActual)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Benefit Actuals] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)

	
Rep.nextTestStep("Click On [+ Add] ")
	WebUI.click(findTestObject(Const.kip4Generic + "a_NEWAdd"))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Select a date")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Date"), benefitActual.date)
	
	
Rep.nextTestStep("Select a project")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), benefitActual.project)
	WebUI.click(findTestObject(Const.columnPicker + "input_BenefitAmount"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click on the dropdown field for [Benefit] and select an option from the list")
	WebUI.click(findTestObject(Const.columnPicker + "select_Benefit"))
	WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_Benefit"), benefitActual.benefit, false)
	WebUI.click(findTestObject(Const.columnPicker + "input_Date"))
	
	
Rep.nextTestStep("Enter [Amount]")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), "Default Currency")
	WebUI.setText(findTestObject(Const.columnPicker + "input_BenefitAmount"), benefitActual.amount)
		
	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
	gAct.Wait(1)


Rep.nextTestStep("Verify recently added Action is available for selection in the table")
	sVal.searchTableAddedValue(5, benefitName)
	