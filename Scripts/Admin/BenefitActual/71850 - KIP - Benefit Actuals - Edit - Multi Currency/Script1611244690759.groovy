import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.katalon.plugin.keyword.waitforangular.WaitForAngularKeywords
import com.katalon.plugin.keyword.angularjs.DropdownKeywords

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
import com.katalon.plugin.keyword.calendar.SetDateCalendarKeyword as SetDateCalendarKeyword

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.DatePicker as dpck
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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.BenefitActual
import models.GenerateBenefitActual
import models.Project
import models.GenerateProject
import models.Forecast
import models.GenerateForecast
import models.Currency
import models.GenerateCurrency
import models.ExchangeRate
import models.GenerateExchangeRate

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String ProjectName = Com.generateRandomText(10)
//	String newDate = Com.todayDate()
	String newDate = Com.workingDate()
	String[] newProject = [ProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project project = GenerateProject.createProject(newProject)
	component.createProject(project)
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	String forecastName = Com.generateRandomText(10)
	String benefitName = Com.generateRandomText(6)
	String benefitNameUpper = benefitName.toUpperCase()
	
	def pattern = "dd/M/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] arrDate = todaysDate.split('/')
	int day = arrDate[0].toInteger()
	int month = arrDate[1].toInteger()
	int year = arrDate[2].toInteger()
	
	String newDateNew = gAct.increaseDate(todaysDate, pattern, 5, pattern)
	
	String[] arrDateNew = newDateNew.split('/')
	int dayNew = arrDateNew[0].toInteger()
	int monthNew = arrDateNew[1].toInteger()
	int yearNew = arrDateNew[2].toInteger()
	
	String[] newForecast = [forecastName, project.name, "KIPDEV", "KIP1TEST", benefitName, "Reduction in Costs", todaysDate]
	Forecast forecast = GenerateForecast.createForecast(newForecast)
	component.createForecastAndApprove(forecast)
	
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€", true]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	String[] newExchangeRate = [currency.code, todaysDate, "4.5"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate, currency)
	
	String[] newBenefitActual = [todaysDate, project.name, benefitName + " | Reduction in Costs" , "100"]
	BenefitActual benefitActual = GenerateBenefitActual.createBenefitActual(newBenefitActual)
	component.createBenefitActual(benefitActual, currency)
	
	String ProjectNameNew = Com.generateRandomText(10)
	String[] newProjectNew = [ProjectNameNew, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project projectNew = GenerateProject.createProject(newProjectNew)
	component.createProject(projectNew)
	
	String forecastNameNew = Com.generateRandomText(10)
	String benefitNameNew = Com.generateRandomText(6)
	String[] newForecastNew = [forecastNameNew, projectNew.name, "KIPDEV", "KIP1TEST", benefitNameNew, "Bring a pet to work", todaysDate]
	Forecast forecastNew = GenerateForecast.createForecast(newForecastNew)
	component.createForecastAndApprove(forecastNew)
	
	String[] newBenefitActualNew = [newDateNew, projectNew.name, benefitNameNew + " | Bring a pet to work" , "150"]
	BenefitActual benefitActualNew = GenerateBenefitActual.createBenefitActual(newBenefitActualNew)
	
	
Rep.nextTestStep("Select [benefitActuals] from search filter")
	Nav.selectSearchFilter("Benefit Actuals")
	

Rep.nextTestStep("Click onto [Inline menu] against a currency")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(6, benefitActual.project)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Change Project in Project Field")
	gObj.selectComboboxValue("project", "project", benefitActualNew.project, benefitActualNew.project)
	

Rep.nextTestStep("Date - Change DateField")
	WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
	dpck.setDate(arrDateNew[0], arrDateNew[1], arrDateNew[2])
	

Rep.nextTestStep("Benefit - Select Benefit")
	gObj.selectComboboxValue("benefit", "benefit", benefitActualNew.benefit, benefitActualNew.benefit)
	

Rep.nextTestStep("Currency - Select Currency")
	gObj.selectComboboxValue("currency", "currency", currency.name, currency.name)


Rep.nextTestStep("Amount - Update amount")
	gObj.setEditSync(Const.kip4BenefitActual + "input_Amount", benefitActualNew.amount)

	
Rep.nextTestStep("Click 'Save and Close'")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()	

	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	sVal.searchTableAddedValue(6, benefitActualNew.project)
	
	
Rep.info("Steps to test - 70930")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Inline menu] against a currency")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(6, benefitActualNew.project)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	

Rep.nextTestStep("Change Project in Project Field")
	gObj.selectComboboxValue("project", "project", benefitActual.project, benefitActual.project)
	

Rep.nextTestStep("Click onto [Close]  and [Cancel] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
		
		
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	
	
Rep.nextTestStep("Verify the changes are not updated on the location table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	sVal.searchTableDeletedValue(6, benefitActual.project)

Rep.info("~~~~~~~~~~~~~~~~~~~~~")


	