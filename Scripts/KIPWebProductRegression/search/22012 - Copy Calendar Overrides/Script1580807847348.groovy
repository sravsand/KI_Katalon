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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Action as sAct
import search.Validate as sVal
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.WorkingTimes
import models.GenerateWorkingTimes

String searchItem = "Working Times"
String searchVal = "Working Time"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	def pattern = "dd/MM/yyyy"
	def weekCommence = Com.weekStartDate()
	def date = Date.parse(pattern, weekCommence)
	
	def weekEnd = Com.increaseWorkingCalendarWithFormat(date, 12, "dd/MM/yyyy")
	
	String workingTimeTitle = Com.generateRandomText(7)
	String workingTimeCode = workingTimeTitle.toUpperCase()
	
	String[] newWorkingTime = [workingTimeTitle, workingTimeCode, "7", "1"]
	WorkingTimes workingTimes = GenerateWorkingTimes.createWorkingTimes(newWorkingTime)
	component.createWorkingTimes(workingTimes)
	
	String newWorkingTimeTitle = Com.generateRandomText(7)
	String newWorkingTimeCode = newWorkingTimeTitle.toUpperCase()
	
	String[] newWorkingTime2 = [newWorkingTimeTitle, newWorkingTimeCode, "8", "1"]
	WorkingTimes secondWorkingTimes = GenerateWorkingTimes.createWorkingTimes(newWorkingTime2)
	component.createWorkingTimes(secondWorkingTimes)

	
Rep.nextTestStep("Click on the [Configuration] cog icon located at the top right hand side of the main menu bar")
	gObj.buttonClickSync(Const.mainToolBar + "settings")
	
	
Rep.nextTestStep("Click on [Working Times] from the options list ")
	gObj.buttonClickSync(Const.settingsMenu + "WorkingTimes")
	
	
Rep.nextTestStep("Click on the [Copy Calendar Overrides] button")
	gObj.buttonClickSync(Const.calendarOverride + "a_Copy Calendar Overrides")

	
Rep.nextTestStep("Select [Full Time Calendar (UK)] from the [Source Working Time] dropdown field")
	gObj.buttonClickSync(Const.calendarOverride + "sourceWorkingTimeDropDown")
	gObj.buttonClickSubSync(Const.calendarOverride + "selectSourceWorkingTime", workingTimes.title, "select")


Rep.nextTestStep("Verify that the code is displayed next to the [Source Working Time] dropdown field")
	String sourceAdded = gObj.getEditTextSync(Const.calendarOverride + "sourceWorkingTimeDropDown")


Rep.nextTestStep("Select the [Merge] radio button")
	gObj.buttonClickSync(Const.calendarOverride + "Radio_Merge")


Rep.nextTestStep("Select a valid [Period] by selecting dates within the [From] and [To] calendar fields")
	gObj.clearAndSetText(Const.calendarOverride + "input_PeriodFrom", weekCommence)
	gObj.clearAndSetText(Const.calendarOverride + "input_PeriodTo", weekEnd)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click inside the [Destination Working Times] textbox field and select [Working Times]")
	gObj.buttonClickSync(Const.calendarOverride + "inputDestinationWorkingTime")
	gObj.buttonClickSubSync(Const.calendarOverride + "selectDestinationWorkingTime", secondWorkingTimes.title, "select")


Rep.nextTestStep("Click [Copy]")
	WebUI.click(findTestObject(Const.calendarOverride + "button_Copy"))
	gAct.Wait(GVars.shortWait)


Rep.nextTestStep("Click [X] to delete the selected [Working Time] within [Destination Working Times]")
	WebD.clickElement("//a[@class='select2-search-choice-close']")


Rep.nextTestStep("Click inside the [Destination Working Times] textbox field and select [Full Time Calendar (UK)]")
	gObj.buttonClickSync(Const.calendarOverride + "inputDestinationWorkingTime")
	gObj.buttonClickSubSync(Const.calendarOverride + "selectDestinationWorkingTime", workingTimes.title, "select")

	
Rep.nextTestStep("Click [Copy] ")
	gObj.buttonClickSync(Const.calendarOverride + "button_Copy")
	WebUI.verifyElementVisible(findTestObject(Const.calendarOverride + "ValidationError"))

	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.calendarOverride + "button_Close")
