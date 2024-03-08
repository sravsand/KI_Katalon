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

import org.junit.After
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
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
import buildingBlocks.createComponents
import models.Cost
import models.GenerateCost

String costName = Com.generateRandomText(8)
String costCode = costName.toUpperCase()

String[] newCost = [costName, costCode, "15.00", "", "Default Currency"]
Cost cost = GenerateCost.createCost(newCost)

String searchItem = "Costs"
String searchVal = "Cost"


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Cost] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)
	
	String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click On [+ Add] ")
	WebUI.click(findTestObject(Const.columnPicker + "a_Add"))


Rep.nextTestStep("Add text in [Name] field")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), cost.name)
			
			
Rep.nextTestStep("Add [Code]")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), cost.code)
			
			
Rep.nextTestStep("Select [Active] through checkbox")
	boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
	if(!active){
		WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
	}
		
	
Rep.nextTestStep("Select [Currency] from [Currency] dropdown")
	WebUI.clearText(findTestObject(Const.columnPicker + "input_Currency"))
	WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), cost.currency)
	
	
Rep.nextTestStep("Add text in [rate] field")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), cost.rate)
		
		
Rep.nextTestStep("Select [Standard] from [Applies to] section through checkbox")	
	WebUI.check(findTestObject(Const.columnPicker + "chk_Standard"))


Rep.nextTestStep("Select [Overtime] from [Applies to] section through checkbox")
	WebUI.check(findTestObject(Const.columnPicker + "chk_Overtime"))

	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		
			
Rep.nextTestStep("Verify the newly added Expense Type is added onto the table")
	sVal.searchTableAddedValue(2, cost.code)
	