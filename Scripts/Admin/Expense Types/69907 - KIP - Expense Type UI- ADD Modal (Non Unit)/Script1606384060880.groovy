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
	
	String[] newExpenseType = [expensetypeName, expensetypeCode, "", "1", "false", "12.00"]
	ExpenseType expenseType = GenerateExpenseType.createExpenseType(newExpenseType)

		
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Expense Type]")
	gObj.buttonClickSync(Const.busEntMenu + "a_ExpenseTypes")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", expenseType.name)
	
	
Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", expenseType.code)
	
	
Rep.nextTestStep("Click over [Allow Receipt] checkbox")
	gObj.scrollToElementSync(Const.kip4ExpenseType + "chk_AllowReceipt")
	gObj.buttonClickSync(Const.kip4ExpenseType + "chk_AllowReceipt")
	
	
Rep.nextTestStep("Click over [Reimbursable default value] checkbox")
	gObj.buttonClickSync(Const.kip4ExpenseType + "chk_Reimbdefvalue")
	
	
Rep.nextTestStep("Click over [Default] tab")
	gObj.buttonClickSync(Const.kip4ExpenseType + "tab_Defaults")
	
	
Rep.nextTestStep("Click over [Restrictions] tab")
	gObj.buttonClickSync(Const.kip4ExpenseType + "tab_Restrictions")
	

Rep.nextTestStep("Click over [Expense Type Custom Field] tab")
	gObj.buttonClickSync(Const.kip4ExpenseType + "tab_CustomFields")
	
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the Expense Type table")
	sVal.searchTableAddedValue(2, expenseType.code)
	