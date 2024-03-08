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
import models.Expense
import models.GenerateExpense
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Category
import models.GenerateCategory
import search.Validate as sVal 


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	component.createCategory(category)
		
	String newDate = Com.todayDate()
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", category.code, true, "16.00", "", "16.00", false, "Some random notes"]
	Expense expense = GenerateExpense.createExpense(newExpense)
	
	
Rep.nextTestStep("Hover over the mouse to the [+] sign from the side bar menu and select Expenses")
	WebUI.mouseOver(findTestObject(Const.plusMenu + 'plusSideMenu'))
	gObj.buttonClickSync(Const.plusMenu + 'menu_Expense')
	
	
Rep.nextTestStep("Click on [Save] or [Save & close] button ")
	gObj.buttonClickSync(Const.expense + 'button_Save')
	String error = WebUI.verifyElementVisible(findTestObject(Const.expense + 'ExpenseError'))
	
	if(GVars.browser == "MicrosoftEdge"){
		gVal.objectTextEdge(Const.expense + 'ExpenseError', "Unable to Save Expense\nProject is required\nExpense Type is required\nCost Gross is required\nCost Net is required\nCharge Gross is required\nCharge Net is required")		
	}else{
		gVal.objectText(Const.expense + 'ExpenseError', "Unable to Save Expense\nProject is required\nExpense Type is required\nCost Gross is required\nCost Net is required\nCharge Gross is required\nCharge Net is required")
	}
	
	
Rep.nextTestStep("Click on [Search] button and select a [Resource] ")
	gObj.clearSetEditSync(Const.expense + 'input_Resource', expense.resource)
	gObj.buttonClickSync(Const.expense + 'ExpenseError')
	gAct.Wait(1)

	
Rep.nextTestStep("Verify todays date is populated in [Date] field ")
	gObj.buttonClickSync(Const.expense + 'input_Date')
	gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
	

Rep.nextTestStep("Select a project from the [Project] field")
		String projectText = gObj.getAttributeSync(Const.expense + 'input_Project', 'value')
		if(projectText == ""){
			gObj.setEditSync(Const.expense + 'input_Project', expense.project)
			gAct.Wait(GVars.shortWait)
			//Obj.buttonClickSync("//a[@id='ui-active-menuitem']")
	}

	
Rep.nextTestStep("Select a option from [Expense type] field ")
	gObj.selectComboByValueSync(Const.expense + 'select_Expense', expense.expenseType)


Rep.nextTestStep("Select a option from [category] field ")
	gObj.selectComboByValueSync(Const.expense + 'select_Category', expense.category)
	
	
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
	gAct.Wait(1)
	WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)
	
	
Rep.nextTestStep("Click onto [Expenses] button on the side bar ")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
	

Rep.nextTestStep("Verify recently added [Transaction] is visible ")
	gObj.buttonClickSync(Const.expense + 'a_AllExpenses')
	gObj.buttonClickSync(Const.expense + 'a_UnclaimedExpenses')
	gAct.Wait(GVars.midWait)
	
	int rowNo = sVal.searchSpecificTableReturnRow(10, category.name, "46992ac0-e69a-43f7-b578-fddfcf428a24")


Rep.nextTestStep("[Open] the Expense and verify the details ")
	Val.expense(expense, rowNo)
	