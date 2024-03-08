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
	
	String[] newClonedExpenseType = [clonedexpensetypeName, clonedexpensetypeCode, "Default Currency", "1", "false", "12.00"]
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
	
	
Rep.nextTestStep("Click onto [Clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Verify the [Name] is pre-populated")
	String actName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actName, expenseType.name)
	gObj.setEditSync(Const.kip4Generic + "input_Name", clonedExpenseType.name)
	
	
Rep.nextTestStep("Verify User is able to add [Code] (Code not auto generated)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", clonedExpenseType.code)
	
	
Rep.nextTestStep("Verify the [Active Checkbox] is pre-selected as per the cloned Expense Type")
	def activeRem = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(activeRem, "true")

			
Rep.nextTestStep("Save Cloned Expense Type")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the Expense Type table")
	sVal.searchTableAddedValue(2, clonedExpenseType.code)
	