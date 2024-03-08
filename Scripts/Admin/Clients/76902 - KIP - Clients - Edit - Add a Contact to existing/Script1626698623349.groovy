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
import models.Customer
import models.GenerateCustomer
import models.ClientGroup
import models.GenerateClientGroup
import models.Address
import models.GenerateAddress
import models.NewContact
import models.GenerateNewContact

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String conaddress1 = Com.generateRandomText(6)
	String conaddress2 = Com.generateRandomText(8)
	String concity = Com.generateRandomText(9)
	String constate = Com.generateRandomText(7)
	String concountry = Com.generateRandomText(9)
	String conpostcode = Com.generateRandomText(2) + (Com.generateRandomNumber(99)).toString() + " " + (Com.generateRandomNumber(9)).toString() + Com.generateRandomText(2)
	
	String[] newconAddress = [conaddress1, conaddress2, concity, constate, concountry, conpostcode]
	Address conaddress = GenerateAddress.createAddress(newconAddress)
	
	String contactFirstName = Com.generateRandomText(6)
	String contactMiddleName = Com.generateRandomText(11)
	String contactSurname = Com.generateRandomText(8)
	String contactTelcon = Com.generateRandomNumber(999999).toString()
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	String fax = Com.generateRandomNumber(9999999).toString()
	String email = Com.generateRandomText(6) + "@" + Com.generateRandomText(6) + ".com"
	
	String[] newContact = ["Mr", contactFirstName, contactMiddleName, contactSurname, "Architecture Now", "Testing", "Tester", contactTelcon, altContactTel, fax, "Test", email]
	NewContact contact = GenerateNewContact.createNewContact(newContact, conaddress)
	
	
	String address1 = Com.generateRandomText(6)
	String address2 = ""
	String city = Com.generateRandomText(9)
	String state = Com.generateRandomText(7)
	String country = Com.generateRandomText(9)
	String postcode = Com.generateRandomText(2) + (Com.generateRandomNumber(99)).toString() + " " + (Com.generateRandomNumber(9)).toString() + Com.generateRandomText(2)
	String contactTel = Com.generateRandomNumber(999999).toString()
	
	String[] newAddress = [address1, address2, city, state, country, postcode]
	Address address = GenerateAddress.createAddress(newAddress)
	
	String clientGroupName = Com.generateRandomText(10)
	String clientGroupCode = clientGroupName.toUpperCase()
	
	String[] newClientGroup = [clientGroupName, clientGroupCode]
	ClientGroup clientGroup = GenerateClientGroup.createClientGroup(newClientGroup)
	component.createClientGroup(clientGroup)
	
	String clientName = Com.generateRandomText(10)
	String clientCode = clientName.toUpperCase()
	
	def newClient = [clientName, clientCode, address, contactTel, "", "", "", clientGroup.name]
	Customer client = GenerateCustomer.createCustomerKIP4(newClient)
	component.createClientKIP4(client)


Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Client]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Customers")
	

Rep.nextTestStep("Click onto [Inline menu] against a client")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, client.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Select [Contacts] tab")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Contacts")
	
	
Rep.nextTestStep("Click Add")
	gObj.buttonClickSync(Const.kip4Customer + "btn_Add")
	

Rep.nextTestStep("Enter a [First Name] and [Surname] (Required)")
	WebD.setEditText('id("firstName")',  contact.firstName)
	WebD.setEditText('id("lastName")',  contact.surname)

	
Rep.nextTestStep("Click Save")
	gObj.buttonClickSync(Const.kip4Customer + "btn_SaveAndClose")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Ensure recently added [Contact] is displayed in the table")
	String contactDets = gObj.getEditTextSync(Const.kip4Customer + "ContactsDetails")
	gAct.findSubstringInStringAndReport(contactDets, contact.firstName + " " + contact.surname)

	
Rep.nextTestStep("Close without saving and reopen the same [Client]")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, client.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Select [Contacts] tab")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Contacts")

	
Rep.nextTestStep("Ensure recently added [Contact] is displayed in the table")
	String contactDets2 = gObj.getEditTextSync(Const.kip4Customer + "ContactsDetails")
	gAct.findSubstringInStringAndReport(contactDets2, contact.firstName + " " + contact.surname)


Rep.nextTestStep("Click close")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

