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
import models.Currency
import models.GenerateCurrency

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	String newCurrencyName = Com.generateRandomText(8)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.financialsMenu + "a_Currencies")
	
		
Rep.info("Steps to test - 60347")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Inline menu] against a currency")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, currency.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Amend the [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newCurrencyName)

	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Accept alert and check location not created")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	gObj.refreshUI()
	
	sVal.searchTableDeletedValue(3, newCurrencyName)
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
	
Rep.nextTestStep("Click onto [Inline menu] against a currency")
	gObj.refreshUI()
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, currency.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify the [Name] of the currency is appearing at the top of the modal")
	String currentName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", "value")
	gAct.compareStringAndReport(currentName, currency.name)
	
	
Rep.nextTestStep("Amend the [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newCurrencyName)


Rep.info("Steps to test - 60342")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Select [Active] checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")
	
	if(active == "true"){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	
	Act.verifySavePopUpText()
	
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")


Rep.nextTestStep("Verify the changes are updated accurately on the Currency table")
	sVal.searchTableAddedValue(3, newCurrencyName)

	
Rep.info("Steps to test - 60342")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Verify the changes are updated accurately and the selected location is not marked as active on the location table")

	int rowNumb = sVal.searchTableReturnRow(3, newCurrencyName)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNumb, "row", 6, "col")
	gAct.compareStringAndReport(ActiveStatus, "No")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")