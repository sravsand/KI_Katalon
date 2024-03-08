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
import models.Expense
import models.GenerateExpense
import models.Category
import models.GenerateCategory
import buildingBlocks.createComponentsLogedIn_AdminNew as component

String searchItem = "Expenses"
String searchVal = "Expenses"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	
	component.createCategory(category)
	
	String newDate = Com.todayDate()
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", category.name, true, "16.00", "", "16.00", false, "Some random notes"]
	Expense expense = GenerateExpense.createExpense(newExpense)
	

Rep.nextTestStep("Select [Expenses] from search filter")
	Nav.selectSearchFilter(searchItem)
		
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	
	
Rep.nextTestStep("Click on [Save] or [Save & Close] button")
	WebD.clickElement("//button[contains(text(),'Save & Close')]")
	String errorMsg = gObj.getEditTextSync(Const.columnPicker + "addExpenseError")
	gAct.compareStringAndReport(errorMsg, "Unable to Save Expense")
	
	
Rep.nextTestStep("Click on [Search] button and select a [Resource] ")
	gObj.clearAndSetText(Const.expense + 'input_Resource', expense.resource)
	gObj.buttonClickSync(Const.expense + 'ExpenseError')
	
		
Rep.nextTestStep("Verify todays date is populated in [Date] field ")
	gObj.buttonClickSync(Const.expense + 'input_Date')
	gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
		
	
Rep.nextTestStep("select a project from [Project] field ")
	String projectText = gObj.getAttributeSync(Const.expense + 'input_Project', 'value')
		if(projectText == ""){
		gObj.setEditSync(Const.expense + 'input_Project', expense.project)
			
		}
		
		gAct.Wait(GVars.shortWait)
	
		
Rep.nextTestStep("Select a option from [Expense type] field ")
	gObj.selectComboByValueSync(Const.expense + 'select_Expense', expense.expenseType)
	
		
Rep.nextTestStep("Select a option from [category] field ")
	gObj.selectComboByValueSync(Const.expense + 'select_Category', category.code)
		
		
Rep.nextTestStep("Tick the [Reimbursable] check box ")
	gObj.checkSync(Const.expense + 'chk_Reimbursable')
	
	
Rep.nextTestStep("Enter amount in [Gross] field and click away ")
	gObj.setEditSync(Const.expense + 'input_Gross', expense.gross)
	gObj.buttonClickSync(Const.expense + 'input_Vat')
	
	
Rep.nextTestStep("Verify the amount entered in [Gross] field is also populated in [Net] field ")
	gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', expense.net)
	
	
Rep.nextTestStep("Tick the check box for [Chargeable]")
	gObj.checkSync(Const.expense + 'chk_Chargeable')
	
	
Rep.nextTestStep("Click on [Save] or [Save & Close] button ")
	gObj.buttonClickSync(Const.expense + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)
	WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
	

Rep.nextTestStep("Verify recently added transaction in Expenses table")
	gObj.buttonClickSync(Const.columnPicker + "a_Unclaimed Expenses")
	sVal.searchTableAddedValue(10, category.name)
	