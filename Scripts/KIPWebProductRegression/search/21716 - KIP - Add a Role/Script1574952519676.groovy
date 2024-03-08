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
import search.Action as sAct
import search.Validate as sVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Role
import models.GenerateRole
import models.CostCentre
import models.GenerateCostCentre


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
	
	String searchItem = "Roles"
	String searchVal = "Roles"


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Roles] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click On [+ Add] ")
	WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
	
	
Rep.nextTestStep("Add text in [Name] field")
	WebUI.click(findTestObject(Const.roleMenu + "generalTab"))
	WebUI.setText(findTestObject(Const.roleMenu + "roleName"), role.name)
	
	
Rep.nextTestStep("Add text in [Code] field")
	WebUI.setText(findTestObject(Const.roleMenu + "roleCode"), role.code)

	
Rep.nextTestStep("Select [Active] through checkbox")
	boolean active = WebUI.verifyElementChecked(findTestObject(Const.roleMenu + "roleActive"), 10)
	if(!active){
		WebUI.check(findTestObject(Const.roleMenu + "roleActive"))
	}

	WebUI.setText(findTestObject(Const.roleMenu + "roleDept"), role.department)
	WebUI.setText(findTestObject(Const.roleMenu + "roleLocation"), role.location)
	WebUI.setText(findTestObject(Const.roleMenu + "roleCostCentre"), role.costCentre)
	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.roleMenu + "button_SaveAndClose"))
	
	boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.roleMenu + "saveRoleError"), 5, FailureHandling.CONTINUE_ON_FAILURE)
	
	if(!saveError){
		Rep.fail("Role wasn't created.")
	}
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the newly added Role is added onto the table and available for selection")
	sVal.searchTableAddedValue(3, role.name)
