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
import buildingBlocks.createComponentsLogedIn as component

//change from default login
GVars.configFileName = 	"katalonConfigAuto2.xml"
Act.changeUserCredentials()
String browser = Com.getBrowserType()

String myWorkView = "Public View Test " + browser

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	component.addNewViewAndWidget_MyProjects(myWorkView, 1, 'All Actions')
	
	
Rep.nextTestStep("Click onto [My projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	gObj.buttonClickSubSync(Const.dashBoard + 'selectView', myWorkView, "view")
	
	
Rep.nextTestStep("Click onto [Settings] button ( top right )")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	
	
Rep.nextTestStep("Select [Publish View] option from the list")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Publish View')

	
Rep.nextTestStep("Tick [Reset Individual Layout] check boxes")
	gObj.checkSync(Const.publishConfig + 'chkResetLayouts')
	
	
Rep.nextTestStep("Tick [Reset Individual Widget Configuration] check boxes")
	gObj.buttonClickSync(Const.publishConfig + 'chkResetConfig')

	
Rep.nextTestStep("Click [Save] button")
	WebUI.click(findTestObject(Const.publishConfig + 'button_Save'))
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Click onto [Settings] button ( top right )")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	
	
Rep.nextTestStep("Click [Manage Views]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Manage Views')
	gAct.Wait(GVars.shortWait)


Rep.nextTestStep("Verify the recently [Published] view is displayed in the [Published Views] section")
	String published = gObj.getEditTextSync(Const.manageViewsConfig + 'publishedViews')
	gAct.findSubstringInStringAndReport(published, myWorkView + '\n (' + GVars.user + ')')

	gObj.buttonClickSync(Const.manageViewsConfig + 'button_Close')
	