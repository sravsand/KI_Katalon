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
import global.Object as gObj


Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', 'DCJ Tech Service Delivery')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)
	gAct.Wait(3)

	
Rep.nextTestStep("Click on [Check out] button ")
	WebUI.click(findTestObject(Const.ganttToolbar + 'checkOut'))
	
	
Rep.nextTestStep("Click on [Yes]")
	ganttAct.handleCurrentlyEditedWindow("Yes")
	
		
Rep.nextTestStep("Verify the plan is in [Checked Out] state")
	boolean chkInExist = WebUI.verifyElementPresent(findTestObject(Const.ganttToolbar + 'checkIn'), 5, FailureHandling.OPTIONAL)
	if(chkInExist){
		Rep.pass("plan is in [Checked out] state")
	}else{
		Rep.fail("plan is in [Checked in] state")
	}
	gAct.Wait(3)
	
	
Rep.nextTestStep("Double click any [Task]")
	WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): 2, ('col'): 3]))
	WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): 2, ('col'): 3]))
		
		
Rep.nextTestStep("Verify the task is editable")
	gObj.verifyPresentAndReport(Const.ganttTable + 'ganttNewTaskInput')


Rep.nextTestStep("Close the model")
	WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))

	
Rep.nextTestStep("Verify the Task menu bar has more different editable options available for selection")
	gObj.verifyPresentAndReport(Const.ganttToolbar + 'insertRow')
	gObj.verifyPresentAndReport(Const.ganttToolbar + 'outdent')


Rep.nextTestStep("Click on [Check In] button")
	WebUI.click(findTestObject(Const.ganttToolbar + 'checkIn'))
	WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)

	
Rep.nextTestStep("Click on [Check Out] button")
	WebUI.click(findTestObject(Const.ganttToolbar + 'checkOut'))
	gAct.Wait(3)
	boolean popupNotExists = WebUI.waitForElementNotPresent(findTestObject(Const.editPopUp + 'button_Yes'), 5, FailureHandling.CONTINUE_ON_FAILURE)
	if(popupNotExists){
		Rep.pass("Warning message does not appear.")
	}else{
		Rep.fail("Warning message appeared.")
	}
	gAct.Wait(2)
	
	Rep.nextTestStep("Click on [Check In] button")
	WebUI.click(findTestObject(Const.ganttToolbar + 'checkIn'))
	WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)