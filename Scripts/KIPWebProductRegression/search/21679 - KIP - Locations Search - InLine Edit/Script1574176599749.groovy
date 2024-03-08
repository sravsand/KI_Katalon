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
import models.Location
import models.GenerateLocation

String searchItem = "Locations"
String searchVal = "Location"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String locationName = Com.generateRandomText(10)
	
	String locCode = locationName.toUpperCase()
	String locationCode = locCode.take(3)
	
	String[] newLocation = [locationName, locationCode]
	Location location = GenerateLocation.createLocation(newLocation)
	component.createLocation(location)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Locations] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)
	
	int rowNo = sVal.searchTableReturnRow(3, location.name)
	String ActiveStatus = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 4]))
	gAct.compareStringAndReport(ActiveStatus, "Yes")
	
	
Rep.nextTestStep("Click onto [Inline menu]of an expense type")
	WebUI.click(findTestObject(Const.columnPicker + "inlineEdit_Alt", [('row'): rowNo]))
	
	
Rep.nextTestStep("Click onto [Edit]")
	WebUI.click(findTestObject(Const.columnPicker + "a_Edit"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Tick the checkbox from [Action] column")
	WebD.clickElement("//div[@id='f954fc6f-d29f-4cc1-9906-a44cb84c4722']/div[5]/table/tbody/tr[" + rowNo + "]/td[4]/div/label/span")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click on [Floppy Icon] to save the changes")
	WebUI.click(findTestObject(Const.columnPicker + "a_SaveInline"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify that the changes are saved ")
	String ActiveStatus_new = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 4]))
	gAct.compareStringAndReport(ActiveStatus_new, "No")
