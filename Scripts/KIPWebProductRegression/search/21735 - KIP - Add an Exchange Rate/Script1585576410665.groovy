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
import buildingBlocks.createComponentsLogedIn as component
import models.ExchangeRate
import models.GenerateExchangeRate
import models.Currency
import models.GenerateCurrency
import search.Validate as sVal

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] newExchangeRate = [currency.code, todaysDate, "1.50"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	
	
Rep.nextTestStep("Click onto [Adminstartion] (top left corner)")
	WebUI.click(findTestObject(Const.mainToolBar + "administration"))
	
	
Rep.nextTestStep("Click onto [Exchange Rates] option")
	WebUI.click(findTestObject(Const.financialsMenu + "a_ExchangeRates"))
	gAct.Wait(1)


Rep.nextTestStep("Click On [+ Add]")
	WebUI.click(findTestObject(Const.columnPicker + "a_AddExchangeRate"))
	
	
Rep.nextTestStep("Select a [Currency] from dropdown")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), exchangeRate.currency)
	WebUI.click(findTestObject(Const.columnPicker + "input_Rate"))
	
	
Rep.nextTestStep("Select a [Start Date]")
	WebUI.setText(findTestObject(Const.columnPicker + "input_ExRateStartDate"), exchangeRate.startDate)

	
Rep.nextTestStep("Add a [Rate]")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), exchangeRate.rate)
	

Rep.nextTestStep("Click onto [Save & Close] button")
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndCloseExchangeRate"))
	gAct.Wait(2)
	

Rep.nextTestStep("Verify the new entered [Exchange Rate] is added within the exchange rate table")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, "00000000-0000-0000-0000-000000000000")

	
Rep.nextTestStep("Verify the [Details] are accurate")
	String actDate = WebUI.getText(findTestObject(Const.columnPicker + "table_ExchangeRate", [('row'): rowNo, ('col'): 3]))
	gAct.compareStringAndReport(actDate, exchangeRate.startDate)
	String actRate = WebUI.getText(findTestObject(Const.columnPicker + "table_ExchangeRate", [('row'): rowNo, ('col'): 4]))
	gAct.compareStringAndReport(actRate, exchangeRate.rate)
	
