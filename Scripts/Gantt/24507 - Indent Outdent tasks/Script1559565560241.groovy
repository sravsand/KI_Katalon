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
	

Rep.nextTestStep("Select few random tasks through radio button and click indent button")
	ganttAct.indentSubTask(4, 3)
	ganttAct.indentSubTask(4, 3)

	
Rep.nextTestStep("Verify indents")
	ganttVal.subTaskValue(3, 4, 3, "Development")
	ganttVal.subTaskValue(3, 6, 3, "Testing")
	ganttVal.subItemValue(3, 5, 2, 3, "Development Phase")
	ganttVal.subItemValue(3, 7, 3, 3, "Testing Complete")

	
Rep.nextTestStep("Click on [Floppy Disk] icon")
	WebUI.click(findTestObject(Const.ganttToolbar + 'save'))
	ganttAct.handleSaveErrorWindow_NotExpected()

		
Rep.nextTestStep("Select any [Child Tasks] through radio button and click outdent button")
	ganttAct.outdentSubTask(5, 3, 3)
	
	
Rep.nextTestStep("Verify outdents")
	ganttVal.taskValue(6, 3, "Deployment Complete")
