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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import models.Timesheet
import models.GenerateTimesheet
import buildingBlocks.createComponents


GVars.configFileName = 	"katalonConfigAuto8.xml"
Act.changeUserCredentials()
String loginUsrNme = GVars.user

String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
String[] newHours = ["7.50", "", "7.50", "", "7.50", "", "7.50", "", "7.50", "", "", "", "", ""]
def thisWeek = Com.weekDates()
Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
createComponents.submitTimesheet(timesheet)

//change from default login
GVars.configFileName = 	"katalonConfigAuto7.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	Com.edgeSync()
	Com.firefoxSync()


Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Project Innovation')
	

Rep.nextTestStep("Click onto [Approvals]")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Approvals", "MVC-PRL1-APPROVALS?")


Rep.nextTestStep("Select the [Timesheet] to approve")
	String unAppVal, appVal, cellVal
	int intCnt
	boolean found = false
	
	int rowCnt = WebD.getTableRowCount("//table[contains(@class,'table tableRows table-condensed table-bordered table-hover')]")
	
	for(intCnt = 1; intCnt < rowCnt - 1; intCnt ++){
		cellVal = gObj.getEditTextDblSubIntPosSync(Const.approvals + "timesheetApprovalCell", intCnt, "row", 4, "col")	
			
		if(cellVal == loginUsrNme){
			unAppVal = gObj.getEditTextDblSubIntPosSync(Const.approvals + "timesheetApprovalCell", intCnt, "row", 7, "col")
			appVal = gObj.getEditTextDblSubIntPosSync(Const.approvals + "timesheetApprovalCell",  intCnt, "row", 6, "col")			
			found = true
			break
		}
	}

	if(!found){
		FailureHandling.STOP_ON_FAILURE
		Rep.fail("timesheet approval request not found")
	}
	
	gObj.buttonClickSubIntPosSync(Const.approvals + 'chk_timesheet', intCnt, "row")
	
					
Rep.nextTestStep("Click [Approve]")
	gObj.buttonClickSync(Const.approvals + 'a_Approve')
			
	String Approver = gObj.getAttributeSync(Const.approvals + 'input_Reference', "value")
	gAct.compareStringAndReport(Approver, GVars.altUser)
			
		
Rep.nextTestStep("Click [Save]")
	gObj.buttonClickSync(Const.approvals + 'button_Save')
	gAct.Wait(1)
	Act.verifyPopUpText("Time Approved Sucessfully")
	
	String newAppVal = gObj.getEditTextDblSubIntPosSync(Const.approvals + "timesheetApprovalCell", intCnt, "row", 6, "col")
	String newUnAppVal = gObj.getEditTextDblSubIntPosSync(Const.approvals + "timesheetApprovalCell", intCnt, "row", 7, "col")
		
	gAct.compareStringAndReport(newAppVal, unAppVal)
	gAct.compareStringAndReport(newUnAppVal, appVal)
		