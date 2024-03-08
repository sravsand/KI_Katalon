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
import global.WebDriverMethods as WebD

//Define Variables
String[] standardHours = ["8:00","8:00","8:00","8:00","8:00"]
String[] standardNotes = ["Text1","Text2","Text3","Text4","Text5"]

//change from default login
GVars.configFile = 	".\\Config\\katalonConfigAuto4.xml"
Act.changeUserCredentials(GVars.configFile)

//Test Step
Rep.nextTestStep("Shared steps 12112: KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
//Test Step
Rep.nextTestStep("Navigate to Left Side Menu and click [Timesheets] option")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

	
//Test Step
Rep.nextTestStep("Click on [+] sign button")
	WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
	

//Test Step
Rep.nextTestStep("Select project, activity and task on new row")
	Pop.CreateAdditionalRow("2", "Project Innovation", "Project Management", "Testing Phase")

	
//Test Step
Rep.nextTestStep("Populate Timesheet")
	Pop.StandardHours(standardHours, standardNotes)

	
//Test Step
Rep.nextTestStep("Save Timesheet")
	tAct.timesheetSave()
	
	
//Test Step
Rep.nextTestStep("Click on [My Work] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")


//Test Step
Rep.nextTestStep("Click on [Timesheet] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
	
	
//Test Step
Rep.nextTestStep("Verify the data entered in above steps are saved")
	Val.timesheetCellHours(Const.timesheet + 'input_Day1', standardHours[0] )
	Val.timesheetCellHours(Const.timesheet + 'input_Day2', standardHours[1] )
	Val.timesheetCellHours(Const.timesheet + 'input_Day3', standardHours[2] )
	Val.timesheetCellHours(Const.timesheet + 'input_Day4', standardHours[3] )
	Val.timesheetCellHours(Const.timesheet + 'input_Day5', standardHours[4] )


//Test Step
Rep.nextTestStep("Verify that the saved time is not greyed out and is editable")
	Val.timesheetCellEditable(Const.timesheet + 'input_Day1')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day2')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day3')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day4')
	Val.timesheetCellEditable(Const.timesheet + 'input_Day5')

	gAct.Wait(3)