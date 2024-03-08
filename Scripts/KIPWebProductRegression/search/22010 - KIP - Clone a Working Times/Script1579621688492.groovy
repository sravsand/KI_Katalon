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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.WorkingTimes
import models.GenerateWorkingTimes

String searchItem = "Working Times"
String searchVal = "Working Time"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String workingTimeTitle = Com.generateRandomText(7)
	String workingTimeCode = workingTimeTitle.toUpperCase()
	
	String[] newWorkingTime = [workingTimeTitle, workingTimeCode, "8", "1"]
	WorkingTimes workingTimes = GenerateWorkingTimes.createWorkingTimes(newWorkingTime)
	component.createWorkingTimes(workingTimes)
	
	String clonedWorkingTimeTitle = Com.generateRandomText(7)
	String clonedWorkingTimeCode = clonedWorkingTimeTitle.toUpperCase()
	
	String[] newClonedWorkingTime = [clonedWorkingTimeTitle, clonedWorkingTimeCode, "8", "1"]
	WorkingTimes clonedWorkingTimes = GenerateWorkingTimes.createWorkingTimes(newClonedWorkingTime)


Rep.nextTestStep("Select [Working Times] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Resolution Plan Items")
	int rowNo = sVal.searchTableReturnRow(3, workingTimes.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneContract")
	

Rep.nextTestStep("Edit data in [Name] textbox")
	gObj.setEditSync(Const.addWorkingTime + "input_Name", clonedWorkingTimes.title)
	gObj.buttonClickSync(Const.addWorkingTime + "a_General")
	
	
Rep.nextTestStep("Add text in [Code] textbox ")
	gObj.setEditSync(Const.addWorkingTime + "input_Code", clonedWorkingTimes.code)
	
	
Rep.nextTestStep("Verify the [Full Time Hours Per Day] textbox is prefilled ")
	String fThours = gObj.getAttributeSync(Const.addWorkingTime + 'input_FullTimeHoursPerDay', 'value')
	gAct.compareStringAndReport(fThours, clonedWorkingTimes.fullTimeHours)
	

Rep.nextTestStep("Verify the [FTE] textbox is prefilled")
	String FTE = gObj.getAttributeSync(Const.addWorkingTime + 'input_FTE', 'value')
	gAct.compareStringAndReport(FTE, clonedWorkingTimes.fte)

	
Rep.nextTestStep("Select Monday to Friday through checkbox")
	String sun = WebD.getCheckBoxState("//input[@id='SundayWorkingEnabled']")
	gAct.compareStringAndReport(sun, null)
	
	String mon = WebD.getCheckBoxState("//input[@id='MondayWorkingEnabled']")
	gAct.compareStringAndReport(mon, "true")
	
	String tues = WebD.getCheckBoxState("//input[@id='TuesdayWorkingEnabled']")
	gAct.compareStringAndReport(tues, "true")
	
	String weds = WebD.getCheckBoxState("//input[@id='WednesdayWorkingEnabled']")
	gAct.compareStringAndReport(weds, "true")
	
	String thurs = WebD.getCheckBoxState("//input[@id='ThursdayWorkingEnabled']")
	gAct.compareStringAndReport(thurs, "true")
	
	String fri = WebD.getCheckBoxState("//input[@id='FridayWorkingEnabled']")
	gAct.compareStringAndReport(fri, "true")
	
	String sat = WebD.getCheckBoxState("//input[@id='SaturdayWorkingEnabled']")
	gAct.compareStringAndReport(sat, null)
					
		
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.addWorkingTime + 'button_Save and Close')
	gAct.Wait(GVars.shortWait)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, clonedWorkingTimes.title)
