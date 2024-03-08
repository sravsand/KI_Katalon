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
import search.Validate as sVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Action as sAct
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Timesheet
import models.GenerateTimesheet
import models.Timesheet
import models.GenerateTimesheet
import models.Lock
import models.GenerateLock

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

String searchItem = "Locks"
String searchVal = "Lock"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	
	def weekStart = Com.decreaseWorkingCalendarWithFormat(date, 10, "dd/MM/yyyy")
	def endDate = Date.parse(pattern, weekStart)
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(endDate, 4, "dd/MM/yyyy")
	
	def wStart = Com.changeDateFormat(weekStart, pattern, "d MMMM")
	def wEnd = Com.changeDateFormat(weekEnd, pattern, "d MMMM")
	
	String weekDates = wStart + " - " + wEnd
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation_" + GVars.selectedBrowser, "Project Management", "Project Plan"]
	String[] newHours = ["8.00", "", "8.00", "", "8.00", "", "8.00", "", "8.00", "", "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(weekDates, newTimesheet, newHours)
	component.submitOldTimesheetAndApprove(timesheet, 2, weekStart)
	
	String[] newLock = [weekStart, weekEnd, true, false, "5", "0"]
	Lock lock = GenerateLock.createLock(newLock)
	component.createLock(lock)


Rep.nextTestStep("Select [Locks] from search filter")
	Nav.selectSearchFilter(searchItem)
	

Rep.nextTestStep("Click onto [Inline menu] of Locks")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_SearchLockFilter")
	int rowNo = sVal.searchTableReturnRow(3, lock.startDate)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
	