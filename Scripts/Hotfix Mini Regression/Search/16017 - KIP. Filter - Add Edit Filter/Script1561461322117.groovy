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
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct


String searchItem = "Timesheet Submissions"
String filter = "Submission Submitted for Approval"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click On [Search] from the side bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

	
Rep.nextTestStep("Select an item from the [Drop-down] list within filter section i.e Timesheet Submissions ")
	gObj.buttonClickSync(Const.search + "search_DropDown")
	gObj.buttonClickSubSync(Const.search + "filterType", searchItem, "label")
	
	
Rep.nextTestStep("Click on [+Add row]")
	gObj.buttonClickSync(Const.search + "a_Add Row")
	

Rep.nextTestStep("Select an item from [Dropdown] list of newly created row i.e Submission Submitted for Approval")
	gObj.buttonClickSync(Const.search + "searchCatDropdown")
	gObj.buttonClickSubSync(Const.search + "searchSelection", filter, "selection")


Rep.nextTestStep("Select [=] from the second field dropdown")
	gObj.buttonClickSync(Const.search + "symbolDropDown")
	gObj.buttonClickSync(Const.search + "equalsSymbol")


Rep.nextTestStep("Select an option from the third field dropdown [Yes] or [No]")
	gObj.buttonClickSync(Const.search + "valueDropDown")
	gObj.buttonClickSync(Const.search + "yesSelection")


Rep.nextTestStep("Click on [Search]")
	gObj.buttonClickSync(Const.search + "a_Search")
	gAct.Wait(2)
	int rowCnt = WebD.getTableRowCount("//div[@id='searchResults']/div")
	for(int intCnt = 1; intCnt < rowCnt; intCnt ++){
		String cellText = gObj.getEditTextSubIntPosSync(Const.search + 'statusCell', intCnt, "row")
		gAct.compareStringAndReport(cellText, "Submitted for approval")
	}
