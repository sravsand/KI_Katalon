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

String tableName = "00000000-0000-0000-0000-000000000000"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	String clonedCurrencyName = Com.generateRandomText(8)
	String clonedCurrencyCode = clonedCurrencyName.toUpperCase()
	clonedCurrencyCode = clonedCurrencyCode.take(3)
	
	String[] clonedCurrency = [clonedCurrencyName, clonedCurrencyCode, "€"]
	Currency newClonedCurrency = GenerateCurrency.createCurrency(clonedCurrency)
	component.createCurrency(newClonedCurrency)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] newExchangeRate = [currency.code, todaysDate, "1.50"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate)
	
	String[] clonedExchangeRate = [newClonedCurrency.code, todaysDate, "2.50"]
	ExchangeRate newClonedExchangeRate = GenerateExchangeRate.createExchangeRate(clonedExchangeRate)
	
	
Rep.nextTestStep("Click onto [Adminstartion] (top left corner)")
	WebUI.click(findTestObject(Const.mainToolBar + "administration"))
	
	
Rep.nextTestStep("Click onto [Exchange Rates] option")
	WebUI.click(findTestObject(Const.financialsMenu + "a_ExchangeRates"))
	gAct.Wait(1)


Rep.nextTestStep("Click onto [Inline] menu of an exchange rate")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_ExchgRate', [('table'): tableName, ('row'): rowNo]))
	
	
Rep.nextTestStep("Click onto [Clone]")
	WebUI.click(findTestObject(Const.columnPicker + "a_InlineMenuPositionSelection", [('pos'): 2]))
	gAct.Wait(1)
	
	WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), newClonedExchangeRate.currency)
	WebUI.click(findTestObject(Const.columnPicker + "input_Rate"))
	WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), newClonedExchangeRate.rate)
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndCloseExchangeRate"))
	gAct.Wait(1)
	
	sVal.searchTableAddedValueSpecificTable(2, clonedCurrencyName, tableName)

	