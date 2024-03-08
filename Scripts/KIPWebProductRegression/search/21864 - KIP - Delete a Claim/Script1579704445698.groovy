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
import models.Expense
import models.GenerateExpense
import models.Category
import models.GenerateCategory
import models.Claim
import models.GenerateClaim

String searchItem = "Claims"
String searchVal = "Claims"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	int amt = Com.generateRandomNumber(199)
	String expenseVal = amt.toString() + ".00"
	
	def pattern = "dd/MM/yyyy"
	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	String claimNotes = Com.generateRandomText(18)
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	component.createCategory(category)
	
	String newDate = Com.todayDate()
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", category.code, true, expenseVal, "", expenseVal, false, "Some random notes"]
	Expense expense = GenerateExpense.createExpense(newExpense)
	component.createExpense(expense)
	
	String[] newClaim = [categoryCode,  GVars.user, newDate, claimNotes]
	Claim claim = GenerateClaim.createClaim(newClaim)
	String ref = component.createClaim(claim)

	
Rep.nextTestStep("Select [Claims] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of an Claims")
	int rowNo = sVal.searchTableReturnRow(3, ref)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [Delete]")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "a_DeletePosition", 4, "pos")
	
	
Rep.nextTestStep("Click OK")
	gObj.buttonClickSync(Const.columnPicker + "button_OK")

	
Rep.nextTestStep("Verify that the deleted expense type is not available for selection")
	sVal.searchTableWithFooterDeletedValue(3, ref)
	
