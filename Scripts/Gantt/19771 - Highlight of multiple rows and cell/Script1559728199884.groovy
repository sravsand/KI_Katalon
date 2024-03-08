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


Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', 'Efficiency Solution')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()

	
Rep.nextTestStep("Change the name of any existing [Task] from the plan")
	String newTaskName = "Requirements Testing"
	ganttPop.updateTask(2, 3, newTaskName)
	WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): 10, ('col'): 3]))
	
	
Rep.nextTestStep("Verify the amended row is highlighted with a different colour")
	gAct.Wait(1)
	String colourTaskNew = WebUI.getAttribute(findTestObject(Const.ganttTable + 'ganttRow', [('task'): newTaskName]), 'style')	
	gAct.compareStringAndReport(colourTaskNew, "background-color: rgb(218, 218, 238);")
	

Rep.nextTestStep("Add a [New] row to the plan")
	String tsk1 = "NewTestingTask"
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "insertRow"))
	ganttPop.newTask(4, 3, tsk1)
	
		
Rep.nextTestStep("Verify the row is highlighted with a different colour")
	String colourNewTask = WebUI.getAttribute(findTestObject(Const.ganttTable + 'ganttRow', [('task'): tsk1]), 'style')
	gAct.compareStringAndReport(colourNewTask, "background-color: rgb(198, 223, 167);")

	
Rep.nextTestStep("Amend any existing child task")
	String itemName = "Testing Phase"
	String newItemName = "Testing Phase Started"
	String colour = WebUI.getAttribute(findTestObject(Const.ganttTable + 'ganttRow', [('task'): itemName]), 'style')
	gAct.compareStringAndReport(colour, "")
	ganttPop.updateCell(7, 2, 3, newItemName)
	
	
Rep.nextTestStep("Verify the amended row is highlighted with a different colour")
	gAct.Wait(1)	
	String colourNew = WebUI.getAttribute(findTestObject(Const.ganttTable + 'ganttRow', [('task'): newItemName]), 'style')
	gAct.compareStringAndReport(colourNew, "background-color: rgb(218, 218, 238);")
