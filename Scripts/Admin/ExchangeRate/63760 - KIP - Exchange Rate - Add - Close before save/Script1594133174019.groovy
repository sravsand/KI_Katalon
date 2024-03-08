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
import models.ExchangeRate
import models.GenerateExchangeRate
import models.Currency
import models.GenerateCurrency

String tableName = "00000000-0000-0000-0000-000000000000"

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€", "true"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	def pattern = "d/M/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String[] arrDate = todaysDate.split('/')
	
	String[] newExchangeRate = [currency.code, todaysDate, "1.50"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Exchange rate]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")

	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")


Rep.info("Steps to test - 63760")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton", 'disabled')
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Click [Currency] dropdown")
	gObj.selectComboboxValue("currency", "currency", currency.name, currency.name)
	
	
Rep.nextTestStep("Click [Start Date] Calendar button and add start date")
	WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
	dpck.setDate(arrDate[0], arrDate[1], arrDate[2])
	
	
Rep.nextTestStep("Enter [Rate] as a numerical/decimal figure")
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", exchangeRate.rate)
	
	
Rep.info("Steps to test - 61962")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	gObj.checkSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Expenses with the ammended rate")
	def tmeNom = WebD.getCheckBoxState("//input[@id='adjustExpenses']")
	gAct.compareStringAndReport(tmeNom, "true")
	gObj.checkSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Timesheets with the ammended rate")
	def expNom = WebD.getCheckBoxState("//input[@id='adjustTimesheets']")
	gAct.compareStringAndReport(expNom, "true")
	
	gObj.buttonClickSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Expenses with the ammended rate")
	def tmeNom2 = WebD.getCheckBoxState("//input[@id='adjustExpenses']")
	gAct.compareStringAndReport(tmeNom2, null)
	gObj.buttonClickSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Timesheets with the ammended rate")
	def expNom2 = WebD.getCheckBoxState("//input[@id='adjustTimesheets']")
	gAct.compareStringAndReport(expNom2, null)
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
		
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	

Rep.nextTestStep("Click onto [ContinueWithoutSaving] button")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	
		
Rep.nextTestStep("Verify the new entered [Exchange Rate] is not added within the exchange rate table")
	sVal.searchTableDeletedValueSpecificTable(2, currencyName, tableName)
