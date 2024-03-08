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
import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.annotation.TearDownTestCase
import buildingBlocks.createComponents

String tsk1 = "New inserted Task"
Rep.nextTestStep("Pre-Req: A mandatory Custom field is required in Task Plan")
	setupTestCase(tsk1)


Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click onto [My Project] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Tech Crunch Web Services Deployment')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()

	
Rep.nextTestStep("Add a new Row in task plan and change name")
	ganttPop.newTaskBelow(4, 3, tsk1)	
	ganttVal.taskValue(5, 3, tsk1)
	

Rep.nextTestStep("Click on [Save] button from toolbar")
	WebUI.click(findTestObject(Const.ganttToolbar + 'save'))
	ganttAct.handleSaveErrorWindow()
	gAct.Wait(2)


Rep.nextTestStep("Remove mandatory custom field")
	WebUI.closeBrowser()
	TearDownTestCase(tsk1)
		

//Test Case specific setup and teardown methods

@SetupTestCase()
def setupTestCase(String tsk1){
	createComponents.customTaskField(tsk1)
}


@TearDownTestCase()
def TearDownTestCase(String tsk1){
	Act.removeCustomTaskField(tsk1)
}

