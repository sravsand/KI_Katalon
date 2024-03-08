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
import gantt.Object as ganttObj

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
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

	
Rep.nextTestStep("Click onto [+] within the task bar menu")
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "insertRow"))
	
	
Rep.nextTestStep("Click into new added row and Enter text into [Task Name] ")
	String tsk1 = "RollBack Test task"
	ganttPop.newTask(4, 3, tsk1)
	
	
Rep.nextTestStep("Click onto [Rollback] Button")
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "rollback"))


Rep.nextTestStep("Click No")
	boolean chkRoleBack = WebUI.verifyElementPresent(findTestObject(Const.rolebackMsg + 'rolebackMessage'), 5, FailureHandling.OPTIONAL)	
	if(chkRoleBack){
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.rolebackMsg + 'button_No'))
	}else{
		Rep.fail("Roleback popup did not appear.")
	}
		
	ganttVal.topLevelTaskValue(4, 3, "RollBack Test task")	
		
	
Rep.nextTestStep("Click onto [Rollback] Button")
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "rollback"))
		

Rep.nextTestStep("Click Yes")
	chkRoleBack = WebUI.verifyElementPresent(findTestObject(Const.rolebackMsg + 'rolebackMessage'), 5, FailureHandling.OPTIONAL)
	if(chkRoleBack){
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.rolebackMsg + 'button_Yes'))
	}else{
		Rep.fail("Roleback popup did not appear.")
	}
	
	ganttObj.verifyCellNotPresentAndReport(Const.ganttTable + 'ganttTask', 4, 3)
	
	
Rep.info("Extra steps to cover Test Case 24826")


Rep.nextTestStep("Click onto [Roll Back] button")
	gAct.Wait(2)
	WebUI.click(findTestObject(Const.ganttToolbar + "rollback"))

	
Rep.nextTestStep("Click [Save]")
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.ganttToolbar + "save"))
	ganttAct.handleSaveErrorWindow_NotExpected()