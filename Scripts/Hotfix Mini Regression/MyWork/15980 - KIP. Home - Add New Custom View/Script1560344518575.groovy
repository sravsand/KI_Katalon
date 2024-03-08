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


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My work] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")
	
	
Rep.nextTestStep("Click onto [Spanner] Settings button ( top right )")
	gObj.buttonClickSync(Const.myWork + 'spannerSettings')
	

Rep.nextTestStep("Click onto [Add New View]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Add New View')
	
	
Rep.nextTestStep("Enter some Data into [Name] Field")
	String newWidget = "All New Work View"
	gObj.setEditSync(Const.createViewDialog + 'createViewName', newWidget)
	
	
Rep.nextTestStep("Click [Save] ")
	gObj.buttonClickSync(Const.createViewDialog + 'createViewSave')
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click onto [Spanner] Settings button ( top right )")
	gObj.buttonClickSync(Const.myWork + 'spannerSettings')

	
Rep.nextTestStep("Click on [Configure Current View]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Configure Current View')

	
Rep.nextTestStep("Click onto [+] Sign of a section i.e Project ")
	gObj.buttonClickSync(Const.configureViewMenu + 'plusSymbol')

	
Rep.nextTestStep("Select a widget from the displayed list to be added")
	def test = gObj.elementVisibleSync(Const.addWidget + 'a_Action Views')
	gObj.buttonClickSync(Const.addWidget + 'a_Action Views')
	gObj.buttonClickSubIntPosSync(Const.addWidget + 'actionViewsButton', 1, "button")

	
Rep.nextTestStep("Verify widget is added to the right section")
	String widgetName = gObj.getEditTextSync(Const.widgetView + 'widgetViewName')
	gAct.compareStringAndReport(widgetName, newWidget)
	boolean widgetPresent = WebUI.verifyElementPresent(findTestObject(Const.widgetView + 'WidgetName',[('widget'): 'All Actions']), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
	
	if(widgetPresent){
		Rep.pass("Widget has been created and added to the correct View")
	}else{
		Rep.fail("Widget has been not been created or added to the correct View")
		Rep.screenshot("Widget has been not been created or added to the correct View")
	}
	
	
Rep.nextTestStep("Click [Floppy Disk]  button next to Layout button")
	gObj.buttonClickSync(Const.widgetView + 'widgetSave')

