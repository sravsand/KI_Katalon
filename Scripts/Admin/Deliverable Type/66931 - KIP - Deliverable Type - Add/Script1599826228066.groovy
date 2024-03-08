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

def pattern = "dd/MM/yyyy"
Date today = new Date()
String todaysDate = today.format(pattern)

	
Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String DeliverableTypeName = Com.generateRandomText(10)
	String DeliverableTypeCode = DeliverableTypeName.toUpperCase()
	
	String [] newDelType = [DeliverableTypeName, DeliverableTypeCode]
	DeliverableType deliverableType = GenerateDeliverableType.createDeliverableType(newDelType)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Deliverable Type]")
	gObj.buttonClickSync(Const.busEntMenu + "a_DeliverableTypes")

	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	

Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", deliverableType.name)
	
	
Rep.nextTestStep("Add a [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", deliverableType.code)

	
	def activeRem = WebD.getCheckBoxState("//input[@id='active']")
	
	if(activeRem == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
	
	def allowAtt = WebD.getCheckBoxState("//input[@id='allowAttachments']")
	
	if(allowAtt == null){
		gObj.checkSync(Const.kip4DeliverableType + "chk_AllowAttachments")
	}
	
	
	def audit = WebD.getCheckBoxState("//input[@id='auditCustomFields']")
	
	if(allowAtt == null){
		gObj.checkSync(Const.kip4DeliverableType + "chk_AuditCustomFields")
	}
	
	
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_DeliverableCustomFields")
	
	gObj.buttonClickSync(Const.kip4DeliverableType + "deliverableCustomField")
	gObj.setEditSync(Const.kip4DeliverableType + "deliverableCustomField", "General")
	
	gObj.buttonClickSync(Const.kip4DeliverableType + "select_deliverableCustomField")
	gObj.buttonClickSync(Const.kip4DeliverableType + "tab_General")
	
	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
	
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, deliverableType.code)
	
	
Rep.info("Steps to test - 65811")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
Rep.nextTestStep("Verify the Time stamp is visible at the bottom of the modal & is displaying username & Time accurately")
	int rowNo = sVal.searchTableReturnRow(3, deliverableType.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Clone]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify there is no [Time Stamp] on the footer of the [Add Deliverable] modal")
	String infoValue = gObj.getEditTextSync(Const.kip4DeliverableType + "edit_LastEditInfo")
	
	gAct.findSubstringInStringAndReport(infoValue, GVars.user + " " + todaysDate)

	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

Rep.info("~~~~~~~~~~~~~~~~~~~~~")