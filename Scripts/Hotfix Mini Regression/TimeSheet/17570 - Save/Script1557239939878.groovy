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
String[] newHours = ["8:00", "Text1", "8:00", "Text2", "8:00", "Text3", "8:00", "Text4", "8:00", "Text5", "", "", "", ""]

Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)


//change from default login
GVars.configFileName = 	"katalonConfigAuto4.xml"
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
Rep.nextTestStep("Save Timesheet")
	tAct.timesheetSave()
	
	
//Test Step
Rep.nextTestStep("Click on [My Work] from side menu bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")


//Test Step
Rep.nextTestStep("Click on [Timesheet] from side menu bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
	
	
//Test Step
Rep.nextTestStep("Verify the data entered in above steps are saved")
	Val.timesheetCellHours(Const.timesheet + 'input_Day1', timesheet.row[0].hours.mon )
	Val.timesheetCellHours(Const.timesheet + 'input_Day2', timesheet.row[0].hours.tues )
	Val.timesheetCellHours(Const.timesheet + 'input_Day3', timesheet.row[0].hours.weds )
	Val.timesheetCellHours(Const.timesheet + 'input_Day4', timesheet.row[0].hours.thurs )
	Val.timesheetCellHours(Const.timesheet + 'input_Day5', timesheet.row[0].hours.fri )


//Test Step
Rep.nextTestStep("Verify that the saved time is not greyed out and is editable")
	Val.timesheetCellEditable(Const.timesheet + 'input_Day1')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day2')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day3')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day4')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day5')
