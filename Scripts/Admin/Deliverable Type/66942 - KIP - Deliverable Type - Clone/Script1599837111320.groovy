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
	component.createDeliverableType(deliverableType)
	
	String clonedDeliverableTypeName = Com.generateRandomText(10)
	String clonedDeliverableTypeCode = clonedDeliverableTypeName.toUpperCase()
	
	String [] newClonedDelType = [clonedDeliverableTypeName, clonedDeliverableTypeCode]
	DeliverableType clonedDeliverableType = GenerateDeliverableType.createDeliverableType(newClonedDelType)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Deliverable Type]")
	gObj.buttonClickSync(Const.busEntMenu + "a_DeliverableTypes")

	
Rep.nextTestStep("Click onto [Inline menu] against a deliverable Type")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, deliverableType.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Verify the [Name] is pre-populated")
	String actName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actName, deliverableType.name)
	gObj.setEditSync(Const.kip4Generic + "input_Name", clonedDeliverableType.name)
	
	
Rep.nextTestStep("Verify User is able to add [Code] (Code not auto generated)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", clonedDeliverableType.code)
	
	
Rep.nextTestStep("Verify the [Checkboxes] are pre-selected as per the cloned deliverable type")
	def activeRem = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(activeRem, "true")

	def allowAtt = WebD.getCheckBoxState("//input[@id='allowAttachments']")
	gAct.compareStringAndReport(allowAtt, "true")
	
	def audit = WebD.getCheckBoxState("//input[@id='auditCustomFields']")
	gAct.compareStringAndReport(audit, "true")

	
Rep.nextTestStep("Verify the [Custom Field Restriction] is populated with same selection as of cloned Deliverable type")
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_DeliverableCustomFields")
	String customVal = gObj.getEditTextSync(Const.kip4DeliverableType + "customFieldValue")
	gAct.findSubstringInStringAndReport(customVal, "General")
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_General")

			
Rep.nextTestStep("Verify there is no [Time Stamp] on the footer of the [Add Deliverable] modal")
	WebUI.verifyElementNotHasAttribute(findTestObject(Const.kip4DeliverableType + "edit_LastEditInfo"), 'text', GVars.midWait)
	
	
Rep.nextTestStep("Save Cloned Deliverable Type")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, clonedDeliverableType.code)
	
