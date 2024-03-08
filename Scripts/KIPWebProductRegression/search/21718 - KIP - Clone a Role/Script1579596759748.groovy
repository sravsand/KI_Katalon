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
import models.Role
import models.GenerateRole
import models.CostCentre
import models.GenerateCostCentre

String searchItem = "Roles"
String searchVal = "Roles"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String roleName = Com.generateRandomText(8)
	String roleCode = roleName.toUpperCase()
	String[] newRole = [roleName, roleCode, "KIP Dev Team 1", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "", "", ""]
	Role role = GenerateRole.createNewRole(newRole)
	component.createRole(role)
	
	String clonedRoleName = Com.generateRandomText(8)
	String clonedRoleCode = clonedRoleName.toUpperCase()
	String[] newClonedRole = [clonedRoleName, clonedRoleCode, "KIP Dev Team 1", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "", "", ""]
	Role clonedRole = GenerateRole.createNewRole(newClonedRole)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Roles] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)


Rep.nextTestStep("Click onto [Inline menu] of a Roles")
	WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
	WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.columnPicker + "previousPage"))
	int rowNo = sVal.searchTableReturnRow(3, role.name)
	WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(3)
	gAct.Wait(2)
	

Rep.nextTestStep("Amend text in [Name] field")
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceName"), clonedRole.name)
	
	
Rep.nextTestStep("Add text in [Code] field")
	WebUI.setText(findTestObject(Const.roleMenu + "roleCode"), clonedRole.code)
		
		
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
	gAct.Wait(2)
	WebUI.refresh()
	gAct.Wait(3)
	WebUI.click(findTestObject(Const.columnPicker + "nextPage"))
	WebUI.click(findTestObject(Const.columnPicker + "previousPage"))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, clonedRole.name)

