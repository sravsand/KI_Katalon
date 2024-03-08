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
import global.WebDriverMethods as WebD

//Define Variables
String[] standardHours = ["4.00","","","5.00","7.00"]
String[] standardNotes = ["","","","",""]
String[] standardHoursLine2 = ["4.00","7.50","8.00","",""]
String[] standardNotesLine2 = ["","","","",""]
String[] standardHoursLine3 = ["","0.50","","3.00","1.00"]
String[] standardNotesLine3 = ["","","","",""]

//change from default login
GVars.configFile = 	".\\Config\\katalonConfigAuto5.xml"
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
	Pop.StandardHoursAdditionalRow("0", standardHoursLine2, standardNotesLine2)

	
//Test Step
Rep.nextTestStep("Click on [+] sign button")
	WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		
			
//Test Step
Rep.nextTestStep("Select second project, activity and task on new row")
	Pop.CreateAdditionalRow("3", "Non-Project Work", "Meetings", "")

		
//Test Step
Rep.nextTestStep("Populate second Timesheet line")
	Pop.StandardHoursAdditionalRow("1", standardHoursLine2, standardNotesLine2)
	
	
//Test Step
Rep.nextTestStep("Click on [+] sign button")
	WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
			
				
//Test Step
Rep.nextTestStep("Select third project, activity and task on new row")
	Pop.CreateAdditionalRow("4", "Non-Project Work", "Staff Training", "")
	
			
//Test Step
Rep.nextTestStep("Populate third Timesheet line")
	Pop.StandardHoursAdditionalRow("2", standardHoursLine3, standardNotesLine3)
	
	
//Test Step
Rep.nextTestStep("Click on [Pin Post] icon (left side of the row)")
	WebUI.click(findTestObject(Const.timesheet + 'pinRow', [('row'): 2]))
	WebUI.click(findTestObject(Const.timesheet + 'pinRow', [('row'): 3]))
	WebUI.click(findTestObject(Const.timesheet + 'pinRow', [('row'): 4]))
	

//Test Step
Rep.nextTestStep("Click [Save]")
	tAct.timesheetSave()
	
	
//Test Step
Rep.nextTestStep("Click on [Date Navigation] and change the week")
	tNav.timesheetNextWeekValidateDate()
	
	
//Test Step
Rep.nextTestStep("Validate Pinned values")
	Val.timesheetPinnedRow("2", "Non-Project Work", "Meetings", "N/A")
	Val.timesheetPinnedRow("3", "Non-Project Work", "Staff Training", "N/A")
	Val.timesheetPinnedRow("4", "Project Innovation", "Project Management", "1.3.1 | Testing Phase")


