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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Issue
import models.GenerateIssue
import models.IssueAction
import models.GenerateIssueAction

String searchItem = "Issues"
String searchVal = "Issues"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String issueTitle = Com.generateRandomText(10)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String issueDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String issueActionDescription = Com.generateRandomText(4) + " " + Com.generateRandomText(3)
	
	String[] newIssue = [issueTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, "", "Project", "Medium", "Improving", issueDescription, ""]
	Issue issue = GenerateIssue.createNewIssue(newIssue)
	component.createIssue(issue)
	
	String[] newIssueAction = ["Project Innovation_" + GVars.selectedBrowser, issue.code + " | " + issueTitle, "Mitigation",  GVars.user, "Open", todaysDate, "Medium", issueActionDescription]
	IssueAction issueAction = GenerateIssueAction.createNewIssueAction(newIssueAction)
	component.createIssueAction(issueAction)

	
Rep.nextTestStep("Select [Issues] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline] menu of a [Issue] with associated [Resolution Plan items]")
	int rowNo = sVal.searchTableReturnRow(3, issue.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Click on the [Resolution Plan Items] tab")
	gObj.buttonClickSync(Const.columnPicker + "td_MitigationPlanItemsTab")


Rep.nextTestStep("Verify that all the associated [Resolution Plan Items] are displayed within the table ")
	String owner = gObj.getEditTextSubIntPosSync(Const.columnPicker + "mitPlanTable", 3, "col")
	gAct.compareStringAndReport(owner, issueAction.owner)
	
	String dueDate = gObj.getEditTextSubIntPosSync(Const.columnPicker + "mitPlanTable", 5, "col")
	gAct.compareStringAndReport(dueDate, issueAction.dueDate)
	
	String desc = gObj.getEditTextSubIntPosSync(Const.columnPicker + "mitPlanTable", 6, "col")
	gAct.compareStringAndReport(desc, issueAction.description)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	