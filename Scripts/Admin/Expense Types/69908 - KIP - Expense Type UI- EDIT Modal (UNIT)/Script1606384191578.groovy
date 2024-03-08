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
import models.Expense
import models.GenerateExpense
import models.Category
import models.GenerateCategory

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String expensetypeName = Com.generateRandomText(7)
	String expensetypeCode = expensetypeName.toUpperCase()

	String[] newExpenseType = [expensetypeName, expensetypeCode, "Default Currency", "1", "false", "12.00"]
	ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)
	component.createExpenseType(expenseType)

		
	String clonedexpensetypeName = Com.generateRandomText(10)
	String clonedexpensetypeCode = clonedexpensetypeName.toUpperCase()

	String[] newClonedExpenseType = [clonedexpensetypeName, clonedexpensetypeCode, "Default Currency", "2", "false", "14.50"]
	ExpenseType clonedExpenseType = GenerateExpenseType.createExpenseType(newClonedExpenseType)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")


Rep.nextTestStep("Click over [Expense Type]")
	gObj.buttonClickSync(Const.busEntMenu + "a_ExpenseTypes")


Rep.nextTestStep("Click onto [Inline menu] against an Expense Type")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, expenseType.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")


Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", clonedExpenseType.name)
	
	
Rep.nextTestStep("Verify the [Code] field is locked and user unable to amend the value")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')
	
	
Rep.nextTestStep("Select [Active] checkbox")
	
	def active = WebD.getCheckBoxState("//input[@id='active']")
	
	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
Rep.nextTestStep("Click over [Unit Based] radio button")
	gObj.scrollToElementSync(Const.kip4ExpenseType + "span_UnitBased")
	gObj.buttonClickSync(Const.kip4ExpenseType + "span_UnitBased")
	
	
Rep.nextTestStep("Click over [Currency] dropdown")
//	WebUI.click(findTestObject(Const.kip4ExpenseType + "dropDown_CurrencyEdit"))
//	WebUI.click(findTestObject(Const.kip4Generic + 'delete_GenericComboValue', [('val'): 'currency']))
//	WebUI.setText(findTestObject(Const.kip4ExpenseType + "input_CurrencyEdit"), clonedExpenseType.currency)
//	gAct.Wait(1)
	
//	WebUI.click(findTestObject(Const.kip4ExpenseType + "select_Currency", [('curr'): " " + clonedExpenseType.currency + " "]))

//	gAct.Wait(3)
	
	
Rep.nextTestStep("Set [Cost Per Currency] value ")
	gObj.setEditSync(Const.kip4ExpenseType + "input__unitCost", clonedExpenseType.rate)
	
		
Rep.nextTestStep("Set [Cost Per Currency] value ")
	gObj.setEditSync(Const.kip4ExpenseType + "input__singleUnitLabel", clonedExpenseType.name)

	
Rep.nextTestStep("Set [Cost Per Currency] value ")
	gObj.setEditSync(Const.kip4ExpenseType + "input__PluralUnitLabel", clonedExpenseType.name)
	
	
Rep.nextTestStep("Set [Multiple Expense Value] value ")
	gObj.setEditSync(Const.kip4ExpenseType + "input__multipleExpense", clonedExpenseType.rate)
	
			
Rep.nextTestStep("Click over [Allow Receipt] checkbox")
	gObj.buttonClickSync(Const.kip4ExpenseType + "chk_AllowReceipt")
	
	
Rep.nextTestStep("Click over [Reimbursable default value] checkbox")
	gObj.buttonClickSync(Const.kip4ExpenseType + "chk_Reimbdefvalue")
			  
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the Expense Type table")
	sVal.searchTableAddedValue(3, clonedExpenseType.name)
