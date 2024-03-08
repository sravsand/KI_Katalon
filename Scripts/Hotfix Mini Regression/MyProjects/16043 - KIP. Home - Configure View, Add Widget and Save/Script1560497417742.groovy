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
import org.openqa.selenium.Keys as Keys
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
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()
String myWorkView = "Basic View"


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Project Innovation')
	gObj.buttonClickSubSync(Const.dashBoard + 'selectView', myWorkView, "view")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [Spanner] Settings button ( top right )")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	gAct.Wait(GVars.shortWait)

	
Rep.nextTestStep("Click on [Configure Current View]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Configure Current View')
	
	
Rep.nextTestStep("Add [Expenses] tile to the page by clicking the [+] (Add) icon")
	gObj.buttonClickSync(Const.configureViewMenu + 'plusSymbol')
	WebUI.switchToDefaultContent()
	gAct.Wait(1)
	gObj.buttonClickSync(Const.addWidget + 'a_Expenses')
	gObj.buttonClickSync(Const.addWidget + 'addExpensesButton')
	gObj.buttonClickSync(Const.widgetView + 'widgetSave')
	

Rep.nextTestStep("Click on the [Settings] button on the [Expenses] tile to configure the details to display")
//	gObj.buttonClickSubSync(Const.widgetView + 'a_newWidgetConfig', "Expenses", "widge")
	gObj.buttonClickDblSubGenSync(Const.widgetView + 'a_newWidgetConfig', "Expenses", "widge", 2, "pos")
//	gObj.buttonClickSync(Const.widgetView + 'a_newWidgetConfig')
	
	
Rep.nextTestStep("Select the required [Expense] from the dropdown list")
	gObj.buttonClickSync(Const.widgetConfig + 'DefaultFilterList')
	gObj.setEditSync(Const.widgetConfig + 'DefaultFilterSearch', "All Expenses")
	gObj.buttonClickSubSync(Const.widgetConfig + 'DefaultFilterChoice', 'All Expenses', "choice")
	
	
Rep.nextTestStep("Click [Save] and close window after confirmation message")
	gObj.buttonClickSync(Const.widgetConfig + 'button_Save')
	gAct.Wait(2)
	String saveMessage =  gObj.getEditTextSync(Const.widgetConfig + 'widgetConfigSaveMessage')
	if(GVars.browser == "MicrosoftEdge"){
		gAct.findSubstringInStringAndReport(saveMessage, "Configuration saved successfully")
	}else{
		gAct.compareStringAndReport(saveMessage, "×\nConfiguration saved successfully")
	}
	gObj.buttonClickSync(Const.widgetConfig + 'button_Close')
	
	
Rep.nextTestStep("Drag and Drop [Projects] tile to change its location")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Configure Current View')
	gAct.Wait(1)
	
	int leftPosition = WebUI.getElementLeftPosition(findTestObject(Const.configureViewMenu + 'ProjectsWidget'))
	
	if(GVars.browser == "Firefox"){
		WebUI.scrollToElement(findTestObject(Const.configureViewMenu + 'ProjectsWidget'), 10)
	}
	
	if(GVars.browser == "MicrosoftEdge" || GVars.browser == "Firefox"){
		int yCo = WebUI.getElementLeftPosition(findTestObject(Const.configureViewMenu + 'TimesheetsWidget'))	
		int newX = yCo - leftPosition
		WebUI.dragAndDropByOffset(findTestObject(Const.configureViewMenu + 'ProjectsWidget'), newX, 0)
	}else{
		WebUI.dragAndDropToObject(findTestObject(Const.configureViewMenu + 'ProjectsWidget'), findTestObject(Const.configureViewMenu + 'TimesheetsWidget'))
	}
	
	int newLeftPosition = WebUI.getElementLeftPosition(findTestObject(Const.configureViewMenu + 'ProjectsWidget'))

	
Rep.nextTestStep("Drag and Drop [Timesheets] tile to change its location")
	int leftPositionTimeSheet = WebUI.getElementLeftPosition(findTestObject(Const.configureViewMenu + 'TimesheetsWidget'))
	
	if(GVars.browser == "Firefox"){
		WebUI.scrollToElement(findTestObject(Const.configureViewMenu + 'TimesheetsWidget'), 10)
	}
	
	if(GVars.browser == "MicrosoftEdge"){
		int yCoCollab = WebUI.getElementLeftPosition(findTestObject(Const.configureViewMenu + 'CollaborationWidget'))
		int newX = yCoCollab - leftPositionTimeSheet
		WebUI.dragAndDropByOffset(findTestObject(Const.configureViewMenu + 'TimesheetsWidget'), newX, 0)
	}else{
		WebUI.dragAndDropToObject(findTestObject(Const.configureViewMenu + 'TimesheetsWidget'), findTestObject(Const.configureViewMenu + 'CollaborationWidget'))
	}
	
	int newLeftPositionTimeSheet = WebUI.getElementLeftPosition(findTestObject(Const.configureViewMenu + 'TimesheetsWidget'))
		

Rep.nextTestStep("Click on the [Settings] button on the [Projects] tile ")
	gObj.buttonClickDblSubGenSync(Const.widgetView + 'main_widgetConfig', 'Add', "widget", 1, "pos")
	gObj.buttonClickSync(Const.widgetView + 'button_Close')
	
	
Rep.nextTestStep("Click the [Delete] button on the [Collaboration] section")
	gObj.buttonClickSync(Const.configureViewMenu + 'button_DeleteCollaboration')
	gAct.Wait(1)
	boolean collaborationExists = WebUI.verifyElementNotPresent(findTestObject(Const.configureViewMenu + 'CollaborationWidget'), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
	if(collaborationExists){
		Rep.pass("Collaboration has been deleted")
	}else{
		Rep.fail("Collaboration has not been deleted")
	}
	
	
Rep.nextTestStep("Click the [Save] button (floppy disk icon) next to [Layout] button ")
	WebUI.sendKeys(findTestObject(Const.configureViewMenu + 'button_Save'), Keys.chord(Keys.PAGE_UP))
	gObj.buttonClickSync(Const.configureViewMenu + 'button_Save')
//	WebD.clickElement("//*[@href = '#' and @title = 'Save' and @class = 'dashboard-save btn btn-sm btn-default']")

//	gObj.buttonClickSync(Const.configureViewMenu + 'button_Save')
	gAct.Wait(1)
	boolean saveExists = WebUI.verifyElementNotVisible(findTestObject(Const.configureViewMenu + 'button_Save'), FailureHandling.CONTINUE_ON_FAILURE)
	if(saveExists){
		Rep.pass("Screen is no longer in configuration mode and all changes are saved")
	}else{
		Rep.fail("Screen is still in configuration mode.")
	}
	
