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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.CostCentreCustomField
import models.GenerateCostCentreCustomField
import models.CostCentre
import models.GenerateCostCentre
import customfields.Create as createCF
import customfields.Common as ComCF

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String tableName = "63f30b25-1931-4053-98a5-eec594abfc22"
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	String ReadOnlyAmendVal = Com.generateRandomText(9)
	String ReadOnlyAmendVal2 = Com.generateRandomText(11)
	String newExVal = Com.generateRandomText(9)
	String newExVal2 = Com.generateRandomText(12)
	String newExVal3 = Com.generateRandomText(7)
	
	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String[] newCostCentreCF = [costCentreCFName, "Existing Values", "Normal", "", ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)
	createCF.costCentreCustomField_ExistingValue(tabName, costCentreCustomField)

	String costCentreName = Com.generateRandomText(10)
	String costCentreCode = costCentreName.toUpperCase()
	
	String[] newCostCentre = [costCentreName, costCentreCode, "We have some notes"]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	component.costCentreWithExistingCustomField(costCentre, tabName, ReadOnlyAmendVal)
	
	String costCentreName2 = Com.generateRandomText(10)
	String costCentreCode2 = costCentreName2.toUpperCase()
	
	String[] newCostCentre2 = [costCentreName2, costCentreCode2, "We have some notes"]
	CostCentre costCentre2 = GenerateCostCentre.createCostCentre(newCostCentre2)
	component.costCentreWithExistingCustomField(costCentre2, tabName, ReadOnlyAmendVal2)
	
	
	component.costCentreWithExistingCustomField_AddValueOnTab(costCentre2, tabName, newExVal)
	component.costCentreWithExistingCustomField_AddValueOnTab(costCentre2, tabName, newExVal2)
	
		
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an Cost centre")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, costCentre.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	
	
Rep.nextTestStep("Check existing items")
	WebUI.mouseOver(findTestObject(Const.kip4CostCentre + "dropdownSelector"))
	gObj.buttonClickSync(Const.kip4CostCentre + "dropdownSelector")
	
	gObj.elementPresentSubSync(Const.kip4CostCentre + "select_ExistingValueAlt2",  ReadOnlyAmendVal, "value")
	gObj.elementPresentSubSync(Const.kip4CostCentre + "select_ExistingValueAlt2", newExVal2, "value")
	
	WebUI.verifyElementNotPresent(findTestObject(Const.kip4CostCentre + "select_ExistingValueAlt2", [('value'): ReadOnlyAmendVal2]), GVars.midWait)
	WebUI.verifyElementNotPresent(findTestObject(Const.kip4CostCentre + "select_ExistingValueAlt2", [('value'): newExVal]), GVars.midWait)
	
	
Rep.nextTestStep("Add new item items")
	gObj.buttonClickSync(Const.kip4CostCentre + "blankCell")	
	gObj.setEditSync(Const.kip4CostCentre + "input_ExistingVal_extra", newExVal3)

	
	boolean eleVis = gObj.elementVisibleSync(Const.kip4CostCentre + "dropDown_AddItem")
	
	if(eleVis){
		gObj.buttonClickSync(Const.kip4CostCentre + "dropDown_AddItem")
	}
	

Rep.nextTestStep("Enter text in the Existing Values Textfield and Save and Close")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")	
	Act.verifySavePopUpText()

	

Rep.nextTestStep("Re-Open relevant [Cost Centre]")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNos = sVal.searchTableReturnRow(3, costCentre.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNos, "row")

	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Select dropdown to the right side the Existing Values Textfield")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	WebUI.mouseOver(findTestObject(Const.kip4CostCentre + "dropdownSelector"))
	gObj.buttonClickSync(Const.kip4CostCentre + "dropdownSelector")
	
	
Rep.nextTestStep("Ensure that the recently saved Existing Value is available for selection")
	gObj.elementPresentSubSync(Const.kip4CostCentre + "select_ExistingValueAlt2", newExVal3, "value")
	gObj.elementPresentSubSync(Const.kip4CostCentre + "select_ExistingValueAlt2", newExVal2, "value")
	
	WebUI.verifyElementNotPresent(findTestObject(Const.kip4CostCentre + "select_ExistingValueAlt2", [('value'): ReadOnlyAmendVal]), GVars.midWait)
	WebUI.verifyElementNotPresent(findTestObject(Const.kip4CostCentre + "select_ExistingValueAlt2", [('value'): ReadOnlyAmendVal2]), GVars.midWait)
	WebUI.verifyElementNotPresent(findTestObject(Const.kip4CostCentre + "select_ExistingValueAlt2", [('value'): newExVal]), GVars.midWait)

	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)
	