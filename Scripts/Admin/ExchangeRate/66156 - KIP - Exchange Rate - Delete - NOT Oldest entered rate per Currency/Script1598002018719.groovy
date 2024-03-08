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
import models.Charge
import models.GenerateCharge
import models.Timesheet
import models.GenerateTimesheet
import models.TimesheetRow
import models.GenerateTimesheetRow
import models.Project
import models.GenerateProject
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String tableName = "00000000-0000-0000-0000-000000000000"
String searchItem = "Timesheets"

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', 'Project Innovation_CHROME_DRIVER')
	
	String code = Com.generateRandomText(10)
	code = code.toUpperCase()
	String firstName = Com.generateRandomText(5)
	String surname = Com.generateRandomText(8)
	String name = firstName + " " + surname
	String userNme = firstName.take(1) + surname + "@workflows.com"
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = [name, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", userNme, "", "100", "", "", "", "", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)
	component.createResource(resource)
	
	String[] newLogin = [name, code, userNme, resource.code, "TEst1234"]
	Login login = GenerateLogin.createLogin(newLogin)
	component.createLogin(login, "Administration")
		
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€", "true"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	def pattern = "dd/M/yyyy"
	Date today = new Date()
	def weekSt = Com.weekStartDateFormat(pattern)
	String todaysDate = today.format(pattern)
	def oldDate = gAct.decreaseDate(todaysDate, pattern, 7, pattern)
	
	String[] oldExchangeRate = [currency.code, oldDate, "1.50"]
	ExchangeRate exchangeRateOld = GenerateExchangeRate.createExchangeRate(oldExchangeRate)
	component.createExchangeRate(exchangeRateOld, currency)
	
	String[] newExchangeRate = [currency.code, weekSt, "1.75"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate, currency)
	
	String chargeName = Com.generateRandomText(8)
	String chargeCode = chargeName.toUpperCase()
	
	String[] newCharge = [chargeName, chargeCode, "5.00", currency.name]
	Charge charge = GenerateCharge.createCharge(newCharge)
	component.createCharge(charge)
	
	String browser = Com.getBrowserType()
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 4, "dd/MM/yyyy")
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Tech Service Delivery", "Project Management", "Project Plan"]
	String[] newHours = ["8.00", "Test Notes", "7.50", "", "8.00", "", "8.00", "", "8.00", "Some more notes", "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	component.submitTimesheetWithCharge_SpecificUser(timesheet, charge.name, login)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Exchange rate]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")
	
	
Rep.nextTestStep("Click onto [Inline Menu] against ANY rate but the OLDEST active [Exchange Rate] for a specific [Currency]")
	int rowNum = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	String rateVal = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", tableName, "tab", rowNum, "row", 4, "col")
	gAct.compareStringAndReport(rateVal, exchangeRate.rate)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNum, "row")
	

Rep.nextTestStep("Click onto [delete]")
	gObj.selectInlineOption(3)
	

Rep.nextTestStep("[Exchange Rate] details are displayed in READONLY mode BUT the 'Adjust Timesheet/Expense...' checkboxes are functional")
	gObj.verifyAttributeSync(Const.kip4ExchRate + "input_Currency", 'disabled')
	gObj.verifyAttributeSync(Const.kip4ExchRate + "input_StartDateDisabled", 'disabled')
	gObj.verifyAttributeSync(Const.kip4ExchRate + "input_rate", 'disabled')

	
Rep.nextTestStep("Select either/ both of the 'Adjust Timesheet/Expense...' checkboxes")
	gObj.checkSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Timesheets with the ammended rate")
	gObj.checkSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Expenses with the ammended rate")

		
Rep.nextTestStep("Select Delete button")
	gObj.buttonClickSync(Const.kip4ExchRate + "button_OK inserted")
	

Rep.nextTestStep("Ensure that Timesheets/Expenses that were referenced by the deleted [Exchange Rate] have are now using the next available [Exchange Rate]")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	String rateValNew = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", tableName, "tab", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(rateValNew, exchangeRateOld.rate)
	
	