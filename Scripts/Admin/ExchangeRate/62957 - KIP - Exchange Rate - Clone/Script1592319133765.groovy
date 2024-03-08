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
	
	String clonedCurrencyName = Com.generateRandomText(8)
	String clonedCurrencyCode = clonedCurrencyName.toUpperCase()
	clonedCurrencyCode = clonedCurrencyCode.take(3)
	
	String[] clonedCurrency = [clonedCurrencyName, clonedCurrencyCode, "€", "true"]
	Currency newClonedCurrency = GenerateCurrency.createCurrency(clonedCurrency)
	component.createCurrency(newClonedCurrency)
	
	def pattern = "d/M/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String todaysDateFmt = today.format("dd/MM/yyyy")
	String newDate = gAct.increaseDate(todaysDate, pattern, 7, pattern)
	String newDateFormat = gAct.increaseDate(todaysDate, "dd/MM/yyyy", 7, "dd/MM/yyyy")
		
	String[] newExchangeRate = [currency.code, todaysDate, "1.5"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate, currency)
	
	String[] clonedExchangeRate = [newClonedCurrency.code, newDate, "2.50"]
	ExchangeRate newClonedExchangeRate = GenerateExchangeRate.createExchangeRate(clonedExchangeRate)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")
	
	
Rep.nextTestStep("Click onto [Inline] menu of an exchange rate")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")
	

Rep.nextTestStep("Click onto [Clone]")
	gObj.selectInlineOption(2)

	
Rep.nextTestStep("Ensure fields are prepopulated with data from the CLONED [Exchange Rate]")
	String staDate = gObj.getAttributeSync(Const.kip4ExchRate + "input_StartDate",'value')
	gAct.compareStringAndReport(staDate, todaysDateFmt)
	String exRate = gObj.getAttributeSync(Const.kip4ExchRate + "input_rate", 'value')
	gAct.compareStringAndReport(exRate, exchangeRate.rate)
	

Rep.nextTestStep("Click [Currency] dropdown")
	gObj.selectComboboxValue("currency", "currency", newClonedCurrency.name, newClonedCurrency.name)

	
Rep.nextTestStep("Click [Start Date] Calendar button and add start date")
	String[] arrDate = newDate.split('/')
	WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
	dpck.setDate(arrDate[0], arrDate[1], arrDate[2])
	

Rep.nextTestStep("Optional: Enter [Rate] as a numerical/decimal figure")
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", newClonedExchangeRate.rate)


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the new entered [Exchange Rate] is added within the exchange rate table")
	gObj.buttonClickSync(Const.kip4ExchRate + "input_EffectiveDate")
	gObj.setEditSync(Const.kip4ExchRate + "input_EffectiveDate", newDate)
	gObj.buttonClickSync(Const.kip4ExchRate + "a_Search")
	
	int rowNum = sVal.searchSpecificTableReturnRow(2, clonedCurrencyName, tableName)

	
Rep.nextTestStep("Verify the [Details] are accurate")
	String actDate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "table_ExchangeRate", rowNum, "row", 3, "col")
	gAct.compareStringAndReport(actDate, newDateFormat)
	String actRate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "table_ExchangeRate",  rowNum, ,"row", 4, "col")
	gAct.compareStringAndReport(actRate, newClonedExchangeRate.rate)
