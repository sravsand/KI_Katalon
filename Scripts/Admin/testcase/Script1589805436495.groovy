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
	
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', 'Project Innovation_CHROME_DRIVER')
	

	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	def weekSt = Com.weekStartDate()
	String todaysDate = today.format(pattern)
	def oldDate = gAct.decreaseDate(todaysDate, pattern, 7, pattern)
	
	String[] arrDate = oldDate.split('/')
	int day = arrDate[0].toInteger()
	int month = arrDate[1].toInteger()
	int year = arrDate[2].toInteger()



	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	WebUI.click(findTestObject(Const.mainToolBar + "administration"))

	
Rep.nextTestStep("Click over [Exchange rate]")
	WebUI.click(findTestObject(Const.financialsMenu + "a_ExchangeRates"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [Inline Menu] against ANY rate but the OLDEST active [Exchange Rate] for a specific [Currency]")

	WebUI.click(findTestObject(Const.kip4ExchRate + "input_EffectiveDate"))
	WebUI.setText(findTestObject(Const.kip4ExchRate + "input_EffectiveDate"), oldDate)
	WebUI.click(findTestObject(Const.kip4ExchRate + "a_Search"))

gAct.Wait(1)
