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
import models.DeliverableType
import models.GenerateDeliverableType

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String DeliverableTypeName = Com.generateRandomText(10)
	String DeliverableTypeCode = DeliverableTypeName.toUpperCase()
	
	String [] newDelType = [DeliverableTypeName, DeliverableTypeCode]
	DeliverableType deliverableType = GenerateDeliverableType.createDeliverableType(newDelType)
	component.createDeliverableTypeUnclicked(deliverableType)
	
	String newDeliverableTypeName = Com.generateRandomText(10)
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Deliverable Type]")
	gObj.buttonClickSync(Const.busEntMenu + "a_DeliverableTypes")
	
	
Rep.nextTestStep("Click onto [Inline menu] against a deliverable Type")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, deliverableType.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Amend [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newDeliverableTypeName)
	
		
Rep.nextTestStep("Verify the [Code] field is locked and user unable to amend the value")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')
	
	
Rep.nextTestStep("Select [Allow Attachments] checkbox")
	gObj.checkSync(Const.kip4DeliverableType + "chk_AllowAttachments")
	
	
Rep.nextTestStep("Select [Allow Attachments] checkbox")
	gObj.checkSync(Const.kip4DeliverableType + "chk_AuditCustomFields")
	
	
Rep.nextTestStep("Select [Custom Field Restriction] from dropdown list")
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_DeliverableCustomFields")
	
	gObj.buttonClickSync(Const.kip4DeliverableType + "custField_DropDown")
	gObj.setEditSync(Const.kip4DeliverableType + "deliverableCustomField", "General")
	
	gObj.buttonClickSync(Const.kip4DeliverableType + "select_deliverableCustomField")
	
	
Rep.nextTestStep("Click onto [save & close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	gObj.refreshUI()
	
		
Rep.nextTestStep("Click onto [Inline Menu] against the above used Deliverable Type")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, newDeliverableTypeName)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Verify the changes made above are retained accurately")
	String expName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(expName, newDeliverableTypeName)
	
	def allowAtt = WebD.getCheckBoxState("//input[@id='allowAttachments']")
	gAct.compareStringAndReport(allowAtt, "true")
	String newChkAtt = "no"
	
	def audit = WebD.getCheckBoxState("//input[@id='auditCustomFields']")
	gAct.compareStringAndReport(audit, "true")
	String newChkAudit = "no"
	
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_DeliverableCustomFields")
	String customVal = gObj.getEditTextSync(Const.kip4DeliverableType + "customFieldValue")
	gAct.findSubstringInStringAndReport(customVal, "General")
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_General")
	
	
Rep.nextTestStep("Click over [Audit] tab")
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_Audit")
	gObj.buttonClickSync(Const.kip4DeliverableType + "a_Today")
	
	
Rep.nextTestStep("Verify all the [Recent Changes] made above were listed")
	String auditText = gObj.getEditTextSync(Const.kip4DeliverableType + "todayAuditDetails")
	gAct.findSubstringInStringAndReport(auditText, "Name changed from " + deliverableType.name + " to " + newDeliverableTypeName)
	
	if(audit == "true"){
		newChkAudit = "Yes"
	}
	
	gAct.findSubstringInStringAndReport(auditText, "Audit Custom Fields changed from No to " + newChkAudit)
	
	if(allowAtt == "true"){
		newChkAtt = "Yes"
	}
	
	gAct.findSubstringInStringAndReport(auditText, "Allow Attachments changed from No to " + newChkAtt)
	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	