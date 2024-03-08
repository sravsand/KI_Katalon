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

String searchItem = "Claims"
String searchVal = "Claims"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	int amt = Com.generateRandomNumber(199)
	String expenseVal = amt.toString() + ".00"
	
	def pattern = "dd/MM/yyyy"
	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	component.createCategory(category)
	
	String newDate = Com.todayDate()
	String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", category.code, true, expenseVal, "", expenseVal, false, "Some random notes"]
	Expense expense = GenerateExpense.createExpense(newExpense)
	component.createExpense(expense)


Rep.nextTestStep("Select [Claims] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
		
	gObj.setEditSync(Const.columnPicker + "input_CategoryCode", category.code)
	gObj.buttonClickSync(Const.columnPicker + "btn_Search")
	gObj.checkSync(Const.claim +'input_Default Category_chkSelect')
	gObj.buttonClickSync(Const.claim +'button_Select')
	

Rep.nextTestStep("Search & Select [Resource]")
	String resourceName = gObj.getAttributeSync(Const.claim + 'input_Resource', 'value')
	if(resourceName != GVars.user){
		gObj.setEditSync(Const.claim + 'input_Resource', GVars.user)
	}


Rep.nextTestStep("Select an [Expense] through Checkbox")
	Rep.info("Expense chosen on the previous screen")


Rep.nextTestStep("Click on [Save]")
	gObj.buttonClickSync(Const.expense + 'button_Save')


Rep.nextTestStep("Click [OK]")
	def AlertExist = WebUI.verifyAlertPresent(GVars.midWait, FailureHandling.OPTIONAL)
	if(AlertExist) {
		gObj.acceptAlertSync()
		gAct.Wait(1)
		WebUI.switchToWindowIndex(1)
		WebUI.closeWindowIndex(1)
		WebUI.switchToDefaultContent()
	}
	
	
Rep.nextTestStep("Click [Close]")
	WebUI.switchToDefaultContent()
	String refNo = gObj.getEditTextSync(Const.claim + "ClaimReference")
	gObj.buttonClickSync(Const.columnPicker + 'button_CloseSubmission')


Rep.nextTestStep("Verify the recently added claim details are visible in table")
	gObj.buttonClickSync(Const.columnPicker + 'a_ExpenseClaimFilter')
		
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	gObj.setEditSync(Const.timesheetFilter + "input_Value", todaysDate)
	WebD.setEditText("//td[@id='valueCol1']//input[@id='valueField']", GVars.user)
	gObj.buttonClickSync(Const.timesheetFilter + 'a_Search')

	sVal.searchTableAddedValue(3, refNo)
	