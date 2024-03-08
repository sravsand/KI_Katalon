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

	
Rep.nextTestStep("Change the duration of task by entering a number in [Duration]")
	int durationValue = 2
	
	String taskEnd = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 3, ('col'): 7]))
	String totalMainDuration = WebUI.getText(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 4]))
	String totalSubDuration = WebUI.getText(findTestObject(Const.ganttTable + 'ganttSubTask',  [('row'): 2, ('col'): 4]))	
	ganttPop.cell(3, 3, 4, durationValue.toString())


Rep.nextTestStep("Verify the finish date is updated & calculated correctly based on set duration")
	int totalMain = totalMainDuration.toInteger() + durationValue.toInteger()
	int totalSub = totalSubDuration.toInteger() + durationValue.toInteger()
	
	String totalMainDurationNew = WebUI.getText(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 4]))
	String totalSubDurationNew = WebUI.getText(findTestObject(Const.ganttTable + 'ganttSubTask',  [('row'): 2, ('col'): 4]))
	
	gAct.compareStringAndReport(totalMainDurationNew, totalMain.toString())
	gAct.compareStringAndReport(totalSubDurationNew, totalSub.toString())
	
	String newTaskEnd = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 3, ('col'): 7]))
	ganttVal.dateIncrease(taskEnd, newTaskEnd, durationValue)

	
Rep.nextTestStep("Click on [Floppy Disk] icon to save the changes")
	WebUI.click(findTestObject(Const.ganttToolbar + 'save'))
	ganttAct.handleSaveErrorWindow()

	
Rep.nextTestStep("Select another task and change start date")
	int startIncrease = 5
	String taskStartDate = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 2, ('col'): 5]))
	String taskEndDate = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 2, ('col'): 7]))
	String taskDuration = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 2, ('col'): 4]))
	
	String newStartDate = ganttAct.increaseDate(taskStartDate, startIncrease, "dd/MM/yyyy")
	ganttPop.dateCell(3, 2, 5, newStartDate)
	
	
Rep.nextTestStep("Verify the duration of the task is not changed")
	String taskDurationUpdate = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 2, ('col'): 4]))
	gAct.compareStringAndReport(taskDurationUpdate, taskDuration)
	
	
Rep.nextTestStep("Verify the finish date is recalculated correctly")
	String newTaskEndDate = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): 3, ('row'): 2, ('col'): 7]))
	String newEndDate = ganttVal.dateIncrease(taskEndDate, newTaskEndDate, startIncrease -1)
