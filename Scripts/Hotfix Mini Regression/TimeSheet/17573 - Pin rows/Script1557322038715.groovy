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

import timesheets.Validate as Val
import timesheets.Populate as Pop
import timesheets.Action as tAct
import timesheets.Navigate as tNav

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
String[] newHours = ["4:00", "", "", "", "", "", "5:00", "", "7:00", "", "", "", "", ""]

String[] secondRow = ["Non-Project Work", "Meetings", ""]
String[] secondRowHours = ["", "", "4:00", "", "7:30", "", "8:00", "", "", "", "", "", "", ""]

String[] thirdRow = ["Non-Project Work", "Staff Training", ""]
String[] thirdRowHours = ["", "", "0:30", "", "", "", "3:00", "", "1:00", "", "", "", "", ""]

Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
GenerateTimesheet.addTimeSheetRow(timesheet, secondRow, secondRowHours)
GenerateTimesheet.addTimeSheetRow(timesheet, thirdRow, thirdRowHours)

//change from default login
GVars.configFileName = 	"katalonConfigAuto5.xml"
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
Rep.nextTestStep("Click on [+] sign button")
	gObj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
			
				
//Test Step
Rep.nextTestStep("Select third project, activity and task on new row")
	Pop.CreateAdditionalRow("4", timesheet.row[2].project, timesheet.row[2].activity, timesheet.row[2].task)
	
			
//Test Step
Rep.nextTestStep("Populate third Timesheet line")
	Pop.StandardHoursAdditionalRowModel(timesheet, "2", 2)
	
	
//Test Step
Rep.nextTestStep("Click on [Pin Post] icon (left side of the row)")
	gObj.buttonClickSubIntPosSync(Const.timesheet + 'pinRow', 2, "row")
	gObj.buttonClickSubIntPosSync(Const.timesheet + 'pinRow', 3, "row")
	gObj.buttonClickSubIntPosSync(Const.timesheet + 'pinRow', 4, "row")
	

//Test Step
Rep.nextTestStep("Click [Save]")
	tAct.timesheetSave()
	
	
//Test Step
Rep.nextTestStep("Click on [Date Navigation] and change the week")
	tNav.timesheetNextWeekValidateDate()
	
	
//Test Step
Rep.nextTestStep("Validate Pinned values")
	if(GVars.browser == "MicrosoftEdge"){
		Val.timesheetPinnedRowEdge("2", timesheet.row[1].project, timesheet.row[1].activity, "N/A")
		Val.timesheetPinnedRowEdge("3", timesheet.row[2].project, timesheet.row[2].activity, "N/A")
		Val.timesheetPinnedRowEdge("4", timesheet.row[0].project, timesheet.row[0].activity, "1.3.1 | " + timesheet.row[0].task)
	}else{
		Val.timesheetPinnedRow("2", timesheet.row[1].project, timesheet.row[1].activity, "N/A")
		Val.timesheetPinnedRow("3", timesheet.row[2].project, timesheet.row[2].activity, "N/A")
		Val.timesheetPinnedRow("4", timesheet.row[0].project, timesheet.row[0].activity, "1.3.1 | " + timesheet.row[0].task)
	}
	
	
