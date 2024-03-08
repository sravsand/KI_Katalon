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

//Define Variables
def thisWeek = Com.weekDates()
String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
String[] newHours = ["8:00", "Multiple Test case", "", "", "", "", "", "", "8:00", "Comments go here", "", "", "", ""]

String[] secondRow = ["Non-Project Work", "Meetings", ""]
String[] secondRowHours = ["", "", "7:30", "", "8:30", "", "9:00", "", "", "", "", "", "", ""]

Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
GenerateTimesheet.addTimeSheetRow(timesheet, secondRow, secondRowHours)

//change from default login
GVars.configFileName = 	"katalonConfigAuto2.xml"
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
	
	
//Test Step
Rep.nextTestStep("Click on [+] sign button")
	gObj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		
			
//Test Step
Rep.nextTestStep("Select second project, activity and task on new row")
	Pop.CreateAdditionalRow("3", timesheet.row[1].project, timesheet.row[1].activity, timesheet.row[1].task)

		
//Test Step
Rep.nextTestStep("Populate second Timesheet line")
	Pop.StandardHoursAdditionalRowModel(timesheet, "1", 1)


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

		
//Test Step
Rep.nextTestStep("Verify that the submitted time is greyed out and not editable ")
	String statusText = gObj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
	gAct.findSubstringInStringAndReport(statusText, "Submitted")
		
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "1", "0")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "0", "1")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "0", "2")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "0", "3")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "1", "4")
		
	
//Test Step
Rep.nextTestStep("Verify the submitted section is displaying the total hours correctly (End of the row)")
	String projectHoursLine1 = gObj.getEditTextDblSubIntPosSync(Const.timesheet + 'cellTotals',  2, "row", 18, "col")
	String projectHoursLine2 = gObj.getEditTextDblSubIntPosSync(Const.timesheet + 'cellTotals', 3, "row", 18, "col")
	String projectHoursTotal = gObj.getEditTextSync(Const.timesheet + 'timeTotalExpected')
	projectHoursLine1 = projectHoursLine1.replace(":", ".")
	projectHoursLine2 = projectHoursLine2.replace(":", ".")
	projectHoursTotal = projectHoursTotal.replace(":", ".")
	
	float prjHoursSum = projectHoursLine1.toFloat() + projectHoursLine2.toFloat()
	gAct.comparefloatAndReport(prjHoursSum, 41.0)
	
	
	