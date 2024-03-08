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
	
	String[] newBillingPriceList = [billingPriceListName, billingPriceListCode, "DEFAULT", "1.50", ""]
	BillingPriceList billingPriceList = GenerateBillingPriceList.createBillingPriceList(newBillingPriceList)
	component.createBillingPriceList(billingPriceList)
	
	String newBillingPriceListName_temp = Com.generateRandomText(10)
	String newBillingPriceListName = Com.generateRandomText(10)
	

Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [billing price list]")
	gObj.buttonClickSync(Const.financialsMenu + "a_BillingPriceList")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an billing price list")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, billingPriceList.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [Edit]")
	gObj.selectInlineOption(1)

	
Rep.info("Steps to test - ")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
		
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gObj.selectInlineOption(1)
		
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
			
Rep.info("Steps to test - ")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
	gObj.setEditSync(Const.kip4Generic + "input_Name", newBillingPriceListName_temp)	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
	
Rep.nextTestStep("Edit a [name] ")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newBillingPriceListName)
	
	
Rep.nextTestStep("Click [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	sVal.searchTableAddedValue(3, newBillingPriceListName)
	