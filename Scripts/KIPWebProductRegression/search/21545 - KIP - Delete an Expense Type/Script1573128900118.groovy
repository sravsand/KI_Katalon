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
import buildingBlocks.createComponentsLogedIn as component
import models.ExpenseType
import models.GenerateExpenseType

//change from default login
GVars.configFileName = 	"katalonConfigAuto5.xml"
Act.changeUserCredentials()

String searchItem = "Expense Types"
String searchVal = "Expense Type"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String expenseName = Com.generateRandomText(10)
	String expenseCode = expenseName.toUpperCase()
	
	String[] newExpenseType = [expenseName, expenseCode, "", "1", "false"]
	ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)
	
	component.createExpenseType(expenseType)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")


Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))


Rep.nextTestStep("Select [Expense Type] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)
	
	String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gAct.Wait(1)
	WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)
	
	
Rep.nextTestStep("Click onto [Inline menu]of an expense type")
	int RowNumber = sVal.searchTableReturnRow(2, expenseType.code)
	WebUI.click(findTestObject(Const.columnPicker + "inlineEdit", [('row'): RowNumber]))
	
	
Rep.nextTestStep("Click onto [Delete]")
	WebUI.click(findTestObject(Const.columnPicker + "a_Delete"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click OK")
	WebUI.click(findTestObject(Const.columnPicker + "button_OK"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify that the deleted expense type is not available for selection")
	sVal.searchTableDeletedValue(2, expenseType.code)
	