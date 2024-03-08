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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Timesheet
import models.GenerateTimesheet
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String searchItem = "Timesheet Submissions"
String searchVal = "Submissions"

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
	String notes = "Test view timesheet"
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 4, "dd/MM/yyyy")
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation_" + browser, "Project Management", "Project Plan"]
	String[] newHours = ["8.00", notes, "7.00", notes, "8.00", notes, "8.00", notes, "8.00", notes, "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	component.submitTimesheet_SpecificUser(timesheet, resource)
	String totalHours = "39:00"

	
Rep.nextTestStep("Select [Timesheets Submissions] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu] of Timesheet submission")
	gObj.buttonClickSync(Const.columnPicker + "a_Pending Approval")
	int rowNo = sVal.searchTableReturnRow(5, resource.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify the [Status] of Timesheet")
	String actStatus = gObj.getEditTextSync(Const.columnPicker + "subMissionStatus")
	gAct.compareStringAndReport(actStatus, "Submitted for Approval")
	String actApproved = gObj.getEditTextSync(Const.columnPicker + "ApprovedHours")
	gAct.compareStringAndReport(actApproved, "0:00")
	String actDisapproved = gObj.getEditTextSync(Const.columnPicker + "DisapprovedHours")
	gAct.compareStringAndReport(actDisapproved, "0:00")
	String actUnapproved = gObj.getEditTextSync(Const.columnPicker + "UnapprovedHours")
	gAct.compareStringAndReport(actUnapproved, totalHours)
	

Rep.nextTestStep("Click on [Accept] from next to Status")
	gObj.buttonClickSync(Const.columnPicker + "btnAccept")
	

Rep.nextTestStep("Add some text in [Notes] section")
	gObj.setEditSync(Const.columnPicker + "submissionNotesEdit", notes)


Rep.nextTestStep("Click [Accept]")
	gObj.buttonClickSync(Const.columnPicker + "btn_SubmissionAccept")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Verify hours moved to [Approved] within [Breakdown] section")
	String actNewApproved = gObj.getEditTextSync(Const.columnPicker + "ApprovedHours")
	gAct.compareStringAndReport(actNewApproved, totalHours)
	String actNewDisapproved = gObj.getEditTextSync(Const.columnPicker + "DisapprovedHours")
	gAct.compareStringAndReport(actNewDisapproved, "0:00")
	String actNewUnapproved = gObj.getEditTextSync(Const.columnPicker + "UnapprovedHours")
	gAct.compareStringAndReport(actNewUnapproved, "0:00")

	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_CloseSubmission")
