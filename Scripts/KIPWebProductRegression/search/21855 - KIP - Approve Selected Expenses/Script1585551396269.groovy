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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Expense
import models.GenerateExpense
import models.Category
import models.GenerateCategory

String searchItem = "Expenses"
String searchVal = "Expenses"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String expenseNotes = Com.generateRandomText(16)
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	
	component.createCategory(category)
	
	String newDate = Com.todayDate()
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", category.code, true, "16.00", "", "16.00", false, expenseNotes]
	Expense expense = GenerateExpense.createExpense(newExpense)
	component.createExpense(expense)

	
Rep.nextTestStep("Select [Expenses] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu] of Expenses")
	gObj.buttonClickSync(Const.columnPicker + "a_Unapproved Expenses")
	int rowNo = sVal.searchTableReturnRow(10, categoryName)
	
	
Rep.nextTestStep("Select the desirable [Expense] user want to approve through checkbox")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "chk_ExpenseRow",rowNo, "row")
	
	
Rep.nextTestStep("Click [Approve] button")
	gObj.buttonClickSync(Const.columnPicker + "a_ApproveExpense")
	
	
Rep.nextTestStep("Click [Save] button")
	gObj.buttonClickSync(Const.columnPicker + "btn_ApproveExpense_Save")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Verity Approved column is displayed Yes")
	gObj.buttonClickSync(Const.columnPicker + "a_Unclaimed Expenses")
	int rowNum = sVal.searchTableReturnRow(10, categoryName)
	String Active_new = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 15, "col")
	gAct.compareStringAndReport(Active_new, "Yes")
	