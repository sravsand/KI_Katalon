import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.katalon.plugin.keyword.waitforangular.WaitForAngularKeywords
import com.katalon.plugin.keyword.angularjs.DropdownKeywords

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
import models.Charge
import models.GenerateCharge

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
			
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€", "true"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
		
	String chargeName = Com.generateRandomText(8)
	String chargeCode = chargeName.toUpperCase()
	
	String[] newCharge = [chargeName, chargeCode, "5.00", "Default Currency"]
	Charge charge = GenerateCharge.createCharge(newCharge)
	component.createCharge(charge)
	
	String edtChargeName = Com.generateRandomText(8)
	String edtChargeCode = edtChargeName.toUpperCase()
	String[] edtCharge = [edtChargeName, edtChargeCode, "9.00", currency.name]
	Charge newEdtCharge = GenerateCharge.createCharge(edtCharge)
	
		
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")


Rep.nextTestStep("Click over [Charge]")
	gObj.buttonClickSync(Const.financialsMenu + "a_Charges")


Rep.nextTestStep("Click onto [Inline menu] against an Charge")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, charge.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	

Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", newEdtCharge.code)
	
	
Rep.nextTestStep("Amend a [name] ")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newEdtCharge.name)
	
	
Rep.nextTestStep("Amend [Currency]")
	gObj.selectComboboxValue("currency", "currency", newEdtCharge.currency, newEdtCharge.currency)	
	
	
Rep.nextTestStep("Amend a [rate] ")
	gObj.setEditSync(Const.kip4Cost + "input_rate", newEdtCharge.rate)

		
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
		
Rep.nextTestStep("Verify the changes are updated accurately on the cost table")
	sVal.searchTableAddedValue(2, newEdtCharge.code)
	