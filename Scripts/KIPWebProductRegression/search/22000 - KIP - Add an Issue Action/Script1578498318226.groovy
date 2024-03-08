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
import models.IssueAction
import models.GenerateIssueAction
import buildingBlocks.createComponentsLogedIn_AdminNew as component


String searchItem = "Resolution Plan Items"
String searchVal = "Resolution Plan Items"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String issueTitle = Com.generateRandomText(10)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String issueDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String issueActionDescription = Com.generateRandomText(4) + " " + Com.generateRandomText(15) + " " + Com.generateRandomText(14)
	
	String[] newIssue = [issueTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, "", "Project", "Medium", "Improving", issueDescription, ""]
	Issue issue = GenerateIssue.createNewIssue(newIssue)
	component.createIssue(issue)
	
	String[] newIssueAction = ["Project Innovation_" + GVars.selectedBrowser, issue.code + " | " + issueTitle, "Mitigation",  GVars.user, "Open", todaysDate, "Medium", issueActionDescription]
	IssueAction issueAction = GenerateIssueAction.createNewIssueAction(newIssueAction)
	

Rep.nextTestStep("Select [Resolution Plan Items] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add]")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	
	
Rep.nextTestStep("Click on [Search] button and select a [Project]")
	gObj.setEditSync(Const.issues + 'input_Project', issueAction.project)
	gObj.buttonClickSync(Const.columnPicker + "td_General")

	
Rep.nextTestStep("Click on [Issue] dropdown and Select issue from dropdown list")
	gObj.selectComboByLabelSync(Const.columnPicker + 'select_Issue', issueAction.issue)


Rep.nextTestStep("Click on [Search] and select [Type]")
	gObj.setEditSync(Const.columnPicker + 'input_RiskActionType', issueAction.type)
	gObj.buttonClickSync(Const.columnPicker + "td_General")


Rep.nextTestStep("Click on [Search] button and select a [Owner] ")
	String ownerText = gObj.getAttributeSync(Const.issues + 'input_Owner', 'value')
	if(ownerText == ""){
		gObj.setEditSync(Const.issues + 'input_Owner', issueAction.owner)
		gObj.buttonClickSync(Const.issues + 'input_Title')
	}


Rep.nextTestStep("Click on Calander icon in [Due Date] and Select a [Date]")
	gObj.setEditSync(Const.columnPicker + 'input_DueDate', issueAction.dueDate)


Rep.nextTestStep("Click on [Priority] and Select [Medium] from dropdown list")
	gObj.selectComboByLabelSync(Const.columnPicker + 'select_Priority', issueAction.priority)
	gObj.buttonClickSync(Const.columnPicker + "td_General")


Rep.nextTestStep("Add some text in [Description]")
	gObj.setEditSync(Const.columnPicker + "action_Description", issueAction.description)

	
Rep.nextTestStep("Click [Save] or  [Save & Close]")
	gObj.buttonClickSync(Const.issues + "button_SaveAndClose")
	gAct.Wait(GVars.shortWait)

	
Rep.nextTestStep("Verify recently added issue resolution is displayed within [Resolution Plan Item]table")
	sVal.searchTableAddedValue(3, issueTitle)
	