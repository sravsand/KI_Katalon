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
GVars.configFileName = 	"katalonConfigAuto7.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String expenseName = Com.generateRandomText(10)
	String expenseCode = expenseName.toUpperCase()
	
	String[] newExpenseType = [expenseName, expenseCode, "", "1", "false", ""]
	ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)
	
	component.createExpenseType(expenseType)
	
	
Rep.nextTestStep("Select [Expense Types] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gAct.Wait(1)
	gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int RowNumber = sVal.searchTableReturnRow(2, expenseType.code)
	String AllowReceiptStatus = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): RowNumber, ('col'): 6]))
	gAct.compareStringAndReport(AllowReceiptStatus, "No")
	
	
Rep.nextTestStep("Click onto [Inline menu]of an expense type")
	gObj.buttonClickSubSync(Const.columnPicker + "inlineEdit", RowNumber, "row")
	
	
Rep.nextTestStep("Click onto [Edit]")
	gObj.selectInlineOption(1)
	gAct.Wait(2)
	
	
Rep.nextTestStep("Tick the checkbox from [Allow Reciept] column")
	gObj.scrollToElementSync(Const.kip4ExpenseType + "chk_AllowReceipt")
	gObj.buttonClickSync(Const.kip4ExpenseType + "chk_AllowReceipt")

	
Rep.nextTestStep("Click on [Floppy Icon] to save the changes")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	gAct.Wait(1)

	
Rep.nextTestStep("Verify that the changes are saved ")
	String AllowReceiptStatus_new = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): RowNumber, ('col'): 6]))
	gAct.compareStringAndReport(AllowReceiptStatus_new, "Yes")
