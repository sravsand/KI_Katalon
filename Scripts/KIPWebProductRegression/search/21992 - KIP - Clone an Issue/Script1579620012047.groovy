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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Issue
import models.GenerateIssue

String searchItem = "Issues"
String searchVal = "Issues"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String issueTitle = Com.generateRandomText(10)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String issueDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String newIssueDesc = Com.generateRandomText(15) + "_" + Com.generateRandomText(4)
	String[] newIssue = [issueTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, "", "Project", "Medium", "Improving", issueDescription, ""]
	Issue issue = GenerateIssue.createNewIssue(newIssue)
	component.createIssue(issue)
	
	String clonedIssueTitle = Com.generateRandomText(10)
	String clonedIssueDescription = Com.generateRandomText(2) + " " + Com.generateRandomText(15) + " " + Com.generateRandomText(9)
	String[] newClonedIssue = [clonedIssueTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, "", "Project", "High", "Improving", clonedIssueDescription, ""]
	Issue clonedIssue = GenerateIssue.createNewIssue(newClonedIssue)

	
Rep.nextTestStep("Select [Issues] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Mitigation Plan Items")
	int rowNo = sVal.searchTableReturnRow(3, issue.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(3)
	

Rep.nextTestStep("Enter data in [Title] textbox")
	gObj.setEditSync(Const.issues + 'input_Title', clonedIssue.title)
	
	
Rep.nextTestStep("Select [Status] from dropdown")
	WebUI.verifyOptionSelectedByLabel(findTestObject(Const.issues + 'select_Status'), clonedIssue.status, false, GVars.midWait)
	
	
Rep.nextTestStep("Select [Impact] from dropdown")
	gObj.selectComboByLabelSync(Const.issues + 'select_Impact', clonedIssue.impact)
	gObj.buttonClickSync(Const.columnPicker + "td_General")
	

Rep.nextTestStep("Select [Resolution Progress] from dropdown")
	gObj.selectComboByLabelSync(Const.issues + 'select_ResolutionProgress', clonedIssue.resolutionProgress)
	gObj.buttonClickSync(Const.columnPicker + "td_General")
			
		
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(GVars.shortWait)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, clonedIssue.title)
