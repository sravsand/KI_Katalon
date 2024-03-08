import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myWork.Action as mAct
import myWork.Validate as mVal

//change from default login
GVars.configFileName = 	"katalonConfigAuto5.xml"
Act.changeUserCredentials()

String myWorkView = "Test Delete View"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	
	
Rep.nextTestStep("Click onto the tab (that needs deleting) i.e Test view tab")
	gObj.buttonClickSubSync(Const.dashBoard + 'selectView', myWorkView, "view")
	
	
Rep.nextTestStep("Click onto [Settings] button ( top right )")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	
	
Rep.nextTestStep("Click [Manage Views]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Manage Views')
	
	
Rep.nextTestStep("Click [Delete] (X) button for tab to be deleted")
	gObj.buttonClickSubSync(Const.manageViewsConfig + 'unpublishedView_Item', myWorkView, "view")
	gObj.buttonClickSubIntPosSync(Const.manageViewsConfig + 'deleteView', 2, "row")

	
Rep.nextTestStep("Click [OK]")
	gObj.buttonClickSync(Const.manageViewsConfig + 'button_OK')
	
	
Rep.nextTestStep("Click [Save] button next to layout button")
	gObj.buttonClickSync(Const.manageViewsConfig + 'button_Save')
	gAct.Wait(1)
	
	boolean viewDeleted = WebUI.verifyElementNotPresent(findTestObject(Const.dashBoard + 'selectView', [('view'): myWorkView]), GVars.midWait)
	if(viewDeleted){
		Rep.pass("View " + myWorkView + " has been deleted.")
	}else{
		Rep.fail("View " + myWorkView + " has not been deleted.")
	}

