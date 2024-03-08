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
import org.apache.commons.lang.RandomStringUtils as Random

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

import models.ExpenseType
import models.GenerateExpenseType

String ExpenseName = Com.generateRandomText(10)
String ExpenseCode = ExpenseName.toUpperCase()

String[] newExpenseType = [ExpenseName, ExpenseCode, "", "1", "false", ""]
ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)

String searchItem = "Expense Types"
String searchVal = "Expense Type"

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Expense Types] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	
	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")

	
Rep.nextTestStep("Click on [Save] or [Save & Close] button")
	WebUI.verifyElementHasAttribute(findTestObject(Const.columnPicker + "button_SaveAndClose"), 'disabled', GVars.midWait)
//	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
//	String errorMsg = WebUI.getText(findTestObject(Const.columnPicker + "addExpenseError"))
//	gAct.compareStringAndReport(errorMsg, "Unable to Save Expense Type")
	
	
Rep.nextTestStep("Add text in [Name] field")
	gObj.setEditSync(Const.kip4Generic + "input_Name", expenseType.name)
	
	
Rep.nextTestStep("Add [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", expenseType.code)
	
	
Rep.nextTestStep("Select [Active] through checkbox")
	boolean active = WebUI.verifyElementChecked(findTestObject(Const.kip4Generic + "chk_Active"), GVars.midWait, FailureHandling.OPTIONAL)
	if(!active){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}

	
Rep.nextTestStep("Select [Currency] from dropdown list")
	if(expenseType.currency != "")
	{
		gObj.buttonClickSync(Const.kip4ExpenseType + "chk_FixedCurrency")
		gObj.buttonClickSync(Const.kip4ExpenseType + "dropDown_Currency")
		gObj.setEditSync(Const.kip4ExpenseType + "input_Currency", expenseType.currency)
		gObj.buttonClickSubSync(Const.kip4ExpenseType + "select_Currency", " " + expenseType.currency + " ", "curr")
	}


Rep.nextTestStep("Enter [1] in [Multiply expense charge on by]")
	gObj.setEditSync(Const.kip4ExpenseType + "input__multipleExpense", expenseType.multiplyExpChg)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the newly added Expense Type is added onto the table")
	sVal.searchTableAddedValue(2, expenseType.code)
	