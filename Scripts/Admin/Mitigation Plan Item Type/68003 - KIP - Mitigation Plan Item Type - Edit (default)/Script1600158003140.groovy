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
import models.MitigationPlanItemType
import models.GenerateMitigationPlanItemType

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String mitigationPlanItemTypeName = Com.generateRandomText(8)
	String mitigationPlanItemTypeCode = mitigationPlanItemTypeName.toUpperCase()
	
	String[] newMitPlanType = [mitigationPlanItemTypeName, mitigationPlanItemTypeCode]
	MitigationPlanItemType mitigationPlanItemType = GenerateMitigationPlanItemType.createMitigationPlanItemType(newMitPlanType)
	component.createMitigationPlanItemType(mitigationPlanItemType)

	String newMitigationPlanItemTypeName = Com.generateRandomText(8)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Mitigation Plan Item Type]")
	gObj.buttonClickSync(Const.busEntMenu + "a_MitigationPlanItemTypes")
	

Rep.nextTestStep("Click onto [Inline menu] against a department")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, mitigationPlanItemType.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Edit text in 'Name' field")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newMitigationPlanItemTypeName)

	
Rep.nextTestStep("Verify the [Code] field is locked and user unable to amend the value")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')
	String actCode = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actCode, mitigationPlanItemType.code)


Rep.nextTestStep("Check/uncheck 'Active' field")
	WebD.clickElement("//span[contains(text(),'Active')]")

	def active = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(active, null)
	
	WebD.clickElement("//span[contains(text(),'Active')]")

	active = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(active, "true")
	

Rep.nextTestStep("Leave Default Mitigation Plan Item Type checkbox unchecked")
	gObj.checkSync(Const.kip4MitigationPlanItemType + "chk_DefaultMitigationPlanItemType")
	def mitVal = WebD.getCheckBoxState("//input[@id='defaultRiskItemType']")
	gAct.compareStringAndReport(mitVal, "true")
	

Rep.nextTestStep("Click [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	
	
Rep.nextTestStep("Click [OK]")
	gObj.buttonClickSync(Const.kip4MitigationPlanItemType + "button_OK")
	Act.verifySavePopUpText()


Rep.nextTestStep("Verify the chagnes are updated accurately and the selected Benefit Type is marked as active on the Benefit Type table")
	int rowNum = sVal.searchTableReturnRow(3, newMitigationPlanItemTypeName)
	
	String status = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 4, "col")
	gAct.compareStringAndReport(status, "Yes")
	