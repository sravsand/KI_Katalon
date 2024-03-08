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
	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', 'DCJ Tech Service Delivery')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()

	
Rep.nextTestStep("Enter [0] in [Duration] of any existing task")
	ganttPop.cell(3, 5, 4, "0")


Rep.nextTestStep("Click on [Save] button from toolbar")
	WebUI.click(findTestObject(Const.ganttToolbar + 'save'))
	ganttAct.handleSaveErrorWindow()
	gAct.Wait(2)


Rep.nextTestStep("Verify the Milestone is displaying on [Finish Date]")
	Rep.screenshot("Verify the Milestone is displaying on [Finish Date]")
	Rep.warning("Manual check of Gantt chart required as unable to check via automation")
	
	WebUI.click(findTestObject(Const.ganttToolbar + 'checkIn'))
	WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)
