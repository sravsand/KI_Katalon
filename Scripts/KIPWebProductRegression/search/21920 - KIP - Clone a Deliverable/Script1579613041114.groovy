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
	
	String[] newDeliverable = [DeliverableName, GVars.user, "Project Innovation_" + GVars.selectedBrowser, "BILLING", endDate]
	Deliverable deliverable = GenerateDeliverable.createDeliverable(newDeliverable)
	component.createDeliverable(deliverable)
	
	String clonedDeliverableName = Com.generateRandomText(8)
	String clonedEndDate = Com.increaseWorkingCalendarWithFormat(today, 8, pattern)
	String[] newClonedDeliverable = [clonedDeliverableName, GVars.user, "Project Innovation_" + GVars.selectedBrowser, "BILLING", endDate]
	Deliverable clonedDeliverable = GenerateDeliverable.createDeliverable(newClonedDeliverable)
	

Rep.nextTestStep("Select [Deliverables] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Deliverables")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(2)
	int rowNo = sVal.searchTableReturnRow(5, deliverable.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneProject")
	

Rep.nextTestStep("Amend text in [name] field")
	gObj.setEditSync(Const.columnPicker + 'input_DelName', clonedDeliverable.name)
	

Rep.nextTestStep("Amend [Start Date]")
	gObj.setEditSync(Const.columnPicker + "input_DeliveryDate", clonedDeliverable.deliveryDate)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAmpClose")
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(5, clonedDeliverable.name)

	