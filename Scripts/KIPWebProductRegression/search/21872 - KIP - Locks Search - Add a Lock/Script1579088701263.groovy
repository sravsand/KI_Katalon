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
import search.Validate as sVal
import search.Action as sAct
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Timesheet
import models.GenerateTimesheet
import models.Lock
import models.GenerateLock
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String searchItem = "Locks"
String searchVal = "Lock"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
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
	
	def weekStart = Com.decreaseWorkingCalendarWithFormat(date, 5, "dd/MM/yyyy")
	def endDate = Date.parse(pattern, weekStart)
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(endDate, 4, "dd/MM/yyyy")
	
	def wStart = Com.changeDateFormat(weekStart, pattern, "d MMMM")
	def wEnd = Com.changeDateFormat(weekEnd, pattern, "d MMMM")
	
	String weekDates = wStart + " - " + wEnd
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation_" + browser, "Project Management", "Project Plan"]
	String[] newHours = ["8.00", "Test Notes", "7.50", "", "8.00", "", "8.00", "", "8.00", "Some more notes", "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(weekDates, newTimesheet, newHours)
	component.submitOldTimesheetAndApprove_SpecificUser(timesheet, 1, weekStart, resource)
	
	String[] newLock = [weekStart, weekEnd, true, false, "5", "0"]
	Lock lock = GenerateLock.createLock(newLock)

	
Rep.nextTestStep("Select [Locks] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Select [Start Date] by clicking calander icon")
	gObj.setEditSync(Const.kip4Locks + "input_StartDate", lock.startDate)


Rep.nextTestStep("Select [End Date] by clicking calander icon")
	gObj.setEditSync(Const.kip4Locks + "input_EndDate", lock.endDate)

	
Rep.nextTestStep("Select  [Include Timesheets] through checkbox")
	gObj.checkSync(Const.kip4Locks + "chk_IncludeTimesheets")


Rep.nextTestStep("Select a [Filter] from dropdown list (if no filter available for selection create a new filter)")


Rep.nextTestStep("Click onto [Estimate Lock Counts] (modal footer)")
	gObj.buttonClickSync(Const.kip4Locks + "btn_EstimateLocks")
	
	
	String timesheetsIncluded = gObj.getAttributeSync(Const.kip4Locks + "TimesheetCount", "value")
	int timesheetInt = timesheetsIncluded as Integer
	
	if(timesheetInt >= 5){
		Rep.pass("Timesheets added as expected")
	}else{
		Rep.fail("Timesheets not added")
	}
	

Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.kip4Locks + "btn_Saveclosebutton")
	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	
	sVal.searchTableAddedValue(3, lock.startDate)
