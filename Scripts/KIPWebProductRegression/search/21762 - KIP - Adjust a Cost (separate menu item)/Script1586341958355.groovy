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
import models.Cost
import models.GenerateCost

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String costName = Com.generateRandomText(8)
	String costCode = costName.toUpperCase()
	
	String[] newCost = [costName, costCode, "17.00", "", "Default Currency"]
	Cost cost = GenerateCost.createCost(newCost)
	component.createCost(cost)

	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	def weekEnd = Com.increaseWorkingCalendarWithFormat(today, 31, "dd/MM/yyyy")
	String newRate = "14.50"
	
	
Rep.nextTestStep("Click onto [Adminstartion] (top left corner)")
	WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		
		
Rep.nextTestStep("Click onto [Adjust Cost Rate] option")
	WebUI.click(findTestObject(Const.financialsMenu + "a_AdjustCosts"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Select [Existing Cost Code]")
	WebUI.setText(findTestObject(Const.adjustCostExRate + "input_ExistingChargeCode"), cost.code)
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.adjustCostExRate + "input_ToDate"))
	gAct.Wait(2)
	String actCode = WebUI.getAttribute(findTestObject(Const.adjustCostExRate + "input_NewRate"), 'value')
	gAct.compareStringAndReport(actCode, cost.rate)

	
Rep.nextTestStep("Enter [New Rate] for the selected charge")
	WebUI.setText(findTestObject(Const.adjustCostExRate + "input_NewRate"), newRate)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Select a [Date Between] via date picker")
	WebUI.setText(findTestObject(Const.adjustCostExRate + "input_FromDate"), todaysDate)

	
Rep.nextTestStep("Select a [Date To] via date picker")
	WebUI.setText(findTestObject(Const.adjustCostExRate + "input_ToDate"), weekEnd)

	
Rep.nextTestStep("Click onto [Adjust] button")
	WebUI.click(findTestObject(Const.adjustCostExRate + "btnAdjust"))
	
		
Rep.nextTestStep("Click [Ok] button within pop up message")
	WebUI.acceptAlert()
	gAct.Wait(1)
	
	WebUI.switchToDefaultContent()
	String actCostNew = WebUI.getAttribute(findTestObject(Const.adjustCostExRate + "input_NewRate"), 'value')
	gAct.compareStringAndReport(actCostNew, newRate)