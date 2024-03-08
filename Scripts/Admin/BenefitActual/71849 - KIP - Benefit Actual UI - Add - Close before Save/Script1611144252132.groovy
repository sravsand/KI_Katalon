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

	String newDate = Com.todayDate()	
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
	
	String[] newForecast = [forecastName, "Project Innovation_CHROME_DRIVER", "KIPDEV", "KIP1TEST", benefitName, "Reduction in Costs", todaysDate]
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
	
	String[] newBenefitActual = [todaysDate, "Project Innovation_CHROME_DRIVER", benefitName + " | Reduction in Costs" , "100"]
	BenefitActual benefitActual = GenerateBenefitActual.createBenefitActual(newBenefitActual)
	
	
Rep.nextTestStep("Select [benefitActuals] from search filter")
	Nav.selectSearchFilter("Benefit Actuals")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Select Project")
	gObj.selectComboboxValue("project", "project", benefitActual.project, benefitActual.project)
	
	
Rep.nextTestStep("Select Date")
	WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
	dpck.setDate(arrDate[0], arrDate[1], arrDate[2])
	

Rep.nextTestStep("Select Benefit")
	gObj.selectComboboxValue("benefit", "benefit", benefitActual.benefit, benefitActual.benefit)

	
Rep.nextTestStep("Select currency")
	gObj.selectComboboxValue("currency", "currency", currency.name, currency.name)


Rep.nextTestStep("Enter non-numeric text, ie - 'testAmount'")
	String amt_Err = Com.generateRandomText(10)
	gObj.setEditSync(Const.kip4BenefitActual + "input_Amount", amt_Err)
	gObj.buttonClickSync(Const.kip4BenefitActual + "heading_AddBenefitActual")
	gObj.elementVisibleSubSync(Const.kip4BenefitActual + "amount_Error", amt_Err, "err")

	
Rep.nextTestStep("Enter valid Amount")
	gObj.setEditSync(Const.kip4BenefitActual + "input_Amount", benefitActual.amount)
	WebUI.verifyElementNotPresent(findTestObject(Const.kip4BenefitActual + "amount_Error", [('err') : amt_Err]), GVars.shortWait)
	

Rep.info("Steps to test - 70923")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close]  and [Cancel] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
	
	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
		
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Select Project")
	gObj.selectComboboxValue("project", "project", benefitActual.project, benefitActual.project)
	
	
Rep.nextTestStep("Select Date")
	WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
	dpck.setDate(arrDate[0], arrDate[1], arrDate[2])
	

Rep.nextTestStep("Select Benefit")
	gObj.selectComboboxValue("benefit", "benefit", benefitActual.benefit, benefitActual.benefit)

	
Rep.nextTestStep("Select currency")
	gObj.selectComboboxValue("currency", "currency", currency.name, currency.name)

	
Rep.nextTestStep("Enter valid Amount")
	gObj.setEditSync(Const.kip4BenefitActual + "input_Amount", benefitActual.amount)
	
		
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	gObj.createNewFilter("Benefit Name", "=", benefitName)
 	sVal.searchTableAddedValue(6, benefitActual.project)
	
