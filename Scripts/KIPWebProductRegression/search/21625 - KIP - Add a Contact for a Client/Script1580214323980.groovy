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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Client
import models.GenerateClient
import models.Contact
import models.GenerateContact

String searchItem = "Customers"
String searchVal = "Client"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String clientName = Com.generateRandomText(10)
	String clientCode = clientName.toUpperCase()
	
	String[] newClient = [clientName, clientCode]
	Client client = GenerateClient.createClient(newClient)
	component.createClient(client)
	
	String contactFirstName = Com.generateRandomText(6)
	String contactSurname = Com.generateRandomText(8)
	int contactTel = Com.generateRandomNumber(999999)
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	
	String[] newContact = [contactFirstName, contactSurname, clientName, "Testing", "Tester", contactTel]
	Contact contact = GenerateContact.createContact(newContact)


Rep.nextTestStep("Click On [Search] from the side bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")


Rep.nextTestStep("Click on [Filter] dropdown")
	WebUI.click(findTestObject(Const.search + "search_DropDown"))


Rep.nextTestStep("Select [Action View] from dropdown list")
	WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
	gAct.Wait(1)


Rep.nextTestStep("Click on an [Inline] menu of [Customer]")
	WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
	WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
	gAct.Wait(1)
	int rowNo = sVal.searchTableReturnRow(3, client.name)
	WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
	gAct.Wait(1)


Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	gAct.Wait(1)


Rep.nextTestStep("Click onto [Contacts] tab within the modal ")
	WebUI.click(findTestObject(Const.kip4Customer + "tab_Contacts"))

	
Rep.nextTestStep("Click onto [Add] button")
	WebUI.click(findTestObject(Const.kip4Customer + "btn_Add"))

	
Rep.nextTestStep("Add text in [First Name] field")
	WebUI.setText(findTestObject(Const.kip4Contacts + "input_firstName"), contact.firstName)

	
Rep.nextTestStep("Add text in [Last Name]")
	WebUI.setText(findTestObject(Const.kip4Contacts + "input_lastName"), contact.surname)


Rep.nextTestStep("Select [Active] through checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		WebUI.check(findTestObject(Const.kip4Generic + "chk_Active"))
	}

	
Rep.nextTestStep("Verify the [Customer] is Pre-populated and not editable")
	String expCust = WebUI.getText(findTestObject(Const.kip4Contacts + "input_CustDisabled"))
	gAct.compareStringAndReport(expCust, client.name)
	

Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.kip4Generic + "button_SaveAndClose"))
	gAct.Wait(3)
		
	
Rep.nextTestStep("Verify the [Contact] details are visible ")
	String actContact = WebUI.getText(findTestObject(Const.kip4Customer + "added_Contact"))
	gAct.findSubstringInStringAndReport(actContact, contact.firstName + " " + contact.surname)
	
Rep.nextTestStep("Click [Save and Close]")
	WebUI.click(findTestObject(Const.kip4Generic + "button_SaveAndClose"))
	