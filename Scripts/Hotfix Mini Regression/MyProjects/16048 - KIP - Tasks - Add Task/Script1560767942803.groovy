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
import keyedInProjects.Populate as Pop
import gantt.Populate as ganttPop
import gantt.Validate as ganttVal
import gantt.Action as ganttAct

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD


GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()
String browser = Com.getBrowserType()

Rep.nextTestStep("Log in V6")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Project Innovation_' + browser)

	
Rep.nextTestStep("Click onto [Tasks] tab")
	gObj.buttonClickSync(Const.myProjects + "menu_Tasks")
	ganttAct.checkoutCurrentProject()
	String taskEnd = "Tue, 17 Sep 2019"
//	String taskEnd = WebUI.getText(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): 5, ('col'): 5]))
	
Rep.nextTestStep("Add a new Row in task plan")
	String tsk1 = "New Task"
	gObj.buttonClickSync(Const.ganttToolbar + "insertRow")
	WebUI.doubleClick(findTestObject(Const.ganttTable + 'ganttNewRow', [('row'): 4, ('col'): 3]))

	
Rep.nextTestStep("Enter data into [Description] field")
	gObj.buttonClickSync(Const.ganttTaskDetails + 'Description_Name')
	gObj.setEditSync(Const.ganttTaskDetails + 'Description_Name', tsk1)
	
	
Rep.nextTestStep("Select an [Activity]")
	gObj.buttonClickSync(Const.ganttTaskDetails + 'activityDropDown')
	gObj.buttonClickSubSync(Const.ganttTaskDetails + "selectActivityByName", "Meetings", "activity")

	
Rep.nextTestStep("Select [Start Date] and [Time]")
	String newStartDate = ganttAct.increaseDate(taskEnd, 1, "dd/MM/yyyy")
	gObj.setEditSync(Const.ganttTaskDetails + 'input__StartDate', newStartDate)
	gObj.buttonClickSync(Const.ganttTaskDetails + 'input__StartDate_Time')
	gObj.setEditSync(Const.ganttTaskDetails + 'input__StartDate_Time', "08:00")
	

Rep.nextTestStep("Select a [Finish Date] and [Time]")
	String newEndDate = ganttAct.increaseDate(taskEnd, 4, "dd/MM/yyyy")
	gObj.setEditSync(Const.ganttTaskDetails + 'input__EndDate', newEndDate)
	gObj.buttonClickSync(Const.ganttTaskDetails + 'input__EndDate_Time')
	gObj.setEditSync(Const.ganttTaskDetails + 'input__EndDate_Time', "16:00")

	
Rep.nextTestStep("Enter [Effort] hours")
	gObj.clearSetEditSync(Const.ganttTaskDetails + 'input_Effort', "12")

	
Rep.nextTestStep("Click onto each of the tabs within the task edit window and verify")
	gObj.buttonClickSync(Const.ganttTaskDetails + 'TaskSkillsTab')
	gObj.verifyVisibleAndReport(Const.ganttTaskDetails + 'label_RequiredSkills')
	gObj.buttonClickSync(Const.ganttTaskDetails + 'TaskAssignmentsTab')
	gObj.verifyVisibleAndReport(Const.ganttTaskDetails + 'label_Assignments')
	gObj.buttonClickSync(Const.ganttTaskDetails + 'TaskPredecessorsTab')
	gObj.verifyVisibleAndReport(Const.ganttTaskDetails + 'label_Predecessors')
	

Rep.nextTestStep("Click onto [OK] button at the bottom left of the page")
	gObj.buttonClickSync(Const.ganttTaskDetails + 'button_OK')
	gAct.Wait(1)
	ganttVal.topLevelTaskValue(4, 3, tsk1)

