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
import com.kms.katalon.core.webui.driver.DriverFactory
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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Issue
import models.GenerateIssue

String searchItem = "Issues"
String searchVal = "Issues"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String issueTitle = Com.generateRandomText(10)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	String issueDescription = Com.generateRandomText(10) + " " + Com.generateRandomText(10) + " " + Com.generateRandomText(4)
	String newIssueDesc = Com.generateRandomText(15) + "_" + Com.generateRandomText(4)
	String[] newIssue = [issueTitle, "Project Innovation_" + GVars.selectedBrowser, GVars.user, "Open", todaysDate, "", "Project", "Medium", "Improving", issueDescription, ""]
	Issue issue = GenerateIssue.createNewIssue(newIssue)
	component.createIssue(issue)
	
	String newCode = Com.generateRandomText(5).toUpperCase()
	

Rep.nextTestStep("Select [Issues] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	WebD.clickElement("//a[@class='menuChooser']")
	gObj.buttonClickSync(Const.columnPicker + "a_Column Chooser")
	gAct.Wait(2)
	WebUI.dragAndDropToObject(findTestObject(Const.columnPicker + "col_Project"), findTestObject(Const.columnPicker + "col_Title"))
	gAct.Wait(2)
	WebUI.dragAndDropToObject(findTestObject(Const.columnPicker + "col_Code"), findTestObject(Const.columnPicker + "col_Project"))
	gObj.buttonClickSync(Const.columnPicker + "button_Save")
	
	String textVal = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
	if(textVal != "10"){
		gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	}
	
	
Rep.nextTestStep("Click onto [Inline menu] of a Issues")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(4, issue.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Code convert]")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "a_CodeConvert", 4, "pos")
	
	
Rep.nextTestStep("Enter code in [New Code] textbox")
	gObj.setEditSync(Const.columnPicker + "input_NewCode", newCode)
	
	
Rep.nextTestStep("Click on [Convert]")
	WebUI.click(findTestObject(Const.columnPicker + "btnConvert"))
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click on [OK]")
	if(WebUI.verifyAlertPresent(GVars.shortWait, FailureHandling.OPTIONAL))
	{
		WebUI.acceptAlert()
	}
	
	
Rep.nextTestStep("Click on [Close]")
	WebUI.switchToDefaultContent()
	gObj.buttonClickSync(Const.columnPicker + "button_CloseCodeConverterModal")
	gAct.Wait(GVars.shortWait)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)


Rep.nextTestStep("Verify that the changes have been updated in expense type table")
	sVal.searchTableAddedValue(2, newCode)

