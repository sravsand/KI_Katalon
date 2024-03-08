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
import models.Currency
import models.GenerateCurrency
import models.ExpenseType
import models.GenerateExpenseType
import models.Timesheet
import models.GenerateTimesheet
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre
import models.Login
import models.GenerateLogin

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String expenseName = Com.generateRandomText(10)
	String expenseCode = expenseName.toUpperCase()
	
	String[] newExpenseType = [expenseName, expenseCode, "", "1", "true", "5.00"]
	ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)
	
	component.createExpenseType(expenseType)
	
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
	
	String browser = Com.getBrowserType()
	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	String notes = "Test Approve"
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 4, "dd/MM/yyyy")
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation_" + browser, "Project Management", "Project Plan"]
	//String[] newTimesheet = ["Project Innovation_" + browser, "Project Management", "Project Plan"]
	String[] newHours = ["8.00", notes, "7.50", notes, "8.00", notes, "8.00", notes, "8.00", notes, "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	component.submitTimesheet_SpecificUser(timesheet, resource)
	
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, '^']
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	String newCurrencyName = Com.generateRandomText(8)
	String newCurrencyCode = newCurrencyName.toUpperCase()
	newCurrencyCode = newCurrencyCode.take(3)
	
	String[] newCurrencyAdd = [newCurrencyName, newCurrencyCode, '@']
	Currency nextCurrency = GenerateCurrency.createCurrency(newCurrencyAdd)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.financialsMenu + "a_Currencies")

	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Currency + "input_Symbol", nextCurrency.symbol)	
	
	
Rep.info("Steps to test - 54403")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	
	boolean codeError = gObj.elementPresentSync(Const.kip4Currency + 'name_message')
	if(codeError){
		String errtext = gObj.getEditTextSync(Const.kip4Currency + "name_message")
		gAct.compareStringAndReport(errtext, "Name is required")
	}
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Add a [Name] ")
	gObj.setEditSync(Const.kip4Generic + "input_Name", currency.name)

	
Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", currency.code)
	

Rep.nextTestStep("Select [Active] checkbox")

	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
	
Rep.nextTestStep("Enter text in [Symbol] field (Required)")
	gObj.setEditSync(Const.kip4Currency + "input_symbol", nextCurrency.symbol)


Rep.nextTestStep("Attempt to check [System Currency] checkbox.")
	def sysCurrency = WebD.getCheckBoxState("//input[@id='systemCurrency']")
	gAct.compareStringAndReport(sysCurrency, null)
	gObj.buttonClickSync(Const.kip4Currency + "chk_SystemCurrency")
	
	sysCurrency = WebD.getCheckBoxState("//input[@id='systemCurrency']")
	gAct.compareStringAndReport(sysCurrency, null)
	gObj.verifyAttributeSync(Const.kip4Currency + "chk_SysCurr", 'disabled')
		

Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	gAct.Wait(1)
	boolean errMessCode = gObj.elementPresentSync(Const.kip4Currency + "err_Curr")
	
	if(errMessCode){
		errMessage = gObj.getEditTextSync(Const.kip4Currency + "err_Curr")
		gAct.findSubstringInStringAndReport(errMessage, "The code already exists")
	}
	
	
Rep.info("Steps to test - 54405")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")	

Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", nextCurrency.code)

	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Accept alert and check location not created")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	sVal.searchTableDeletedValue(2, nextCurrency.code)
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
	