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
import global.Validate as gVal
import global.Object as gObj
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import gantt.Action as ganttAct

String browser = Com.getBrowserType()
String forecastName = Com.generateRandomText(5)
String projName = 'Project Innovation'
String forecastNme = 'New Forecast ' + browser + forecastName
String role = "ADMIN"
String dept = "KIP Quality Assurance"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', projName)
	

Rep.nextTestStep("Click onto [Forecasts] tab")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Forecasts", "MVC-PRL1-FORECASTS?")
	

Rep.nextTestStep("Click onto [+Add]  button")
	gObj.buttonClickSync(Const.forecasts + 'a_Add')	
	

Rep.nextTestStep("Enter data into [Project] field - if applicable")
	String project = gObj.getAttributeSync(Const.forecasts + 'projectName', 'value')	
	gAct.compareStringAndReport(project, projName)
	

Rep.nextTestStep("Enter data into [Name] field")
	gObj.setEditSync(Const.forecasts + 'forecastName', forecastNme)	


Rep.nextTestStep("Click [Add a Row] icon from the table displayed (icon next to '>>')")
	gObj.buttonClickSync(Const.forecasts + 'addRowAtEnd')
	gAct.Wait(1)

	
Rep.nextTestStep("Click [Search] icon under the Role column and select a Role")
	gObj.setEditSync(Const.forecasts + "input_ForecastDept", dept)
	gAct.Wait(1)
	gObj.buttonClickSubIntPosSync(Const.forecasts + 'Effort_cell', 13, "cell")
	gObj.setEditSync(Const.forecasts + 'roleName', role)

	
Rep.nextTestStep("Select [Year / Month] from Confirmed Until column")
	def customDate = gObj.getAttributeSync(Const.forecasts + 'customDate', 'value')	
	String confirmDate = gAct.increaseDate(customDate, "dd/MM/yyyy", 60, "dd/MM/yyyy")
	gObj.setEditSync(Const.forecasts + 'confirmUntil', confirmDate)

	
Rep.nextTestStep("Enter data for Effort in the Years columns i.e. under the year 2015, 2016, 2017 where applicable")
	gObj.buttonClickSubIntPosSync(Const.forecasts + 'Effort_cell', 13, "cell")
	gObj.setEditSubIntPosSync(Const.forecasts + 'Effort_cell', 13, "cell", "40")
	gObj.setEditSubIntPosSync(Const.forecasts + 'Effort_cell', 14, "cell", "37")
	gObj.setEditSubIntPosSync(Const.forecasts + 'Effort_cell', 15, "cell", "25")
	gObj.buttonClickSubIntPosSync(Const.forecasts + 'Effort_cell', 16, "cell")
	
	
Rep.nextTestStep("Click through all the tabs within Add forecast window")
	gObj.buttonClickSync(Const.forecasts + 'ExpenditureTab')
	gVal.objectAttributeValue(Const.forecasts + 'ExpenditureTab', 'class', 'tabSelectedPopup')
	gObj.buttonClickSync(Const.forecasts + 'BenefitsTab')
	gVal.objectAttributeValue(Const.forecasts + 'BenefitsTab', 'class', 'tabSelectedPopup')
	gObj.buttonClickSync(Const.forecasts + 'BudgetTab')
	gVal.objectAttributeValue(Const.forecasts + 'BudgetTab', 'class', 'tabSelectedPopup')
	
	
Rep.nextTestStep("Click [Save] or [Save Close]")
	int intCnt
	gObj.buttonClickSync(Const.forecasts + 'button_Save and Close')
	gAct.Wait(2)
	
	int rowCnt = WebD.getTableRowCount("//div[contains(@class,'editableTable tableDiv hasOperations')]")
	
	for(intCnt = 1; intCnt < rowCnt; intCnt ++){
		String savedBy = gObj.getEditTextDblSubIntPosSync(Const.forecasts + 'ForecastTableRow', intCnt, "row", 2, "col")
		if(savedBy == forecastNme){
			break
		}	
	}
	
	String newForecastDetails = gObj.getEditTextDblSubIntPosSync(Const.forecasts + 'ForecastTableRow', intCnt, "row", 2, "col")
	gAct.compareStringAndReport(newForecastDetails, forecastNme)
