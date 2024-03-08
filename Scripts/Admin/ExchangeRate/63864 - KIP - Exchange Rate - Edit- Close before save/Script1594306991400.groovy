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
	
	String[] newExchangeRate = [currency.code, todaysDate, "1.50"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate, currency)
	
	String ExRateNew = "5.50"
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Exchange rate]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")

	
Rep.nextTestStep("Click onto [Inline] menu of an exchange rate")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")
	

Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)


Rep.info("Steps to test - 57259")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton", 'disabled')
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
		
Rep.nextTestStep("Enter [Rate] as a numerical/decimal figure")
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", ExRateNew)
	
			
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	

Rep.nextTestStep("Click onto [continue without saving] button")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	
		
Rep.nextTestStep("Verify the new entered [Exchange Rate] is added within the exchange rate table")
	int rowNum = sVal.searchSpecificTableReturnRow(2, currency.name, tableName)

	
Rep.nextTestStep("Verify the [Details] are accurate")
	String actRate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "table_ExchangeRate", rowNum, "row", 4, "col")
	gAct.compareStringAndReport(actRate, exchangeRate.rate)
