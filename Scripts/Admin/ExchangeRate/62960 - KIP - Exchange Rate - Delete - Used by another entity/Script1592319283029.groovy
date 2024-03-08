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
import buildingBlocks.createComponentsLogedIn as component
import buildingBlocks.createComponentsLogedIn_AdminNew as admComp
import models.ExchangeRate
import models.GenerateExchangeRate
import models.Currency
import models.GenerateCurrency
import models.ExpenseWithCurrency
import models.GenerateExpenseWithCurrency
import models.Category
import models.GenerateCategory

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
	admComp.createCurrency(currency)
	
	def pattern = "dd/M/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] newExchangeRate = [currency.code, todaysDate, "4.5"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	admComp.createExchangeRate(exchangeRate, currency)
	
	
	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String expenseNotes = Com.generateRandomText(16)
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	
	admComp.createCategory(category)
	
	String newDate = Com.todayDate()
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", category.code, true, "16.00", "", "16.00", false, expenseNotes, currency.code]
	ExpenseWithCurrency expense = GenerateExpenseWithCurrency.createExpenseWithCurrency(newExpense)
	component.createExpenseWithCurrency(expense)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")
	
	
Rep.nextTestStep("Click onto [Inline] menu of an exchange rate")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")
	

Rep.nextTestStep("Click onto [delete]")
	gObj.selectInlineOption(3)
	
	
Rep.nextTestStep("Click onto [delete]")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK")
	
	boolean modalError = gObj.elementPresentSync(Const.kip4ExchRate + "msg_DeleteExchangeRate")
	if(modalError){
		String errtext = gObj.getEditTextSync(Const.kip4ExchRate + "msg_DeleteExchangeRate")
		gAct.findSubstringInStringAndReport(errtext, "At least one Expense refers to it")
	}
	
	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_Cancel")
	
	
Rep.nextTestStep("Verify the new entered [Exchange Rate] is added within the exchange rate table")
	int rowNum = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	
	if(rowNum > 0){
		Rep.pass("The Exchange rate " + currencyName + " has not been deleted")
	}else{
		Rep.fail("The Exchange rate " + currencyName + " has been deleted")
	}
