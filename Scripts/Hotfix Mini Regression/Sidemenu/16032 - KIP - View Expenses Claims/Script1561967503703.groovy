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
import buildingBlocks.createComponents as component

//change from default login
GVars.configFileName = 	"katalonConfigAuto3.xml"
Act.changeUserCredentials()

String newDate = Com.todayDate()
String[] newExpense = [GVars.user, newDate, "KIPDEV1", "DEFAULT", "PRJ017", "MEALS", "DEFAULT", true, "16.00", "", "16.00", false, "Some random notes"]
Expense expense = GenerateExpense.createExpense(newExpense)
component.createExpense(expense)


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [Expenses] button on the side bar ")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
	
	
Rep.nextTestStep("Click onto [Inline] menu (an arrow in a square Sign displayed)")
	gObj.buttonClickSync(Const.expense + 'a_AllExpenses')
	gObj.buttonClickSync(Const.expense + 'a_UnclaimedExpenses')

	gObj.buttonClickSubIntPosSync(Const.expense + 'inlineMenu', 1, "row")


Rep.nextTestStep("Click [Open] ")
	gObj.buttonClickSync(Const.expense + 'a_Open')
	WebUI.verifyElementVisible(findTestObject(Const.expense + 'AmendExpenseHeader'))

	
Rep.nextTestStep("Click [Close]")
	WebD.clickElement("//button[@class='btn btn-default button']")
	
	
Rep.nextTestStep("Click on [Claims] Tab")
	gObj.buttonClickSync(Const.claim + 'a_Claims')
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Select and open existing claim by clicking [Inline] Menu and then [Open]")
	gObj.buttonClickSubIntPosSync(Const.claim + 'inlineMenu_Claims', 1, "row")
	gObj.buttonClickSync(Const.claim + 'a_Open')
	gAct.Wait(1)
	WebUI.verifyElementVisible(findTestObject(Const.claim + 'ViewClaimHeader'))
	

Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.claim + 'button_Close')

