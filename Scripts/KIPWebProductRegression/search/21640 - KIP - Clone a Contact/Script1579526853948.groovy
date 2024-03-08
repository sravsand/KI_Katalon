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
import buildingBlocks.createComponentsLogedIn as component
import models.Contact
import models.GenerateContact

String searchItem = "Contacts"
String searchVal = "Contact"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String contactFirstName = Com.generateRandomText(6)
	String contactSurname = Com.generateRandomText(8)
	int contactTel = Com.generateRandomNumber(999999)
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	
	String[] newContact = [contactFirstName, contactSurname, "PPM Solutions Inc", "Testing", "Tester", contactTel]
	Contact contact = GenerateContact.createContact(newContact)
	component.createContact(contact)
	
	String cloneContactFirstName = Com.generateRandomText(6)
	String cloneContactSurname = Com.generateRandomText(8)
	int cloneContactTel = Com.generateRandomNumber(999999)
	
	String[] newCloneContact = [cloneContactFirstName, cloneContactSurname, "PPM Solutions Inc", "Testing", "Tester", cloneContactTel]
	Contact clonedContact = GenerateContact.createContact(newCloneContact)


Rep.nextTestStep("Click On [Search] from the side bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
	

Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))
	
	
Rep.nextTestStep("Select [Contacts] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)


Rep.nextTestStep("Click onto [Inline menu] of a Contacts")
	int rowNo = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [clone]")
	WebUI.click(findTestObject(Const.columnPicker + "a_CloneItem"))
	gAct.Wait(1)
	

Rep.nextTestStep("Amend text in [Name] field")
	WebUI.setText(findTestObject(Const.columnPicker + "input_firstName"), clonedContact.firstName)

	
Rep.nextTestStep("Add [Last Name]")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Surname"), contact.surname)
	

Rep.nextTestStep("Select [Active] through checkbox")
	boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
	if(!active){
		WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
	}

	
Rep.nextTestStep("Add [customer]")
	WebUI.setText(findTestObject(Const.columnPicker + "input_Customer"), contact.customer)
		
	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	sVal.searchTableAddedValue(3, contact.firstName + " " + contact.surname)

