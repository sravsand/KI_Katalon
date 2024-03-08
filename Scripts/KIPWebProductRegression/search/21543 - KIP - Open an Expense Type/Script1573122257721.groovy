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
import models.ExpenseType
import models.GenerateExpenseType

String searchItem = "Expense Types"
String searchVal = "Expense Type"

//change from default login
GVars.configFileName = 	"katalonConfigAuto3.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String expenseName = Com.generateRandomText(10)
	String expenseCode = expenseName.toUpperCase()
	
	String[] newExpenseType = [expenseName, expenseCode, "", "1", "true", ""]
	ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)
	
	component.createExpenseType(expenseType)


Rep.nextTestStep("Click On [Search] from the side bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Expense Type] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(3)

	WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)
	int RowNumber = sVal.searchTableReturnRow(2, expenseType.code)
	String AllowReceiptStatus = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): RowNumber, ('col'): 6]))
	gAct.compareStringAndReport(AllowReceiptStatus, "Yes")
	
	
Rep.nextTestStep("Click onto [Inline menu]of an expense type")
	WebUI.click(findTestObject(Const.columnPicker + "inlineEdit", [('row'): RowNumber]))
	
	
Rep.nextTestStep("Click onto [Open]")
	WebUI.click(findTestObject(Const.columnPicker + "a_InlineMenuPositionSelection", [('pos'): 1]))
	gAct.Wait(2)
	
	
Rep.nextTestStep("Untick [Allow Receipt]")
	WebUI.uncheck(findTestObject(Const.columnPicker + "chk_AllowReceipt"))


Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
	gAct.Wait(3)
	
	
Rep.nextTestStep("Verify that the changes have been updated in expense type table")
	String AllowReceiptStatus_new = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): RowNumber, ('col'): 6]))
	gAct.compareStringAndReport(AllowReceiptStatus_new, "No")
	