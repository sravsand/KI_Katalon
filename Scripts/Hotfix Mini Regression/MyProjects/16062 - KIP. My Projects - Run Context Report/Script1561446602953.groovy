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
import myProjects.Action as mAct

//change from default login
GVars.configFileName = 	"katalonConfigAuto8.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [Reports] dropdown (Top right corner) and select [Task Progress]")
	mAct.selectContextReportAndValidate('a_TaskProgress', "Task Progress")

	
Rep.nextTestStep("Select [Risk Register]")
	mAct.selectContextReportAndValidate('a_RiskRegister', "Project Risk Register")

	
Rep.nextTestStep("Select [Risk Mitigation Plan]")
	mAct.selectContextReportAndValidate('a_RiskMitigationPlan', "Risk Mitigation Plan")
	
	
Rep.nextTestStep("Select [Issue Log]")
	mAct.selectContextReportAndValidate('a_IssueLog', "Issue Log")
	
	
Rep.nextTestStep("Select [Issue Resolution Plan]")
	mAct.selectContextReportAndValidate('a_ProjectIssueResolutionPlan', "Issue Resolution Plan")
	
	
Rep.nextTestStep("Select [Change Requests]")
	mAct.selectContextReportAndValidate('a_ChangeRequests', "Change Requests")
	
	
Rep.nextTestStep("Select [Project Documents]")
	mAct.selectContextReportAndValidate('a_ProjectDocuments', "Project Documents")
	
	
Rep.nextTestStep("Select [Lessons Learned]")
	mAct.selectContextReportAndValidate('a_LessonsLearned', "Lessons Learned")
	
	
Rep.nextTestStep("Select [Project Progress Update]")
	mAct.selectContextReportAndValidate('a_ProjectProgressUpdate', "Project Progress Updates")
	
	
Rep.nextTestStep("Select [Completed Tasks]")
	mAct.selectContextReportAndValidate('a_CompletedTasks', "Completed Tasks")
	
	
Rep.nextTestStep("Select [Overdue Tasks]")
	mAct.selectContextReportAndValidate('a_OverdueTasks', "Overdue Tasks")
	
	
Rep.nextTestStep("Select [Overdue Tasks (with Assignments)]")
	mAct.selectContextReportAndValidate('a_OverdueTasks(withAssignments)', "Overdue Tasks (with Assignments)")
	
	
Rep.nextTestStep("Select [Tasks Due This Month]")
	mAct.selectContextReportAndValidate('a_TasksDueThisMonth', "Tasks Due this Month")
	
	
Rep.nextTestStep("Select [Tasks Due This Month (with Assignments)]")
	mAct.selectContextReportAndValidate('a_TasksDueThisMonth(withAssignments)', "Tasks Due this Month (with Assignments)")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Select [Project Risk Register]")
	mAct.selectContextReportAndValidate('a_ProjectRiskRegister', "Project Risk Register")
	
	
Rep.nextTestStep("Select [Project Status Report]")
	Rep.info("Project Status Report removed as issue occurs with url.")
/*
	WebUI.click(findTestObject(Const.context + 'button_ContextReports'))
	WebUI.click(findTestObject(Const.context + 'a_ProjectStatusReport'))
	gAct.Wait(5)
	WebUI.switchToWindowIndex(1)
//	WebUI.refresh()
	gAct.Wait(12)
	String url = WebUI.getUrl()
	gAct.compareStringAndReport(url, GVars.ppmUrl + "KIPWebPortal/reports/extra/ProjectStatus2/rep_ProjectStatus2.aspx?Output=PDF")
	WebUI.switchToDefaultContent()
*/	