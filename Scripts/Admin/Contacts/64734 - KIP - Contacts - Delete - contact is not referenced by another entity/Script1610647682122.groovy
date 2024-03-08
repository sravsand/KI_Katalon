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
	
	String contactFirstName = Com.generateRandomText(6)
	String contactSurname = Com.generateRandomText(8)
	int contactTel = Com.generateRandomNumber(999999)
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	
	String[] newContact = ["Mrs", contactFirstName, "", contactSurname, "PPM Solutions Inc", "Testing", "Tester", contactTel, "", "", "", ""]
	NewContact contact = GenerateNewContact.createNewContact(newContact, address)
	component.createContact(contact)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Contact]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	

Rep.nextTestStep("Click onto [Inline menu] against a contacts")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Delete]")
	gObj.selectInlineOption(3)
	
	
Rep.nextTestStep("Click [OK]")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK")
	Act.verifyDeletePopUpText()


Rep.nextTestStep("Verify the deleted client is not available for selction in currency table")
	sVal.searchTableDeletedValue(3, contact.firstName + " " + contact.surname)
	