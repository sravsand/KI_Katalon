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
	Val.modalTitle("Adjust Charge Rates")
	
	
Rep.nextTestStep("Click onto [Existing Charge Code] and Select a [Charge Code] from the dropdown list")
	gObj.selectComboboxValue("existingChargeCode", "existingChargeCode", charge.name, charge.name)
	
	
Rep.nextTestStep("Verify the [New Rate] field is pre-populated with existing Charge code rate")
	String actRate = gObj.getAttributeSync(Const.kip4AdjchgRate + "input_newChargeRate", 'value')
	gAct.compareStringAndReport(actRate, charge.rate)


Rep.nextTestStep("Edit the [New Rate]")
	gObj.setEditSync(Const.kip4AdjchgRate + "input_newChargeRate", newRate)


Rep.nextTestStep("Amend the [Between Date]")
	gObj.setEditSync(Const.kip4AdjchgRate + "input_startDate", startDate)


Rep.nextTestStep("Amend the [To Date]")
	gObj.setEditSync(Const.kip4AdjchgRate + "input_endDate", todaysDate)
	

Rep.nextTestStep("Deselect [Disapproved], [Non Invoiced], [Non Chargeable] & [Non Overtime] checkboxes")

	def chkDis = WebD.getCheckBoxState("//input[@id='disapproved']")

	if(chkDis == "true"){
		gObj.buttonClickSync(Const.kip4AdjchgRate + "chk_Disapproved")
	}
	
	
	def chkNonInv = WebD.getCheckBoxState("//input[@id='nonInvoiced']")
	
	if(chkNonInv == "true"){
		gObj.buttonClickSync(Const.kip4AdjchgRate + "chk_NonInvoiced")
	}
	
	def chkNonchg = WebD.getCheckBoxState("//input[@id='nonChargeable']")
	
	if(chkNonchg == "true"){
		gObj.buttonClickSync(Const.kip4AdjchgRate + "chk_NonChargeable")
	}
	
	def chkNonOvr = WebD.getCheckBoxState("//input[@id='nonOvertime']")
	
	if(chkNonOvr == "true"){
		gObj.buttonClickSync(Const.kip4AdjchgRate + "chk_NonOvertime")
	}

Rep.nextTestStep("Click onto [Filter Restrictions] tab")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "tab_FilterRestrictions")

	
Rep.nextTestStep("Verify [No Resource Filter] checkbox is pre-selected")
	WebUI.verifyElementNotVisible(findTestObject(Const.kip4AdjchgRate + "btn_Add"))

	
Rep.nextTestStep("Click onto [Project] tab within [Filter Restrictions]")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "tab_Project")
	

Rep.nextTestStep("Deselect [No Project Filter] checkbox")
	def chkNoProR = WebD.getCheckBoxState("//input[@id='noProjectRestriction']")
	
	if(chkNoProR == "true"){
		gObj.buttonClickSync(Const.kip4AdjchgRate + "chk_NoProjectFilter")
	}
	

Rep.nextTestStep("Click onto [Add] button")
	WebD.clickElement("//button[@id='copyrestriction']")
	

Rep.nextTestStep("Select a Project from the list")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "chk_FirstProject")
	

Rep.nextTestStep("Click onto [Select] button ")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "btn_Select")
	

Rep.nextTestStep("Click onto [Adjust] button")
	gObj.buttonClickSync(Const.kip4AdjchgRate + "btn_Adjust")
	

Rep.nextTestStep("Verify the toaster message to read as [Adjusted Successfully]")
	Act.verifyPopUpText("Charges adjusted successfully")
	
