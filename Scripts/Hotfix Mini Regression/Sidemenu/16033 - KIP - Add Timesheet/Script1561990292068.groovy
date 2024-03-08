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
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys
import java.util.ArrayList;

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import timesheets.Populate as tPop
import timesheets.Validate as tVal
import timesheets.Action as tAct

import models.Timesheet
import models.TimesheetRow
import models.GenerateTimesheet

def thisWeek = Com.weekDates()
String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
String[] newHours = ["8", "Some notes", "8", "Hi", "8", "Yes", "8", "No", "8", "What", "", "", "", ""]

Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)

//change from default login
GVars.configFileName = 	"katalonConfigAuto7.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click on [Timesheets] from the side bar menu ")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")


Rep.nextTestStep("Click on [+] sign button")
	gObj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
	
	
Rep.nextTestStep("Select [Project] from dropdown ")
	tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)


Rep.nextTestStep("Click on the [Day/Date] field i.e. [Mon 29/5] enter 8 hours and notes till Fri")
	tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)


Rep.nextTestStep("Click [Save]")
	tAct.timesheetSave()


Rep.nextTestStep("Click [Submit]")
	gObj.buttonClickSync(Const.timesheet + 'button_Submit')
	gAct.Wait(GVars.midWait)


Rep.nextTestStep("Click [OK]")
	WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
	gAct.Wait(2)


Rep.nextTestStep("Verify that the [submitted] time is greyed out and not editable ")
	String statusText = gObj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
	gAct.compareStringAndReport(statusText, " Submitted")

	tVal.timesheetCellReadOnly(Const.timesheet + 'input_Day1')
	tVal.timesheetCellReadOnly(Const.timesheet + 'input_Day2')
	tVal.timesheetCellReadOnly(Const.timesheet + 'input_Day3')
	tVal.timesheetCellReadOnly(Const.timesheet + 'input_Day4')
	tVal.timesheetCellReadOnly(Const.timesheet + 'input_Day5')

