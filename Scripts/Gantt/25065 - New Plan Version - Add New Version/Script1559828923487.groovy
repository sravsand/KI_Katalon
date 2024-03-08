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
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Tech Crunch Web Services Deployment')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()
	
	
Rep.nextTestStep("Ensure a published Project Plan version is displayed")
	String ganttStatus =  WebUI.getText(findTestObject(Const.gantt + 'ganttStatus'))
	gAct.compareStringAndReport(ganttStatus, "(Published)")


Rep.nextTestStep("Click onto [Create new version of the plan]")
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + 'createNewPlanVers'))
	
	
Rep.nextTestStep("Verify The new screen display the project code, name and version number for the new plan and will allow the description of the new plan to be entered. ")
	boolean newVersionExist = WebUI.verifyElementPresent(findTestObject(Const.ganttNewVersion + 'newPlanVersion'), 5, FailureHandling.OPTIONAL)
	String version
	if(newVersionExist){
		version = WebUI.getText(findTestObject(Const.ganttNewVersion + 'newVersionNo'))	
	}else{
		FailureHandling.STOP_ON_FAILURE
		Rep.fail("New version screen has not appeared.")
	}

	
Rep.nextTestStep("Click onto [Description] Field and Enter data [Up to 70 Char]")
	String newVersionName = "New Version of gantt, version no : "
	WebUI.setText(findTestObject(Const.ganttNewVersion + 'input_NewVersionDescription'), newVersionName)

	
Rep.nextTestStep("Click [OK]")
	WebUI.click(findTestObject(Const.ganttNewVersion + 'button_OK'))
	gAct.Wait(2)
	WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)
	ganttAct.refresh()
	
	String newGanttStatus =  WebUI.getText(findTestObject(Const.gantt + 'ganttStatus'))
	gAct.compareStringAndReport(newGanttStatus, newVersionName + "(Draft)")
	
gAct.Wait()