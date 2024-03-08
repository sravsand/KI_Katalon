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
import com.kms.katalon.core.webui.driver.DriverFactory

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
import issues.Validate as iVal
import models.Issue
import models.GenerateIssue

String issueTitle = Com.generateRandomText(10)

def pattern = "dd/MM/yyyy"
Date today = new Date()
String todaysDate = today.format(pattern)
String issueDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)

String[] newIssue = [issueTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, "", "Project", "Medium", "Improving", issueDescription, ""]
Issue issue = GenerateIssue.createNewIssue(newIssue)

String searchItem = "Issues"
String searchVal = "Issues"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Issues] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add]")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	

Rep.nextTestStep("Click on [Save] or [Save & Close] button")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	String errorMsg = gObj.getEditTextSync(Const.columnPicker + "addExpenseError")
	gAct.compareStringAndReport(errorMsg, "Unable to Save Issue")
	

Rep.nextTestStep("Enter data in [Title] textbox")
	gObj.setEditSync(Const.issues + 'input_Title', issue.title)


Rep.nextTestStep("Click on [Search] button and select a [Project] ")
	gObj.setEditSync(Const.issues + 'input_Project', issue.project)
	gObj.buttonClickSync(Const.columnPicker + "td_General")


Rep.nextTestStep("Click on [Search] button and select a [Owner] ")
	String ownerText = gObj.getAttributeSync(Const.issues + 'input_Owner', 'value')
	if(ownerText == ""){
		gObj.setEditSync(Const.issues + 'input_Owner', issue.owner)
		gObj.buttonClickSync(Const.issues + 'input_Title')
	}

	
Rep.nextTestStep("Select [Status] from dropdown")
	WebUI.verifyOptionSelectedByLabel(findTestObject(Const.issues + 'select_Status'), issue.status, false, 5)


Rep.nextTestStep("Select [Impact] from dropdown")
	gObj.selectComboByLabelSync(Const.issues + 'select_Impact', issue.impact)
	gObj.buttonClickSync(Const.columnPicker + "td_General")
	

Rep.nextTestStep("Select [Resolution Progress] from dropdown")
	gObj.selectComboByLabelSync(Const.issues + 'select_ResolutionProgress', issue.resolutionProgress)
	gObj.buttonClickSync(Const.columnPicker + "td_General")

	
Rep.nextTestStep("Click [Save] or  [Save & Close]")
	gObj.buttonClickSync(Const.issues + "button_SaveAndClose")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Verify recently added Issue is avaible for seletion in  [Issues] table")
	sVal.searchTableAddedValue(3, issue.title)
	
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")
	gAct.Wait(GVars.shortWait)
	
	def textPresent = WebUI.verifyTextPresent(issue.title, false)
	
	if(textPresent){
		WebD.clickText(issue.title)
		gAct.Wait(1)
		iVal.issueDetails(issue)
	}

	