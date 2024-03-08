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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
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
import myProjects.Action as mAct
import search.Action as sAct
import search.Validate as sVal
import risks.Validate as rVal
import excel.Action as eAct
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Project
import models.GenerateProject

String searchItem = "Projects"
String searchVal = "Projects"

def newStartDate = Com.weekDatesDifferent_WorkingStartDate(3, "forward")
def altDate = newStartDate.split("-")
def year = (Calendar.getInstance().get(Calendar.YEAR)).toString()

def newAltDate = altDate[0] + year
def finNewDate = Com.changeDateFormat(newAltDate, "d MMMM yyyy", "dd/MM/yyyy")

def todayWE = new Date()
boolean weekend = Com.isBankHoliday(todayWE)
def currentNewDayDate

if(weekend) {
	currentNewDayDate = finNewDate
}else {
	def currentNewDayDateRaw = Com.returnTodaysDayDateInGivenWeek(finNewDate, "dd/MM/yyyy")
	currentNewDayDate = Com.nextWorkingDate(currentNewDayDateRaw)
}

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String ProjectName = Com.generateRandomText(10)
	String newDate = Com.workingDate()
	String[] newProject = [ProjectName, "customer_oreert", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "TestProject_Auto", "TestAuto_Proj", ""]
	Project project = GenerateProject.createProject(newProject)
	component.createProject(project)


Rep.nextTestStep("Select [Projects] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu]")
//	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
//	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gObj.createNewFilter("Project Name", "=", project.name)
	int rowNo = sVal.searchTableReturnRow(4, project.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	

Rep.nextTestStep("Click onto [Move Project] option")
	gObj.selectInlineOption(8)
	
	
Rep.nextTestStep("Select a date in [Move Start Date to Week Commencing] field via date picker")
	WebUI.switchToDefaultContent()
	gAct.Wait(2)
	WebD.clickElement("//span[contains(text(),'Project:')]")
	gAct.Wait(1)
	WebD.setEditText("//input[@id='MoveDate']", finNewDate)
	gAct.Wait(1)
	WebD.clickElement("//span[contains(text(),'Project:')]")


Rep.nextTestStep("Click onto [Move] button")
	gObj.buttonClickSync(Const.columnPicker + 'a_Move')
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Click onto [Inline] menu (Aero Sign) of the same project ")
//	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
//	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gObj.createNewFilter("Project Name", "=", project.name)
	int rowNum = sVal.searchTableReturnRow(4, project.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")

	
Rep.nextTestStep("Click [Open]")
	boolean objExt = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + 'a_InlineMenuPositionSelection', [('pos'): 1]), 2, FailureHandling.OPTIONAL)
	if(objExt){
		gObj.buttonClickSubIntPosSync(Const.columnPicker + 'a_InlineMenuPositionSelection',  1, "pos")
	}else{
		WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny dropup open')]//li[1]//a[1]")
	}


Rep.nextTestStep("Verify the project [Start Date] is changed to the new date as per the selection in [Move Project] modal date selection")
	String chgDate = gObj.getAttributeSync(Const.projectWizard + 'input_StartDateAddProject', 'value')
	gAct.compareStringAndReport(chgDate, currentNewDayDate)
