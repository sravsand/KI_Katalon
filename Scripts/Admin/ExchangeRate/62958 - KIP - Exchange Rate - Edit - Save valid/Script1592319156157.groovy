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
	String todaysDateFmt = today.format("dd/MM/yyyy")
	
	String[] newExchangeRate = [currency.code, todaysDate, "4.5"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate, currency)
	
	String ExRateNew = "5.50"
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")
	
	
Rep.nextTestStep("Click onto [Inline] menu of an exchange rate")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")
	

Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.info("Steps to test - 57252")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	String currText = gObj.getEditTextSync(Const.kip4ExchRate + "select_currencyControl")
	gAct.compareStringAndReport(currText, currency.name)
	
	String currDis = gObj.verifyAttributeSync(Const.kip4ExchRate + "select_currencyControl", 'disabled')
	gAct.compareStringAndReport(currDis, "true")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	
Rep.info("Steps to test - 57253")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	String newDate = gObj.getAttributeSync(Const.kip4ExchRate + "input_StartDate", 'value')
	gAct.compareStringAndReport(newDate, todaysDateFmt)

	String dateStatus = gObj.getAttributeSync(Const.kip4ExchRate + "input_StartDate", 'disabled')
	gAct.compareStringAndReport(dateStatus, "true")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.info("Steps to test - 57254")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	WebUI.verifyElementPresent(findTestObject(Const.kip4ExchRate + 'label_Rate'), GVars.midWait)
	String strRate = gObj.getAttributeSync(Const.kip4ExchRate + "input_rate", 'value')
	gAct.compareStringAndReport(strRate, exchangeRate.rate)
	
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", "test")
	String saveDis = gObj.verifyAttributeSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton", 'disabled')

	String errText = "1111111111111"
	String trucVal = "111111111111.00"
	
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", errText)
	gAct.Wait(GVars.shortWait)
	
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	
	Act.verifySavePopUpText()

	int rowNum = sVal.searchSpecificTableReturnRow(2, currencyName, "00000000-0000-0000-0000-000000000000")
	
	String actRateVal = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "table_ExchangeRate", rowNum, "row", 4, "col")
		
	gAct.compareStringAndReport(actRateVal, trucVal)
	
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")

	gObj.selectInlineOption(1)
	
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", "11")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.info("Steps to test - 57256")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Enter new [Rate] as a numerical/decimal figure")
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", ExRateNew)
	

Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()

	
Rep.nextTestStep("Verify the new entered [Exchange Rate] is added within the exchange rate table")
	int rowN = sVal.searchSpecificTableReturnRow(2, currency.name, tableName)

	
Rep.nextTestStep("Verify the [Details] are accurate")
	String actRate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "table_ExchangeRate", rowN, "row",  4, "col")
	gAct.compareStringAndReport(actRate, ExRateNew)
	
	
Rep.info("Steps to test - 57260")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	String textVal = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
	if(textVal != "10"){
		gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	}
	
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowN, "row")
			
	gObj.selectInlineOption(1)
			
	gObj.elementPresentSync(Const.kip4ExchRate + 'edit_LastEditInfo')
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	