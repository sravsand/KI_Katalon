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
import buildingBlocks.createComponentsLogedIn as component
import models.DeliverableType
import models.GenerateDeliverableType

String searchItem = "Deliverable Types"
String searchVal = "Deliverables Types"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String DeliverableTypeName = Com.generateRandomText(10)
	String DeliverableTypeCode = DeliverableTypeName.toUpperCase()
	
	String [] newDelType = [DeliverableTypeName, DeliverableTypeCode]
	DeliverableType deliverableType = GenerateDeliverableType.createDeliverableType(newDelType)
	component.createDeliverableType(deliverableType)
	
	String clonedDeliverableTypeName = Com.generateRandomText(10)
	String clonedDeliverableTypeCode = clonedDeliverableTypeName.toUpperCase()
	
	String [] newClonedDelType = [clonedDeliverableTypeName, clonedDeliverableTypeCode]
	DeliverableType clonedDeliverableType = GenerateDeliverableType.createDeliverableType(newClonedDelType)
	

Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Deliverables] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)


Rep.nextTestStep("Click onto [Inline menu] of a Deliverables")
	WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
	WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
	int rowNo = sVal.searchTableReturnRow(3, deliverableType.name)
	WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [clone]")
	WebUI.click(findTestObject(Const.columnPicker + "a_CloneItem"))
	gAct.Wait(1)
	

Rep.nextTestStep("Amend text in [Name] field")
	WebUI.setText(findTestObject(Const.columnPicker + "input_DelTypeName"), clonedDeliverableType.name)

	
Rep.nextTestStep("Add text in [Code] field")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), clonedDeliverableType.code)
		
	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, clonedDeliverableType.name)

