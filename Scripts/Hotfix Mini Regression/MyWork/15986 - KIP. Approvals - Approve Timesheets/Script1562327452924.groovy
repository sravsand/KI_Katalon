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
import global.Object as gObj
import global.Action as gAct
import global.WebDriverMethods as WebD
import global.Validate as gVal
import myWork.Action as mAct
import models.Timesheet
import models.GenerateTimesheet
import buildingBlocks.createComponents

//String subDate = "05/07/2019"
//String resourceName = "Dave Lees 6"
//String approver = "DLEES7"
String contractLineValue = "Time And Materials"

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

String resourceName = GVars.user
def thisWeek = Com.weekDates()
def subDate = Com.todayDate()

String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
String[] newHours = ["8.00", "", "8.00", "", "8.00", "", "8.00", "", "8.00", "", "", "", "", ""]

Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
createComponents.submitTimesheet(timesheet)


//change from default login
GVars.configFileName = 	"katalonConfigAuto7.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Select [Configuration] cog icon displayed on top right hand side of the page ")
	gObj.buttonClickSync(Const.mainToolBar + "settings")


Rep.nextTestStep("Select [General Settings] option")
	gObj.buttonClickSync(Const.settingsMenu + 'TimeExpenseSettings')


Rep.nextTestStep("Ensure [Timesheet Submission] is enabled")
	boolean chk = WebUI.verifyElementChecked(findTestObject(Const.timeExpense_Modal + 'chk_EnabledTimesheet'), GVars.midWait, FailureHandling.OPTIONAL)

	if(chk == false){
		gObj.buttonClickSync(Const.timeExpense_Modal + 'label_Enabled')
	}


Rep.nextTestStep("Click [Save and Close] button")
	if(chk == false){
		gObj.buttonClickSync(Const.timeExpense_Modal + 'button_SaveAndClose')
	}else{
		gObj.buttonClickSync(Const.timeExpense_Modal + 'button_Close')
	}
	

Rep.nextTestStep("Click onto [My Work] from the side bar ")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")


Rep.nextTestStep("Click onto [Approvals] tab ")
	gObj.buttonClickSync(Const.myWork + 'a_Approvals')
	gAct.Wait(2)


Rep.nextTestStep("Goto [Filter] and select [Pending Approval] from drop down list")
	WebUI.switchToDefaultContent()
	WebUI.focus(findTestObject(Const.myWork + 'a_Filter'))
	gObj.buttonClickSync(Const.myWork + 'a_Filter')
	gObj.buttonClickSync(Const.myWork + 'a_Pending Approval')
	gAct.Wait(1)


Rep.nextTestStep("Click on any of the hours under [Total] i.e. 8")
	mAct.selectTimesheetForApproval(subDate, resourceName)
	
	
Rep.nextTestStep("Select the timesheet to [Approve]")
	gObj.buttonClickSync(Const.myWork + 'selectAll')
	gVal.objectTextwithParameter(Const.approveTsheet + 'timeSheetcell', 'row', 1, "")
	

Rep.nextTestStep("Click [Approve]")
	gObj.buttonClickSync(Const.myWork + 'a_Approve')
//	gVal.objectText(Const.approveTsheet + 'ApprovalHeader', 'Approve Time?')
	gVal.objectAttributeValue(Const.approveTsheet + 'input_Reference', 'value', GVars.altUser)
	
	if(GVars.browser == "MicrosoftEdge"){
		String checkValue = gObj.getObjectAttributeValue("//select[@id='SelectedContractLinePO']", "innerText", 1)
		gAct.compareStringAndReport(checkValue, contractLineValue)

	}else{	
		gVal.objectText(Const.approveTsheet + 'select_Time And Materials', contractLineValue)
	}
	

Rep.nextTestStep("Click [Save]")
	gObj.buttonClickSync(Const.approveTsheet + 'button_Save')
	gAct.Wait(2)
	WebUI.waitForElementNotPresent(findTestObject(Const.approveTsheet + 'TimeApprovedSucessfully'), GVars.longWait, FailureHandling.OPTIONAL)

	gVal.objectTextwithParameter(Const.approveTsheet + 'timeSheetcell','row', 1, "Yes")

