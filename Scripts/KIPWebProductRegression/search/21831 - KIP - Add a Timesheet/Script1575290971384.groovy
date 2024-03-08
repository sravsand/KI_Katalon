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
import models.Timesheet
import models.GenerateTimesheet


//String browser = Com.getBrowserType()
def pattern = "dd/MM/yyyy"
def weekCommence = Com.weekStartDate()
def date = Date.parse(pattern, weekCommence)

def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 4, "dd/MM/yyyy")
def thisWeek = Com.weekDates()
String[] newTimesheet = ["PRJ_AUT029 | Project Innovation_" + GVars.selectedBrowser, "PROJ_MANAGEMENT | Project Management", "1 - Project Plan"]
String[] newHours = ["8.00", "Test Notes", "7.50", "", "8.00", "", "8.00", "", "8.00", "Some more notes", "", "", "", ""]
Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)

String searchItem = "Timesheets"
String searchVal = "Filter"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Timesheets] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	

Rep.nextTestStep("Search & Select [Resource]")
	gObj.buttonClickSync(Const.newTimesheet + "input_Resource")
	gObj.setEditSync(Const.newTimesheet + "input_Resource", GVars.user)
	
	
Rep.nextTestStep("Select [From Date]")
	gObj.setEditSync(Const.newTimesheet + "input_FromDate", weekCommence)
	gObj.buttonClickSync(Const.newTimesheet + "input_Resource")
	

Rep.nextTestStep("Select [To Date]")
	gObj.setEditSync(Const.newTimesheet + "input_ToDate", weekEnd)
	gObj.buttonClickSync(Const.newTimesheet + "input_Resource")


Rep.nextTestStep("Select a [Project] from dropdown")
	gObj.selectComboByLabelSync(Const.newTimesheet + "select_Project", newTimesheet[0])
	
	
Rep.nextTestStep("Select [Activity] from dropdown")	
	gObj.selectComboByLabelSync(Const.newTimesheet + "select_Activity", newTimesheet[1])


Rep.nextTestStep("Select [Task] from dropdown")
	gObj.selectComboByLabelSync(Const.newTimesheet + "select_Task", newTimesheet[2])


Rep.nextTestStep("Enter [Hours]")
	gObj.setEditSync(Const.newTimesheet + "input_Hours", "8")


Rep.nextTestStep("Select the [Days] through checkbox")
	gObj.setEditSync(Const.newTimesheet + "input_Minutes", "50")


Rep.nextTestStep("Click [Save]")
	gObj.buttonClickSync(Const.newTimesheet + "button_Save")
	
	
Rep.nextTestStep("Verify the newly added Timesheet added onto the table")	
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gObj.buttonClickSync(Const.timesheetFilter + "select_DwnArrow")
	gObj.setEditSync(Const.timesheetFilter + "input_FieldValue","Resource Name")
	gObj.buttonClickSubSync(Const.timesheetFilter + 'select_FieldSelection', "Resource Name", "choice")
	
	gObj.buttonClickSync(Const.timesheetFilter + "comparison_Arrow")
	gObj.buttonClickSync(Const.timesheetFilter + "comparison_Equals")

	gObj.setEditSync(Const.timesheetFilter + "input_Value", GVars.user)
	gObj.buttonClickSync(Const.timesheetFilter + "a_Add Row")
	gObj.buttonClickSync(Const.timesheetFilter + "a_Select_Alt")
	gObj.setEditSync(Const.timesheetFilter + "input_FieldValue_2","Timesheet Date")
	gObj.buttonClickSubSync(Const.timesheetFilter + 'select_FieldSelection', "Timesheet Date", "choice")
	
	gObj.buttonClickSync(Const.timesheetFilter + "comparison_Arrow")
	gObj.buttonClickSync(Const.timesheetFilter + "comparison_Equals")
	
	gObj.buttonClickSync(Const.timesheetFilter + "input_Value2")
	
//	Date today = new Date()
//	String todaysDate = today.format(pattern)
	
	WebD.setEditText("//td[@id='valueCol1']//input[@id='valueField']", weekCommence)
	gObj.buttonClickSync(Const.timesheetFilter + 'a_Search')
	
	sVal.searchTableAddedValue(4, GVars.user)
