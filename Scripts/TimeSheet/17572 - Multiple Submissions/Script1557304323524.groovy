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
import global.WebDriverMethods as WebD

//Define Variables
String[] standardHours = ["8.00","","","","7.00"]
String[] standardNotes = ["Multiple Test case","","","","Comments go here"]
String[] standardHoursLine2 = ["","7.50","8.50","9.00",""]
String[] standardNotesLine2 = ["","","","",""]

//change from default login
GVars.configFile = 	".\\Config\\katalonConfigAuto2.xml"
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
	Pop.StandardHoursAdditionalRow("0", standardHours, standardNotes)
	
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
Rep.nextTestStep("Save Timesheet")
	tAct.timesheetSave()
		
		
//Test Step
Rep.nextTestStep("Submit Timesheet")
	WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
	gAct.Wait(5)
	
	
//Test Step
Rep.nextTestStep("Click OK")
	WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")

		
//Test Step
Rep.nextTestStep("Verify that the submitted time is greyed out and not editable ")
	String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
	gAct.compareStringAndReport(statusText, " Submitted")
		
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "1", "0")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "0", "1")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "0", "2")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "0", "3")
	Val.timesheetCellReadOnlyGeneric(Const.timesheet + 'input_DayCell', "1", "4")
		
	
//Test Step
Rep.nextTestStep("Verify the submitted section is displaying the total hours correctly (End of the row)")
	String projectHoursLine1 = WebUI.getText(findTestObject(Const.timesheet + 'cellTotals', [('row'): 2, ('col'): 17]))
	String projectHoursLine2 = WebUI.getText(findTestObject(Const.timesheet + 'cellTotals', [('row'): 3, ('col'): 17]))
	String projectHoursTotal = WebUI.getText(findTestObject(Const.timesheet + 'timeTotalExpected'))
	projectHoursLine1 = projectHoursLine1.replace(":", ".")
	projectHoursLine2 = projectHoursLine2.replace(":", ".")
	projectHoursTotal = projectHoursTotal.replace(":", ".")
	
	float prjHoursSum = projectHoursLine1.toFloat() + projectHoursLine2.toFloat()
	gAct.comparefloatAndReport(prjHoursSum, 40.0)
	
	
	