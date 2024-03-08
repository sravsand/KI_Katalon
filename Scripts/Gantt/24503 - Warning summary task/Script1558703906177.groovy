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
	

Rep.nextTestStep("Create 2 Tasks in plan i.e Task1 & Task 2")
	String tsk1 = "Task1"
	String tsk2 = "Task2"
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "insertRow"))
	ganttPop.newTask(4, 3, tsk1)	
	WebUI.click(findTestObject(Const.ganttToolbar + "insertRow"))
	ganttPop.newTask(5, 3, tsk2)

	
Rep.nextTestStep("Add an [Assignment] to Task 1 (first task)")
	String assMnt = "KIP1TEST"
	WebUI.click(findTestObject(Const.ganttTable + 'ganttNewRow', [('row'): 4, ('col'): 3]))
	ganttPop.newAssignment(2, 2, assMnt)	
	WebUI.click(findTestObject(Const.ganttTable + 'ganttNewRow', [('row'): 5, ('col'): 3]))
	WebUI.click(findTestObject(Const.ganttTable + 'ganttNewRow', [('row'): 4, ('col'): 3]))
	
	ganttVal.assignmentAdded(2, 2, "KIP 1 Tester")
	ganttVal.assignmentAdded(2, 5, tsk1)

	
Rep.nextTestStep("Select [Task 2] through radio button")
	WebUI.clickOffset(findTestObject(Const.ganttTable + 'selectRow', [('row'): 5]), 35, 15)

	
Rep.nextTestStep("Click on [Indent] icon from task toolbar")
	WebUI.click(findTestObject(Const.ganttToolbar + 'indent'))
	
	def alertText = WebUI.getAlertText()
	gAct.findSubstringInStringAndReport(alertText, "become a Summary Task, all its Assignments will be lost.")
	
	WebUI.dismissAlert()
