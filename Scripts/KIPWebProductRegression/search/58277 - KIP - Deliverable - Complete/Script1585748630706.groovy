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
import models.Deliverable
import models.GenerateDeliverable

String searchItem = "Deliverables"
String searchVal = "Assignment"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	String DeliverableName = Com.generateRandomText(8)
	
	String[] newDeliverable = [DeliverableName, GVars.user, "Project Innovation_" + GVars.selectedBrowser, "Billing Deliverable", endDate]
	Deliverable deliverable = GenerateDeliverable.createDeliverable(newDeliverable)
	component.createDeliverableAndConfirm(deliverable)


Rep.nextTestStep("Select [Deliverables] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu] of Deliverables")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)
	int rowNo = sVal.searchTableReturnRow(5, deliverable.name)
	WebUI.verifyElementPresent(findTestObject(Const.columnPicker + 'sym_Confirmed', [('row'): rowNo]), GVars.midWait)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	

Rep.nextTestStep("Click onto [Complete]")
	gObj.selectInlineOption(6)
	
	
Rep.nextTestStep("Verify the [Status] is [Confirmed]")
	String status = gObj.getEditTextSync(Const.columnPicker + "confirmedStatus")
	gAct.compareStringAndReport(status, "Yes")

	
Rep.nextTestStep("Click onto [Save & Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAmpClose")
	gAct.Wait(2)
	

Rep.nextTestStep("Verify [Green Tick] is appearing against the above deliverable ")
	WebUI.verifyElementPresent(findTestObject(Const.columnPicker + 'sym_Completed', [('row'): rowNo]), GVars.midWait)
