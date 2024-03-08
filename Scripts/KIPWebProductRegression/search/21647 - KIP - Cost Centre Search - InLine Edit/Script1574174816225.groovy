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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn as component
import models.CostCentre
import models.GenerateCostCentre

String searchItem = "Cost Centres"
String searchVal = "Cost Centre"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String costCentreName = Com.generateRandomText(10)
	String costCentreCode = costCentreName.toUpperCase()
	
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	component.costCentre(costCentre)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Cost Centres] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)
	
	int rowNo = sVal.searchTableReturnRow(3, costCentre.name)
	String ActiveStatus = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 4]))
	gAct.compareStringAndReport(ActiveStatus, "Yes")
	
	
Rep.nextTestStep("Click onto [Inline menu]of an Cost Centres")
	WebUI.click(findTestObject(Const.columnPicker + "inlineEdit_Alt", [('row'): rowNo]))
	
	
Rep.nextTestStep("Click onto [Edit]")
	WebUI.click(findTestObject(Const.columnPicker + "a_Edit"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Tick the checkbox from [Action] column")
	WebD.clickElement("//div[@id='60fa3135-a034-466f-8847-404052d97d11']/div[5]/table/tbody/tr[" + rowNo + "]/td[4]/div/label/span")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click on [Floppy Icon] to save the changes")
	WebD.clickElement("//div[@id='60fa3135-a034-466f-8847-404052d97d11']/div[5]/table/tbody/tr[" + rowNo + "]/td/div/a/i")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Verify that the changes are saved ")
	String ActiveStatus_new = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 4]))
	gAct.compareStringAndReport(ActiveStatus_new, "No")
