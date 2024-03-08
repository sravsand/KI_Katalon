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
import models.Category
import models.GenerateCategory
import buildingBlocks.createComponents

//change from default login
GVars.configFileName = 	"katalonConfigAuto2.xml"
Act.changeUserCredentials()

String expenseNotes = Com.generateRandomText(16)
String newDate = Com.todayDate()
String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", "DEFAULT", true, "15.00", "", "15.00", false, expenseNotes]
Expense expense = GenerateExpense.createExpense(newExpense)
createComponents.createExpense(expense)


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [Expenses] button on the side bar ")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
	
	
Rep.nextTestStep("Click on [Claims] Tab")
	gObj.buttonClickSync(Const.claim + 'a_Claims')

	
Rep.nextTestStep("Click on [+Add]")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.claim + 'a_Add')

	
Rep.nextTestStep("Click [Search] and select the [Default Category] option")
	gObj.setEditSync(Const.claim +'input_CategoryCodeFilter', expense.category)
	gObj.checkSync(Const.claim +'input_SearchFilter')

	gObj.checkSync(Const.claim +'input_Default Category_chkSelect')
	gObj.buttonClickSync(Const.claim +'button_Select')


Rep.nextTestStep("Select a [Resource] ( Account holder)")
	String resourceName = gObj.getAttributeSync(Const.claim + 'input_Resource', 'value')
	if(resourceName != GVars.user){
		gObj.setEditSync(Const.claim + 'input_Resource', GVars.user)
	}

	
Rep.nextTestStep("Verify today's date is populated in [Date] field ")
	String date = gObj.getEditTextSync(Const.claim + 'date')
	gAct.compareStringAndReport(date, newDate)

	
Rep.nextTestStep("Add notes to [Notes] field ")
	gObj.setEditSync(Const.claim + 'textarea_Notes', "Test Notes")


Rep.nextTestStep("Select date range from [Expense] section ")
	gObj.buttonClickSync(Const.claim + 'dateFrom')
	gObj.buttonClickSync(Const.datePicker + 'button_Today')
	
	def today = new Date()
	def todayDate = today.format('MMM')
	String nextMonth = Com.getNextMonth()
	String[] nextMnthYear = nextMonth.split("/")
	gObj.buttonClickSync(Const.datePicker + 'button_Close')
	
	gObj.buttonClickSync(Const.claim + 'dateTo')
	gObj.selectComboByLabelSync(Const.datePicker + 'select_Month_To', nextMnthYear[0])
	gObj.selectComboByLabelSync(Const.datePicker + 'select_Year_To', nextMnthYear[1])
	gObj.buttonClickDblSubGenSync(Const.datePicker + "a_Date_To", 3, "row", 1, "col")
	

Rep.nextTestStep("Select an [Expense] - If Applicable")
	WebUI.check(findTestObject(Const.claim +'chkSelectExpense', [('row'): 1]))

	
Rep.nextTestStep("Click [Save] ")
	gObj.buttonClickSync(Const.claim + 'button_Save')
	gAct.Wait(3)


Rep.nextTestStep("Click [OK] on popup message")
	WebUI.acceptAlert()

	WebUI.switchToWindowIndex(1)
	Com.edgeSync(2)
	WebUI.maximizeWindow()
	Com.firefoxSync()
	Com.edgeSync()
	WebUI.verifyTextPresent(GVars.user, false)
	WebUI.verifyTextPresent(newDate, false)
	WebUI.verifyTextPresent("£15.00", false)
	WebUI.verifyTextPresent("KIP Dev Team 1", false)
	WebUI.verifyTextPresent("Keyed In", false)
	WebUI.verifyTextPresent("KIP Scheduling Project", false)
	WebUI.verifyTextPresent("Meals", false)
	WebUI.closeWindowIndex(1)
	