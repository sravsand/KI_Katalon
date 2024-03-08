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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.ExchangeRate
import models.GenerateExchangeRate
import models.Currency
import models.GenerateCurrency
import search.Validate as sVal
import models.Charge
import models.GenerateCharge

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String chargeName = Com.generateRandomText(8)
	String chargeCode = chargeName.toUpperCase()
	
	String[] newCharge = [chargeName, chargeCode, "3.50", "Default Currency"]
	Charge charge = GenerateCharge.createCharge(newCharge)
	component.createCharge(charge)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	def weekEnd = Com.increaseWorkingCalendarWithFormat(today, 41, "dd/MM/yyyy")
	String newRate = "4.50"
	
	
Rep.nextTestStep("Click onto [Adminstartion] (top left corner)")
	WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		
		
Rep.nextTestStep("Click onto [Adjust Charge Rate] option")
	WebUI.click(findTestObject(Const.financialsMenu + "a_AdjustCharges"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Select [Existing Charge Code]")
	WebUI.setText(findTestObject(Const.adjustChargeExRate + "input_ExistingChargeCode"), charge.code)
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.adjustChargeExRate + "input_ToDate"))
	gAct.Wait(2)
	String actCode = WebUI.getAttribute(findTestObject(Const.adjustChargeExRate + "input_NewRate"), 'value')
	gAct.compareStringAndReport(actCode, charge.rate)
	
	
Rep.nextTestStep("Enter [New Rate] for the selected charge")
	WebUI.setText(findTestObject(Const.adjustChargeExRate + "input_NewRate"), newRate)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Select a [Date Between] via date picker")
	WebUI.setText(findTestObject(Const.adjustChargeExRate + "input_FromDate"), todaysDate)

	
Rep.nextTestStep("Select a [Date To] via date picker")
	WebUI.setText(findTestObject(Const.adjustChargeExRate + "input_ToDate"), weekEnd)

	
Rep.nextTestStep("Click onto [Adjust] button")
	WebUI.click(findTestObject(Const.adjustChargeExRate + "btnAdjust"))
	
		
Rep.nextTestStep("Click [Ok] button within pop up message")
	WebUI.acceptAlert()
	gAct.Wait(2)
	
	WebUI.switchToDefaultContent()
	String actCodeNew = WebUI.getAttribute(findTestObject(Const.adjustChargeExRate + "input_NewRate"), 'value')
	gAct.compareStringAndReport(actCodeNew, newRate)
	