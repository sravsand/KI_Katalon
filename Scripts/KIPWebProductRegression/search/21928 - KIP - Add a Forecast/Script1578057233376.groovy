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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory

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
import models.Forecast
import models.GenerateForecast

String forecastName = Com.generateRandomText(10)

String[] newForecast = [forecastName, "Project Innovation_" + GVars.selectedBrowser, "KIPDEV", "KIP1TEST", "", "", ""]
Forecast forecast = GenerateForecast.createForecast(newForecast)

String searchItem = "Forecasts"
String searchVal = "Forecasts"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Forecasts] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	
	
Rep.nextTestStep("Search & Select [Project]")
	gObj.setEditSync(Const.forecasts + 'projectName', forecast.project)


Rep.nextTestStep("Add text in [Name] field")
	gObj.setEditSync(Const.forecasts + 'forecastName', forecast.name)


Rep.nextTestStep("Add a row by clicking on [Add a Row at the End] from table menu bar")
	gObj.buttonClickSync(Const.forecasts + 'addRowAtEnd')


Rep.nextTestStep("Search & Select [Department]")
	WebUI.switchToFrame(findTestObject(Const.forecasts + 'iframe'), 10)
	WebD.setEditText("//input[@id='pDD0']", forecast.department)
	WebUI.switchToDefaultContent()
	gObj.buttonClickSubIntPosSync(Const.forecasts + 'Effort_cell', 13, "cell")
	
	
Rep.nextTestStep("Search & Select [Role]")
	gObj.setEditSync(Const.forecasts + 'roleName', forecast.role)


Rep.nextTestStep("Select [Year / Month] from Confirmed Until column")
	def customDate = gObj.getAttributeSync(Const.forecasts + 'customDate', 'value')
	String confirmDate = gAct.increaseDate(customDate, "dd/MM/yyyy", 90, "dd/MM/yyyy")
	gAct.Wait(2)
	if(GVars.browser == "Internet explorer"){ //|| GVars.browser == "Chrome"){
		WebUI.focus(findTestObject(Const.forecasts + 'confirmUntil'))
	}
	gObj.setEditSync(Const.forecasts + 'confirmUntil', confirmDate)


Rep.nextTestStep("Add [5] for all the months in table")
	gObj.buttonClickSubIntPosSync(Const.forecasts + 'Effort_cell', 13, "cell")
	gObj.setEditSubIntPosSync(Const.forecasts + 'Effort_cell', 13, "cell", "5")
	gObj.setEditSubIntPosSync(Const.forecasts + 'Effort_cell', 14, "cell", "5")
	gObj.setEditSubIntPosSync(Const.forecasts + 'Effort_cell', 15, "cell", "5")
	gObj.buttonClickSubIntPosSync(Const.forecasts + 'Effort_cell', 16, "cell")


Rep.nextTestStep("Click through all the tabs within Add forecast window")
	gObj.buttonClickSync(Const.forecasts + 'ExpenditureTab')
	gVal.objectAttributeValue(Const.forecasts + 'ExpenditureTab', 'class', 'tabSelectedPopup')
	gObj.buttonClickSync(Const.forecasts + 'BenefitsTab')
	gVal.objectAttributeValue(Const.forecasts + 'BenefitsTab', 'class', 'tabSelectedPopup')
	gObj.buttonClickSync(Const.forecasts + 'BudgetTab')
	gVal.objectAttributeValue(Const.forecasts + 'BudgetTab', 'class', 'tabSelectedPopup')


Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.forecasts + 'button_Save and Close')
	gAct.Wait(GVars.shortWait)


Rep.nextTestStep("Verify recently added Forecast is available for selection in the table")
	gObj.buttonClickSync(Const.columnPicker + "a_All Forecasts")
	sVal.searchTableAddedValue(2, forecast.name)

