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
import models.Client
import models.GenerateClient
import models.NewContact
import models.GenerateNewContact
import models.Address
import models.GenerateAddress

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String address1 = Com.generateRandomText(6)
	String address2 = Com.generateRandomText(8)
	String city = Com.generateRandomText(9)
	String state = Com.generateRandomText(7)
	String country = Com.generateRandomText(9)
	String postcode = Com.generateRandomText(2) + (Com.generateRandomNumber(99)).toString() + " " + (Com.generateRandomNumber(9)).toString() + Com.generateRandomText(2)
	
	String[] newAddress = [address1, address2, city, state, country, postcode]
	Address address = GenerateAddress.createAddress(newAddress)
	
	String clientName = Com.generateRandomText(10)
	String clientCode = clientName.toUpperCase()
	
	String[] newClient = [clientName, clientCode]
	Client client = GenerateClient.createClient(newClient)
	component.createClient(client)

	String contactFirstName = Com.generateRandomText(6)
	String contactSurname = Com.generateRandomText(8)
	int contactTel = Com.generateRandomNumber(999999)
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	
	String[] newContact = ["Mr", contactFirstName, "", contactSurname, clientName, "Testing", "Tester", contactTel, "", "", "", ""]
	NewContact contact = GenerateNewContact.createNewContact(newContact, address)
	component.createContact(contact)
	
	Nav.selectSearchFilter("Customers")

	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, client.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gObj.selectInlineOption(1)
	gObj.buttonClickSync(Const.kip4Customer + "tab_Contacts")
	gObj.selectComboByLabelSync(Const.kip4Customer + 'select_PrimaryContact', contact.firstName + " " + contact.surname)

	gObj.scrollToElementSync(Const.kip4Customer + 'select_AccountContact')
	gObj.selectComboByLabelSync(Const.kip4Customer + 'select_AccountContact', contact.firstName + " " + contact.surname)
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()

	gObj.clickLogoSync()
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Contact]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	

Rep.nextTestStep("Click onto [Inline menu] against a contacts")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	
	
Rep.nextTestStep("Click onto [Delete]")
	gObj.selectInlineOption(3)
	
	
Rep.nextTestStep("Verify error")
	boolean modalError = gObj.elementPresentSync(Const.kip4DeleteModal + "text_DeleteMessage")
	if(modalError){
		String errtext = gObj.getEditTextSync(Const.kip4DeleteModal + "text_DeleteMessage")
		gAct.findSubstringInStringAndReport(errtext, "At least one Primary Contact refers to it")
	}
	
	
Rep.nextTestStep("Click onto [Cancel] button")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_Cancel")
	
	
Rep.nextTestStep("Verify the [client] still exists in the table")
	sVal.searchTableAddedValue(3, contact.firstName + " " + contact.surname)
	