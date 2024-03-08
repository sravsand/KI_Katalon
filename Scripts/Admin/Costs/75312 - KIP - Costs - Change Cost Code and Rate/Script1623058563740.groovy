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
import models.Cost
import models.GenerateCost


//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String startDate = gAct.decreaseDate(todaysDate, pattern, 7, pattern)

	String costName = Com.generateRandomText(8)
	String costCode = costName.toUpperCase()
	
	String[] newCost = [costName, costCode, "12.00", "", "Default Currency"]
	Cost cost = GenerateCost.createCost(newCost)
	component.createCost(cost)
	
	String costNameNew = Com.generateRandomText(8)
	String costCodeNew = costNameNew.toUpperCase()
	
	String[] newCostNew = [costNameNew, costCodeNew, "12.00", "", "Default Currency"]
	Cost costNew = GenerateCost.createCost(newCostNew)
	component.createCost(costNew)
		
	String newRateEdit = "13.00"
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	

Rep.nextTestStep("Select [Adjust costs]")
	gObj.buttonClickSync(Const.financialsMenu + "a_AdjustCosts")
	

Rep.nextTestStep("Verify the header title read as [Adjust Cost]")
	Val.modalTitle("Adjust Cost Rates")
	
	
Rep.nextTestStep("Click onto [Operation] dropdown and Select [Change Cost Code & Rate]")
	gObj.selectComboByLabelSync(Const.kip4AdjcostRate + "select_Operation", "Change Cost Code and Rate")


Rep.nextTestStep("Select code from [Existing Cost Code] dropdown")
	gObj.selectComboboxValue("existingCostCode", "existingCostCode", cost.name, cost.name)


Rep.nextTestStep("Select code from [New Cost Code] dropdown")
	gObj.selectComboboxValue("newCostCode", "newCostCode", costNew.name, costNew.name)
	

Rep.nextTestStep("Verify the [New Rate] field is pre-populated with existing cost code rate")
	String actRate = gObj.getAttributeSync(Const.kip4AdjcostRate + "input_newCostRate", 'value')
	gAct.compareStringAndReport(actRate + ".00", cost.rate)


Rep.nextTestStep("Edit the [New Rate]")
	gObj.setEditSync(Const.kip4AdjcostRate + "input_newCostRate", newRateEdit)
	

Rep.nextTestStep("Select the [Between Date]")
	gObj.setEditSync(Const.kip4AdjcostRate + "input_DateFrom", startDate)

	
Rep.nextTestStep("Select the [To Date]")
	gObj.setEditSync(Const.kip4AdjcostRate + "input_DateTo", todaysDate)
	
	
Rep.nextTestStep("Deselect [Approved], [Invoiced], [Chargeable] & [Overtime] checkboxes")
	def chkApp = WebD.getCheckBoxState("//input[@id='approved']")

	if(chkApp == "true"){
		gObj.buttonClickSync(Const.kip4AdjcostRate + "chk_Approved")
	}
	
	def chkInv = WebD.getCheckBoxState("//input[@id='invoiced']")
	
	if(chkInv == "true"){
		gObj.buttonClickSync(Const.kip4AdjcostRate + "chk_Invoiced")
	}
	
	def chkChg = WebD.getCheckBoxState("//input[@id='chargeable']")
	
	if(chkChg == "true"){
		gObj.buttonClickSync(Const.kip4AdjcostRate + "chk_Chargeable")
	}
	
	def chkOvr = WebD.getCheckBoxState("//input[@id='overtime']")
	
	if(chkOvr == "true"){
		gObj.buttonClickSync(Const.kip4AdjcostRate + "chk_Overtime")
	}

	
Rep.nextTestStep("Click onto [Filter Restrictions] tab")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "tab_FilterRestrictions")
	

Rep.nextTestStep("Deselect [No Resource Filter] checkbox")
	def chkNoRes = WebD.getCheckBoxState("//input[@id='noResourceRestriction']")
	
	if(chkNoRes == "true"){
		gObj.buttonClickSync(Const.kip4AdjcostRate + "chk_NoResourceFilter")
	}
	

Rep.nextTestStep("Click onto [Add] button")
	WebD.clickElement("//button[@id='copyrestriction']")


Rep.nextTestStep("Select a Resource from the list")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "chk_selection")
	

Rep.nextTestStep("Click onto [Select] button ")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "btn_Select")
	

Rep.nextTestStep("Click onto [Project] tab within [Filter Restrictions]")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "tab_Project")


Rep.nextTestStep("Verify [No Project Filter] checkbox is pre-selected")
	String eleVis = WebUI.verifyElementNotVisible(findTestObject(Const.kip4AdjcostRate + "btn_Add"))
	
	
Rep.nextTestStep("Click onto [Adjust] button")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "btn_Adjust")
	

Rep.nextTestStep("Verify the toaster message to read as [Adjusted Successfully]")
	Act.verifyPopUpText("Costs adjusted successfully")

