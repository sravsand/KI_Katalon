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
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = ["Dave_" + ResourceName, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time", costCentreNew, "Martin Phillips", "AutoTest@Workflows.com", "", "100", "", "", "", "", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)
	
	String searchItem = "Resources"
	String searchVal = "Resources"


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Resources] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click On [+ Add] ")
	WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

	
Rep.nextTestStep("Select [Add from a template]")
	WebUI.click(findTestObject(Const.resourceMenu + 'radio_AddTemplateResource'))
	
	
Rep.nextTestStep("Click on [Template] dropdown menu")
	WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))
	
	
Rep.nextTestStep("Select a template from dropdown list")
	WebUI.selectOptionByLabel(findTestObject(Const.resourceMenu + 'select_Template'), "PROJECT MANAGER | Project Manager" , false)
	WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))
	
	
Rep.nextTestStep("Click [OK]")	
	WebUI.click(findTestObject(Const.resourceMenu + 'button_OK'))
	
	gAct.Wait(2)
	
	
Rep.nextTestStep("Verify the pre-populated [Name] field")
	WebUI.click(findTestObject(Const.resourceMenu + "generalTab"))
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceName"), resource.name)

	boolean active = WebUI.verifyElementChecked(findTestObject(Const.resourceMenu + "resourceActive"), 10)
	if(!active){
		WebUI.check(findTestObject(Const.resourceMenu + "resourceActive"))
	}

	
Rep.nextTestStep("Search & add [Department]")
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceDept"), resource.department)
	
	
Rep.nextTestStep("Search & add [Primary Role]")
	WebUI.setText(findTestObject(Const.resourceMenu + "resourcePrimaryRole"), resource.role)
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceLocation"), resource.location)
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceCostCentre"), resource.costCentre)
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceLineManager"), resource.lineManger)
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceEmail"), resource.email)
	WebUI.setText(findTestObject(Const.resourceMenu + "resourceCapacity"), resource.capacity)
	
	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
	gAct.Wait(3)
	
	boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)
	
	if(!saveError){
		Rep.fail("Resource has not been created")
	}


Rep.nextTestStep("Verify recently added risk within [deliverable Type] table")
	sVal.searchTableAddedValue(3, resource.name)
