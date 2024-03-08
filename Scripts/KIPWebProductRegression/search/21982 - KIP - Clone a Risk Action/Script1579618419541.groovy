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
import models.Risk
import models.GenerateRisk
import models.RiskAction
import models.GenerateRiskAction

String searchItem = "Mitigation Plan Items"
String searchVal = "Mitigation Plan Items"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	String riskTitle = Com.generateRandomText(10)
	String riskActionDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String newRiskType = "Issue Resolution"
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String[] newRisk = [riskTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, todaysDate, "Project", "High", "Medium", "High", endDate, "", ""]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	component.createRisk(risk)
	
	String[] newRiskAction = ["Project Innovation_" + GVars.selectedBrowser, risk.code + " | " + riskTitle, "Mitigation", GVars.user, "Open", todaysDate, "1", riskActionDescription, "Medium"]
	RiskAction riskAction = GenerateRiskAction.createNewRiskAction(newRiskAction)
	component.createRiskAction(riskAction)
	
	String clonedRiskActionDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String[] newClonedRiskAction = ["Project Innovation_" + GVars.selectedBrowser, risk.code + " | " + riskTitle, "Issue Resolution", GVars.user, "Open", todaysDate, "1", clonedRiskActionDescription, "Medium"]
	RiskAction clonedRiskAction = GenerateRiskAction.createNewRiskAction(newClonedRiskAction)

	
Rep.nextTestStep("Select [Mitigation Plan Items] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Mitigation Plan Items")
	int rowNo = sVal.searchTableReturnRow(3, risk.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.buttonClickSync(Const.columnPicker + "a_CloneProject")
	
		
Rep.nextTestStep("Click on [Risk] dropdown and Select the associated [Risk] from the dropdown list")
	gObj.selectComboByLabelSync(Const.columnPicker + 'select_Risk', clonedRiskAction.risk)
	
	
Rep.nextTestStep("Click on [Search] and select a [Type]")
	gObj.clearSetEditSync(Const.columnPicker + 'input_RiskActionType', clonedRiskAction.type)
	gObj.buttonClickSync(Const.columnPicker + "td_General")
	

Rep.nextTestStep("Click on [Search] button and select an [Owner] if applicable (this should already be pre-populated with the user's resource)")
	String ownerText = gObj.getAttributeSync(Const.risks + 'input_Owner', 'value')
	if(ownerText == ""){
		gObj.setEditSync(Const.risks + 'input_Owner', clonedRiskAction.owner)
		gObj.buttonClickSync(Const.risks + 'input_Title')
	}
	

Rep.nextTestStep("Select a [Date]")
	gObj.setEditSync(Const.columnPicker + 'input_DueDate', clonedRiskAction.dateDue)
	
	
Rep.nextTestStep("Select a [Priority]")
	gObj.selectComboByLabelSync(Const.columnPicker + 'select_Priority', clonedRiskAction.priority)
	gObj.buttonClickSync(Const.columnPicker + "td_General")
	
	
Rep.nextTestStep("Add some text in the [Description]")
	gObj.setEditSync(Const.columnPicker + "action_Description", clonedRiskAction.description)
	
		
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(6, clonedRiskAction.description)
