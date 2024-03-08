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


def testStartDate = Com.weekDatesDifferentFormat(1, "back", "dd/M/yyyy")

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
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	def weekSt = Com.decreaseWorkingCalendarWithFormat(today, 14, pattern)
//	def weekSt = Com.weekStartDate()
	String todaysDate = today.format(pattern)
	
	String[] newExchangeRate = [currency.code, testStartDate, "1.00"]
	ExchangeRate exchangeRate = GenerateExchangeRate.createExchangeRate(newExchangeRate)
	component.createExchangeRate(exchangeRate, currency)
	
	String chargeName = Com.generateRandomText(8)
	String chargeCode = chargeName.toUpperCase()
	
	String[] newCharge = [chargeName, chargeCode, "5.00", currency.name]
	Charge charge = GenerateCharge.createCharge(newCharge)
	component.createCharge(charge)
	
	String browser = Com.getBrowserType()
	def thisWeek = Com.weekDatesDifferent_StartDate(1, "back")
	String[] newTimesheet = ["Tech Service Delivery", "Project Management", "Project Plan"]
	String[] newHours = ["8.00", "Test Notes", "7.50", "", "8.00", "", "8.00", "", "8.00", "Some more notes", "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	component.submitTimesheetWithCharge_SpecificUser(timesheet, charge.name, login)
	
	String newRate = "2.00"
	
	
Rep.nextTestStep("Select [Timesheets] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	
Rep.nextTestStep("Ensure [Exchange Rate] is applied to [Timesheets] FROM the 'Start Date' of said [Exchange Rate]")
	gObj.createNewFilter("Resource Name", "=", login.name)

	
Rep.nextTestStep("Add charge rate amount column and check value")
	WebD.clickElement("//a[@class='menuChooser']")
	gObj.buttonClickSync(Const.columnPicker + "a_Reset to Default")
	WebD.clickElement("//a[@class='menuChooser']")
	gObj.buttonClickSync(Const.columnPicker + "a_Column Chooser")
	gAct.Wait(2)
	WebUI.dragAndDropToObject(findTestObject(Const.columnPicker + "col_ChargeRateAmount"), findTestObject(Const.columnPicker + "col_Department"))
	gObj.buttonClickSync(Const.columnPicker + "button_Save")
	String chgeRate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", 1, "row", 7, "col")
	gAct.compareStringAndReport(chgeRate, "5.00")
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Exchange rate]")
	gObj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")

	
Rep.nextTestStep("Click onto [Inline] menu of an exchange rate")
	int rowNo = sVal.searchSpecificTableReturnRow(2, currencyName, tableName)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")
	

Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Check [Currency] cannot be amended")
	WebUI.verifyElementHasAttribute(findTestObject(Const.kip4ExchRate + "input_CurrencyDisabled"), 'disabled', GVars.midWait)

	
Rep.nextTestStep("Check [Start Date] cannot be amended")
	WebUI.verifyElementHasAttribute(findTestObject(Const.kip4ExchRate + "input_StartDateDisabled"), 'disabled', GVars.midWait)

	
Rep.nextTestStep("Enter new [Rate] as a numerical/decimal figure")
	gObj.setEditSync(Const.kip4ExchRate + "input_rate", newRate)
	

Rep.nextTestStep("Check the 'Adjust existing non-Invoiced Timesheets with the new rate' box")
	gObj.checkSync(Const.kip4ExchRate + "chk_Adjust existing non-invoiced Timesheets with the ammended rate")

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the [Expense Category] table")
	int rowNum = sVal.searchSpecificTableReturnRow(2, currency.name, tableName)
	
		
Rep.nextTestStep("Verify the [Details] are accurate")
	String actRate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "table_ExchangeRate", rowNum, "row", 4, "col")
	gAct.compareStringAndReport(actRate, newRate)


Rep.nextTestStep("Select [Timesheets] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Ensure [Exchange Rate] is applied to [Timesheets] FROM the 'Start Date' of said [Exchange Rate]")
	gObj.createNewFilter("Resource Name", "=", login.name)
	String newChgeRate = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", 1, "row", 7, "col")
	gAct.compareStringAndReport(newChgeRate, "2.50")
	
	
Rep.nextTestStep("Click on [...] from menu bar ")
	WebD.clickElement("//a[@class='menuChooser']")
	gObj.buttonClickSync(Const.columnPicker + "a_Column Chooser")
	gObj.elementPresentSync(Const.columnPicker + "col_ChargeRateAmount")
	WebUI.dragAndDropToObject(findTestObject(Const.columnPicker + "col_ChargeRateAmount"), findTestObject(Const.columnPicker + "col_Charge"))
	gObj.buttonClickSync(Const.columnPicker + "button_Save")
	
//not sure that the new exchange rate is applied
