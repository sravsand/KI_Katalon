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
import models.BillingPriceList
import models.GenerateBillingPriceList

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String billingPriceListName = Com.generateRandomText(10)
	String billingPriceListCode = billingPriceListName.toUpperCase()
	
	String[] newBillingPriceList = [billingPriceListName, billingPriceListCode, "DEFAULT", "1.5", "Some random notes"]
	BillingPriceList billingPriceList = GenerateBillingPriceList.createBillingPriceList(newBillingPriceList)


Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [billing price list]")
	gObj.buttonClickSync(Const.financialsMenu + "a_BillingPriceList")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", billingPriceList.name)
	
	
Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", billingPriceList.code)


Rep.nextTestStep("Select [Active] checkbox")
	def activeRem = WebD.getCheckBoxState("//input[@id='active']")

	if(activeRem == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}


Rep.nextTestStep("Select [Currency]")
	if(billingPriceList.currency != "DEFAULT"){
		gObj.buttonClickSync(Const.kip4BillingPriceList + "select_currencyControl")
		gObj.setEditSync(Const.kip4BillingPriceList + 'input_currency', billingPriceList.name)
		gObj.buttonClickSubSync(Const.kip4BillingPriceList + "select_Currency", " " + billingPriceList.name + " ", "curr")
	}

	
Rep.nextTestStep("Select [Unit Price]")
	gObj.setEditSync(Const.kip4BillingPriceList + "input_UnitPrice", billingPriceList.unitPrice)

	
Rep.nextTestStep("Add a [Note]")
	gObj.setEditSync(Const.kip4BillingPriceList + "textarea__notes", billingPriceList.notes)


Rep.nextTestStep("Click onto [close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Click onto [Save] button")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Save")
	Act.verifySavePopUpText()
		
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	sVal.searchTableAddedValue(2, billingPriceList.code)
	
	int rowNo = sVal.searchTableReturnRow(3, billingPriceList.name)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 5, "col")
	gAct.compareStringAndReport(ActiveStatus, "Yes")
	
	
Rep.nextTestStep("Click onto [Inline] menu against the above created billing price")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify the modal header is displaying [Name & Code]")
	String headerText = gObj.getEditTextSync(Const.kip4BillingPriceList + "Edit_header")
	
	gAct.compareStringAndReport(headerText, billingPriceList.code + " | " + billingPriceList.name)
	
	String expName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(expName, billingPriceList.name)
	String expCode = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(expCode, billingPriceList.code)
	String expCurr = gObj.getEditTextSync(Const.kip4BillingPriceList + "select_currencyControl")
	gAct.findSubstringInStringAndReport(expCurr, "Default")
	
	String expUnit = gObj.getAttributeSync(Const.kip4BillingPriceList + "input_UnitPrice", 'value')
	gAct.compareStringAndReport(expUnit, billingPriceList.unitPrice)
	String expNotes = gObj.getAttributeSync(Const.kip4BillingPriceList + "textarea__notes", 'value')
	gAct.compareStringAndReport(expNotes, billingPriceList.notes)
	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	