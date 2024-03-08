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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn as component
import models.Timesheet
import models.GenerateTimesheet

//change from default login
GVars.configFileName = 	"katalonConfigAuto4.xml"
Act.changeUserCredentials()
	
String searchItem = "Timesheet Submissions"
String searchVal = "Submissions"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String browser = Com.getBrowserType()
	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	String notes = "Test view timesheet"
	
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 4, "dd/MM/yyyy")
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation_" + browser, "Project Management", "Project Plan"]
	String[] newHours = ["8.00", notes, "8.00", notes, "8.00", notes, "8.00", notes, "8.00", notes, "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	component.submitTimesheet(timesheet)
	String totalHours = "40:00"


Rep.nextTestStep("Select [Timesheets Submissions] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu] of Timesheet submission")
	gObj.buttonClickSync(Const.columnPicker + "a_Pending Approval")
	int rowNo = sVal.searchTableReturnRow(5, GVars.user)
	String ref = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 3, "col")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "a_ViewInline", 1, "pos")
	
	
Rep.nextTestStep("Verify the [Status] of Timesheet")
	String actStatus = gObj.getEditTextSync(Const.columnPicker + "subMissionStatus")
	gAct.compareStringAndReport(actStatus, "Submitted for Approval")
	String actApproved = gObj.getEditTextSync(Const.columnPicker + "ApprovedHours")
	gAct.compareStringAndReport(actApproved, "0:00")
	String actDisapproved = gObj.getEditTextSync(Const.columnPicker + "DisapprovedHours")
	gAct.compareStringAndReport(actDisapproved, "0:00")
	String actUnapproved = gObj.getEditTextSync(Const.columnPicker + "UnapprovedHours")
	gAct.compareStringAndReport(actUnapproved, totalHours)
	

Rep.nextTestStep("Click on [Reject] from next to Status")
	gObj.buttonClickSync(Const.columnPicker + "btnReject")
	

Rep.nextTestStep("Add some text in [Notes] section")
	gObj.setEditSync(Const.columnPicker + "submissionNotesEdit", notes)


Rep.nextTestStep("Click [Reject]")
	gObj.buttonClickSync(Const.columnPicker + "btn_SubmissionReject")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Verify hours moved to [Rejected] within [Breakdown] section")
	String actNewApproved = gObj.getEditTextSync(Const.columnPicker + "subMissionStatus")
	gAct.findSubstringInStringAndReport(actNewApproved, "Rejected (DLEES4 " + todaysDate)

	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_CloseSubmission")

	
Rep.nextTestStep("Verify the [Status] of the timesheet changed to [Rejected] on Timesheet Submissions table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, ref)
	String newStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 6, "col")
	gAct.compareStringAndReport(newStatus, "Rejected")
	