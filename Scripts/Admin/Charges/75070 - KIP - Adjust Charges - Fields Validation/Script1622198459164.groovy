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
import models.Charge
import models.GenerateCharge

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String startDate = gAct.decreaseDate(todaysDate, pattern, 7, pattern)
	
	String chargeName = Com.generateRandomText(8)
	String chargeCode = chargeName.toUpperCase()
	
	String rate = "7.5"
	String newRate = "6.5"
	
	String[] newCharge = [chargeName, chargeCode, rate, "Default Currency"]
	Charge charge = GenerateCharge.createCharge(newCharge)
	component.createCharge(charge)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	

Rep.nextTestStep("Select [Adjust charges]")
	gObj.buttonClickSync(Const.financialsMenu + "a_AdjustCharges")
	
	
Rep.nextTestStep("Verify [Change Charge Rate] is pre-selected in [Operation] field")
	gObj.verifyLabelSelectedSync(Const.kip4AdjchgRate + "select_Operation", "Change Charge Rate")
	
	
Rep.nextTestStep("Verify the header title read as [Adjust Charge]")
	gObj.elementVisibleSync(Const.kip4AdjchgRate + "header_AdjustChargeRates")
	
	
Rep.nextTestStep("Click onto [Existing Charge Code]")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "dropDown_ExistingChargeCode")
	
	
Rep.nextTestStep("Click out without selecting anything from the dropdown")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "div_Operation")


Rep.nextTestStep("Verify the error message to read as [The field is required]")
	gObj.elementVisibleSync(Const.kip4AdjchgRate + "error_fieldRequired")


Rep.nextTestStep("Click into [New Rate] field")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "input_newChargeRate")


Rep.nextTestStep("Click out without entering any value")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "div_Operation")


Rep.nextTestStep("Verify the error message to read as [The field is required]")
	gObj.elementVisibleSync(Const.kip4AdjchgRate + "error_invalidRate")


Rep.nextTestStep("Enter any [Letter] in [New Rate] field")
	gObj.setEditSync(Const.kip4AdjchgRate + "input_newChargeRate", "aaa")


Rep.nextTestStep("Verify the error message to read as ['xxx' is invalid]")
	gObj.elementVisibleSync(Const.kip4AdjchgRate + "error_invalidRate")


Rep.nextTestStep("Click into [Change Timesheet Between] field ")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "input_startDate")


Rep.nextTestStep("Click out without selecting any date")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "div_Operation")


Rep.nextTestStep("Verify the error message to read as [The field is required]")
	gObj.elementVisibleSync(Const.kip4AdjchgRate + "error_StartDate")


Rep.nextTestStep("Verify the [Change Timesheet To] field")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "input_endDate")


Rep.nextTestStep("Click out without selecting any date")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "div_Operation")


Rep.nextTestStep("Verify the error message to read as [The field is required]")
	gObj.elementVisibleSync(Const.kip4AdjchgRate + "error_EndDate")

	
Rep.nextTestStep("Enter a New rate")
	gObj.setEditSync(Const.kip4AdjchgRate + "input_newChargeRate", "8.5")
	

Rep.nextTestStep("Click onto [X] or [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	

Rep.nextTestStep("Click onto [Adjust] button")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "btn_AdjustModal")


Rep.nextTestStep("Verify Error message displaying correct error highlighting all fields accurately")
	String errValu = gObj.getEditTextSync(Const.kip4AdjchgRate + "adjChrgeErr")
	
	gAct.compareStringAndReport(errValu, "×\nPlease correct the following errors\nField is required: To\nField is required: Change Timesheets Between\nField is required: Existing Charge Code")
//	gAct.compareStringAndReport(errValu, "×\nPlease correct the following errors\n(General) Field is required : To\n(General) Field is required : Change Timesheets Between\n(General) Field is required : Existing Charge Code")
	
	