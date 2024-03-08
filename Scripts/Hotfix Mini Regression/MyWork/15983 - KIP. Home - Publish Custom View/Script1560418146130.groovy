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

//change from default login
GVars.configFileName = 	"katalonConfigAuto3.xml"
Act.changeUserCredentials()
String browser = Com.getBrowserType()

String myWorkView = "AutoTest View " + browser

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My work] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")
	gObj.buttonClickSubSync(Const.dashBoard + 'selectView', myWorkView, "view")
	
	
Rep.nextTestStep("Click onto [Settings] button ( top right )")
	gObj.buttonClickSync(Const.myWork + 'spannerSettings')
	
	
Rep.nextTestStep("Select [Publish View] option from the list")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Publish View')

	
Rep.nextTestStep("Update [Name] field   e.g. TEST View - If applicable ")
	String publishName = gObj.getAttributeSync(Const.publishConfig + 'publishViewName', 'value')
	gAct.compareStringAndReport(publishName, myWorkView)
	

Rep.nextTestStep("Add / Enter [Login group] i.e Portfolio Manager - If applicable")
	String groupName = gObj.getEditTextSync(Const.publishConfig + 'publishLoginGroup')
	gAct.compareStringAndReport(groupName, "KIP Admin")


Rep.nextTestStep("Tick [Reset Individual Layout] and [Reset Individual Widget Configuration] check boxes")
	gObj.checkSync(Const.publishConfig + 'chkResetLayouts')
	gObj.checkSync(Const.publishConfig + 'chkResetConfig')

	
Rep.nextTestStep("Click [Save] button")
	gObj.buttonClickSync(Const.publishConfig + 'button_Save')
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Click onto [Settings] button ( top right )")
	gObj.buttonClickSync(Const.myWork + 'spannerSettings')
	
	
Rep.nextTestStep("Click [Manage Views]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Manage Views')
	gAct.Wait(2)


Rep.nextTestStep("Verify the recently [Published] view is displayed in the [Published Views] section")
	String published = gObj.getEditTextSync(Const.manageViewsConfig + 'publishedViews')
	
	gAct.findSubstringInStringAndReport(published, GVars.user)
	
	gObj.buttonClickSync(Const.manageViewsConfig + 'button_Close')
