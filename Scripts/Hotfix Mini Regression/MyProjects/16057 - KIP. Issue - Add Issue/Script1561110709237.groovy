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
import global.Validate as gVal
import global.Object as gObj
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import gantt.Action as ganttAct
import models.Issue
import models.GenerateIssue


String todayDate = Com.todayDate()
String browser = Com.getBrowserType()
String [] newIssue = ["A New Test Issue " + browser, 'Tech Crunch Web Services Deployment', GVars.user, "Open", todayDate, "RSK00011", "Project", "Medium", "Improving", "", ""]
Issue issue = GenerateIssue.createNewIssue(newIssue)

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', issue.project)


Rep.nextTestStep("Click onto [Issues] tab")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Issues", "MVC-PRL1-ISSUES?")
	

Rep.nextTestStep("Click on [Add]")
	gObj.buttonClickSync(Const.issues + 'a_Add')
	
	
Rep.nextTestStep("Add text to [Title] field ")
	gObj.setEditSync(Const.issues + 'input_Title', issue.title)
	
	
Rep.nextTestStep("Verify [Project], [Owner], and [status] is pre selected")
	String projectText = gObj.getAttributeSync(Const.issues + 'input_Project', 'value')
	gAct.compareStringAndReport(projectText, issue.project)
	String ownerText = gObj.getAttributeSync(Const.issues + 'input_Owner', 'value')
	gAct.compareStringAndReport(ownerText, issue.owner)
	WebUI.verifyOptionSelectedByLabel(findTestObject(Const.risks + 'select_Status'), issue.status, false, 5)
	

Rep.nextTestStep("Select [Associated Risk] - Click [OK] to Confirmation message")
	gObj.selectComboByValueSync(Const.issues + 'select_AssocRisk', issue.assocRisk)
	WebUI.acceptAlert()
	
	
Rep.nextTestStep("Select [Publish to] as project")
	WebUI.switchToDefaultContent()
	gObj.selectComboByLabelSync(Const.issues + 'select_PublishTo', issue.publishTo)
	gObj.buttonClickSync(Const.issues + 'input_Title')

	
Rep.nextTestStep("Select an [Impact] as Medium")
	gObj.selectComboByLabelSync(Const.issues + 'select_Impact', issue.impact)
	gObj.buttonClickSync(Const.issues + 'input_Title')

	
Rep.nextTestStep("Select [Resolution Progress] i.e  Improving")
	gObj.selectComboByValueSync(Const.issues + 'select_ResolutionProgress', issue.resolutionProgress)
	gObj.buttonClickSync(Const.issues + 'input_Title')

	
Rep.nextTestStep("Click onto each tab within Add issue window")
	gObj.buttonClickSync(Const.issues + 'ResolutionPlanItemsTab')
	gObj.buttonClickSync(Const.issues + 'NotesTab')
	gObj.buttonClickSync(Const.issues + 'AttachmentsTab')
		

Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.issues + 'button_SaveAndClose')
	boolean issuePresent = WebUI.verifyElementPresent(findTestObject(Const.issues + 'issueTitle', [('issue') : issue.title]), GVars.longWait)
	if(issuePresent){
		Rep.pass("New issue has been created.")
	}else{
		Rep.fail("New issue has not been created.")
	}
	