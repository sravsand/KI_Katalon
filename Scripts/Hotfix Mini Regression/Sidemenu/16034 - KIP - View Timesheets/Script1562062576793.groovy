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
import buildingBlocks.createComponentsLogedIn_AdminNew as components
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import myProjects.Validate as mVal
import myProjects.Action as mAct
import timesheets.Navigate as tNav
import timesheets.Populate as tPop
import timesheets.Validate as tVal
import timesheets.Action as tAct

import models.Timesheet
import models.TimesheetRow
import models.GenerateTimesheet

//change from default login
GVars.configFileName = 	"katalonConfigAuto11.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	//setup pre-requisites
	def thisWeek = Com.weekDates()
	String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
	String[] newHours = ["7", "Notes", "7", "", "7", "", "7", "Notes", "7", "", "", "", "", ""]
	Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
	components.submitTimesheet(timesheet)
	
	
Rep.nextTestStep("Click on [Timesheets] from the side bar menu ")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
	
	
Rep.nextTestStep("Click onto [left pointing arrow]  (top left with day and dates for the selected week timesheet)")
	tNav.timesheetPreviousWeekValidateDate()


Rep.nextTestStep("Click onto [Right pointing arrow] (top left with day and dates for the selected week timesheet)")
	gObj.buttonClickSync(Const.timesheet + 'timesheetNavigateForward')
	gAct.Wait(1)
	gVal.objectText(Const.timesheet + 'timesheetWeek', timesheet.date)


Rep.nextTestStep("Click onto [Hours] field for first day of the week  I.e Mon 15/09 and Click [X] to close the modal.")
	tVal.cellPopupValues("0", "0", timesheet.row[0].hours.monNotes, "Yes")
	

Rep.nextTestStep("Click onto [Hours] field for Each day of the week  I.e Fri 21/09")
	tVal.cellPopupValues("0", "1", timesheet.row[0].hours.tuesNotes, "Yes")
	tVal.cellPopupValues("0", "2", timesheet.row[0].hours.wedsNotes, "Yes")
	tVal.cellPopupValues("0", "3", timesheet.row[0].hours.thursNotes, "Yes")
	tVal.cellPopupValues("0", "4", timesheet.row[0].hours.friNotes, "Yes")

	
Rep.nextTestStep("Verify that a green marker is displayed under the [Hours] field to identify which [Hours] field contains notes")
	Rep.info("Unable to validate 'that a green marker is displayed under the [Hours] field to identify which [Hours] field contains notes' with automation.")


Rep.nextTestStep("Verify the [X] button at the end of the submitted Timesheet row is greyed out and unclickable")
	WebUI.verifyElementPresent(findTestObject(Const.timesheet + 'deleteRow'), GVars.longWait)

