import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.junit.After
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Navigate as Nav
import timesheets.Populate as Pop
import timesheets.Validate as Val
import timesheets.Action as tAct

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import models.Timesheet
import models.GenerateTimesheet

def thisWeek = Com.weekDates()
String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
String[] newHours = ["8.00", "Test Notes", "", "", "", "", "", "", "8.00", "Some more notes", "", "", "", ""]

String[] vactionRow = ["", "", ""]
String[] vacationHours = ["", "", "8.00", "", "8.00", "", "8.00", "", "", "", "", "", "", ""]

Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
GenerateTimesheet.addTimeSheetRow(timesheet, vactionRow, vacationHours)

//change from default login
GVars.configFileName = 	"katalonConfigAuto3.xml"
Act.changeUserCredentials()

//Test Step
Rep.nextTestStep("Shared steps 12112: KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
//Test Step
Rep.nextTestStep("Navigate to Left Side Menu and click [Timesheets] option")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

	
//Test Step
Rep.nextTestStep("Click on [+] sign button")
	gObj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
	
		
//Test Step
Rep.nextTestStep("Select project, activity and task on new row")
	Pop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
	
	
//Test Step
Rep.nextTestStep("Populate Timesheet")
	Pop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
	Pop.VacationHoursModel(timesheet, 1)
	
	
//Test Step
Rep.nextTestStep("Save Timesheet")
	tAct.timesheetSave()
	
	
//Test Step
Rep.nextTestStep("Submit Timesheet")
	gObj.buttonClickSync(Const.timesheet + 'button_Submit')
	gAct.Wait(GVars.midWait)

//Test Step
Rep.nextTestStep("Click OK")
	WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
	gAct.Wait(2)
	
	
//Test Step
Rep.nextTestStep("Verify that the submitted time is greyed out and not editable ")
	String statusText = gObj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
	gAct.findSubstringInStringAndReport(statusText, "Submitted")	
	
	Val.timesheetCellReadOnly(Const.timesheet + 'input_Day1')
	Val.timesheetCellReadOnly(Const.timesheet + 'input_Day5')
	Val.timesheetCellReadOnly(Const.timesheet + 'input_VacationDay2')
	Val.timesheetCellReadOnly(Const.timesheet + 'input_VacationDay3')
	Val.timesheetCellReadOnly(Const.timesheet + 'input_VacationDay4')
	

//Test Step
Rep.nextTestStep("Verify the submitted section is displaying the total hours correctly (End of the row)")
	Val.timesheetHours(40.0)
