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
import global.WebDriverMethods as WebD

import java.text.SimpleDateFormat


Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Project Innovation')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()

	
Rep.nextTestStep("Change the [Finish] date to +3 days")
	int durationValue = 3
	
	String taskEnd = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 7, ('row'): 2, ('col'): 7]))
	String totalTaskDuration = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem',  [('sub'): 7, ('row'): 2, ('col'): 4]))
	String newEndDate = ganttAct.increaseDate(taskEnd, durationValue, "dd/MM/yyyy")
	ganttPop.dateCell(7, 2, 7, newEndDate)
	float increaseDur = totalTaskDuration.toFloat() + durationValue.toInteger()

	
Rep.nextTestStep("Verify the increased days are added onto duration")
	String newTaskDuration = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem',  [('sub'): 7, ('row'): 2, ('col'): 4]))
	gAct.compareStringAndReport(newTaskDuration, increaseDur.toString())

	
Rep.nextTestStep("Change the [Finish] date to -6 days")
	int newDurationValue = 6
	String newTaskEnd = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 7, ('row'): 2, ('col'): 7]))
	String updatedEndDate = ganttAct.decreaseDate(newTaskEnd, newDurationValue, "dd/MM/yyyy")
	ganttPop.dateCell(7, 2, 7, updatedEndDate)
	
	
Rep.nextTestStep("Verify the decreased days are deducted from the duration")
	String updatedTaskDuration = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem',  [('sub'): 7, ('row'): 2, ('col'): 4]))
	float decreaseDur = newTaskDuration.toFloat() - newDurationValue.toInteger()
	gAct.compareStringAndReport(updatedTaskDuration, decreaseDur.toString())

