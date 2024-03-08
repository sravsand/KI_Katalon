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
	
	String[] newCost = [costName, costCode, "9.00", "", "Default Currency"]
	Cost cost = GenerateCost.createCost(newCost)
	component.createCost(cost)
	
	String newRateEdit = "7.00"
	
/*	
	String costNameNew = Com.generateRandomText(8)
	String costCodeNew = costNameNew.toUpperCase()
	
	String[] newCostNew = [costNameNew, costCodeNew, "12.00", "", "Default Currency"]
	Cost costNew = GenerateCost.createCost(newCostNew)
	component.createCost(costNew)
*/	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	

Rep.nextTestStep("Select [Adjust costs]")
	gObj.buttonClickSync(Const.financialsMenu + "a_AdjustCosts")
	
	
Rep.nextTestStep("Verify [Change Cost Rate] is pre-selected in [Operation] field")
	String rateVal = gObj.getEditTextSync(Const.kip4AdjcostRate + "select_Operation")

	
Rep.nextTestStep("Verify the header title read as [Adjust Cost]")
	Val.modalTitle("Adjust Cost Rates")
	

Rep.nextTestStep("Click onto [Existing Cost Code] dropdown")
	gObj.selectComboboxValue("existingCostCode", "existingCostCode", cost.name, cost.name)
	
	
Rep.nextTestStep("Verify the [New Rate] field is pre-populated with existing cost code rate")
	String actRate = gObj.getAttributeSync(Const.kip4AdjcostRate + "input_newCostRate", 'value')
	gAct.compareStringAndReport(actRate + ".00", cost.rate)
	
	
Rep.nextTestStep("Edit the [New Rate]")
	gObj.setEditSync(Const.kip4AdjcostRate + "input_newCostRate", newRateEdit)
	
	
Rep.nextTestStep("Verify the [Change Timesheet Between] field is pre-popluated with today's date")
	gObj.setEditSync(Const.kip4AdjcostRate + "input_DateFrom", todaysDate)

	
Rep.nextTestStep("Verify the [Change Timesheet To] field is pre-popluated with today's date")
	gObj.setEditSync(Const.kip4AdjcostRate + "input_DateTo", todaysDate)

	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "btn_Close")
	

Rep.nextTestStep("Verify the unsaved message to read as [Any Adjustments will not be processed, do you want to continue]")
	String modalMes = gObj.elementVisibleSync(Const.kip4AdjcostRate + "modal_Message")
	

Rep.nextTestStep("Verify the [Buttons] on the unsaved modal read as [Continue without Adjusting] & [Adjust]")
	gObj.elementVisibleSync(Const.kip4AdjcostRate + "btn_ContinueWithoutAdjusting")
	gObj.elementVisibleSync(Const.kip4AdjcostRate + "btn_AdjustModal")

	
Rep.nextTestStep("Click onto [Continue without Adjust]")
	gObj.buttonClickSync(Const.kip4AdjcostRate + "btn_ContinueWithoutAdjusting")
	boolean elementVisible = WebUI.verifyElementNotPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)

	
	
