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
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Arch Now Web Services Implementation')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()

	
Rep.nextTestStep("Add a new Row in task plan and change name")
	String tsk1 = "Test Task"
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "insertRow"))
	ganttPop.newTask(4, 3, tsk1)
	ganttVal.topLevelTaskValue(4, 3, "Test Task")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Navigate away from [Tasks] page")	
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	WebUI.click(findTestObject(Const.dashBoard + "menu_Timesheets"))


Rep.nextTestStep("Click [Continue Without Saving]")
	boolean chkOutExist = WebUI.verifyElementPresent(findTestObject(Const.ganttSaveChanges + 'SaveChangesMessage'), 5, FailureHandling.OPTIONAL)
	if(chkOutExist){
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttSaveChanges + 'button_ContinueWithoutSaving'))
		gAct.Wait(1)
	}


Rep.nextTestStep("Click onto [My Project] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")


Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()
	
	
Rep.nextTestStep("Verify the plan changes are not saved")
	boolean chkNotSaved = WebUI.verifyElementNotPresent(findTestObject(Const.ganttTable + 'ganttTask', [('row'): 4, ('col'): 4]), 5, FailureHandling.CONTINUE_ON_FAILURE)
	if(chkNotSaved){
		Rep.pass("Changes not saved.")
	}else{
		Rep.fail("Changes were saved.")
		Rep.screenshot("Task_" + tsk1 + "_savedInError")		
	}
	