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
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import timesheets.Populate as Pop

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.WebDriverMethods as WebD

//Define Variables
String[] standardHours = ["8.00","8.00","8.00","8.00","8.00"]
String[] standardNotes = ["","Undo","","",""]


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
	Pop.CreateAdditionalRow("2", "Non-Project Work", "Meetings", "")

	
//Test Step
Rep.nextTestStep("Populate Timesheet")
	Pop.StandardHoursAdditionalRow("0", standardHours, standardNotes)
	
	
//Test Step
Rep.nextTestStep("Click on [Cancel] icon")
	WebUI.click(findTestObject(Const.timesheet + 'TimesheetCancel'))
	gAct.Wait(2)

	
//Test Step
Rep.nextTestStep("Click OK")
	WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
	
	
//Test Step
Rep.nextTestStep("Verify that the user can navigate back to last saved stage of the [Timesheets]")
	WebUI.verifyElementNotPresent(findTestObject(Const.timesheet + 'selectProject'), GVars.shortWait, FailureHandling.OPTIONAL)
	