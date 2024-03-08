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
	String contactMiddleName = Com.generateRandomText(11)
	String contactSurname = Com.generateRandomText(8)
	String contactTel = Com.generateRandomNumber(999999).toString()
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	String fax = Com.generateRandomNumber(9999999).toString()
	String email = Com.generateRandomText(6) + "@" + Com.generateRandomText(6) + ".com"
	
	String[] newContact = ["Mr", contactFirstName, contactMiddleName, contactSurname, "Architecture Now", "Testing", "Tester", contactTel, altContactTel, fax, "Test", email]
	NewContact contact = GenerateNewContact.createNewContact(newContact, address)
	component.createContact(contact)

	String address1New = Com.generateRandomText(6)
	String address2New = Com.generateRandomText(8)
	String cityNew = Com.generateRandomText(9)
	String stateNew = Com.generateRandomText(7)
	String countryNew = Com.generateRandomText(9)
	String postcodeNew = Com.generateRandomText(2) + (Com.generateRandomNumber(99)).toString() + " " + (Com.generateRandomNumber(9)).toString() + Com.generateRandomText(2)
	
	String[] newAddressNew = [address1New, address2New, cityNew, stateNew, countryNew, postcodeNew]
	Address addressNew = GenerateAddress.createAddress(newAddressNew)
	
	String contactFirstNameNew = Com.generateRandomText(6)
	String contactMiddleNameNew = Com.generateRandomText(11)
	String contactSurnameNew = Com.generateRandomText(8)
	String contactTelNew = Com.generateRandomNumber(999999).toString()
	String altContactTelNew = Com.generateRandomNumber(9999999).toString()
	String faxNew = Com.generateRandomNumber(9999999).toString()
	String emailNew = Com.generateRandomText(6) + "@" + Com.generateRandomText(6) + ".com"
	
	String[] newContactNew = ["Dr", contactFirstNameNew, contactMiddleNameNew, contactSurnameNew, "Architecture Now", "TestingNew", "TesterNew", contactTelNew, altContactTelNew, faxNew, "TestNew", emailNew]
	NewContact contactNew = GenerateNewContact.createNewContact(newContactNew, addressNew)
	
	String longText = "ephvjafffdephvj!afffdephvjafffde6545phvjafffdephvja£fffdephvjafff4356dephvjafffd"
	String longTextTrunc = "ephvjafffdephvj!afffdephvjafffde6545phvjafffdephvja£fffdephvjafff4356d"
	String longNum = "123456789012345678901234567890998"
	String longNumTrunc = "123456789012345678901234567890"
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Contact]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	

Rep.nextTestStep("Click onto [Inline menu] against a contacts")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.middleName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Select a title in the Title dropdown")
	gObj.buttonClickSync(Const.kip4Contacts + "dropDown_Title")
	gObj.buttonClickSubSync(Const.kip4Contacts + "select_Title", contactNew.title, "title")

	
Rep.nextTestStep("Change text in 'First Name' field")
	gObj.setEditSync(Const.kip4Contacts + "input_firstName", longText)
	String actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_firstName",  'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_firstName", contactNew.firstName)


Rep.nextTestStep("Change text in the 'Surname' field")
	gObj.setEditSync(Const.kip4Contacts + "input_lastName", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_lastName", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_lastName", contactNew.surname)

	
Rep.nextTestStep("Change text in the 'Middle Name' field")
	gObj.setEditSync(Const.kip4Contacts + "input_middleName", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_middleName", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_middleName", contactNew.middleName)
	gObj.scrollToElementSync(Const.kip4Contacts + "input_lastName")
//	WebUI.sendKeys(findTestObject(Const.kip4Contacts + "input_firstName"), Keys.chord(Keys.PAGE_DOWN))
	
	
Rep.nextTestStep("Ensure the Client new Entity picker has a value that is readonly")
	gObj.verifyAttributeSync(Const.kip4Contacts + "customerVal", 'disabled')

	
Rep.nextTestStep("Change text in the 'Position' field")
	gObj.setEditSync(Const.kip4Contacts + "input_Position", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_Position", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_Position", contactNew.position)


Rep.nextTestStep("Change text in the 'Department' field")
	gObj.setEditSync(Const.kip4Contacts + "input_department", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_department", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_department", contactNew.department)


Rep.nextTestStep("Change text in the 'Address Line 1' field")
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine1", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine1", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine1", contactNew.address.address1)


Rep.nextTestStep("Select the 'Copy from Client' button beside 'Address Line 1'")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_CopyAddCustomer")
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine1", 'value')
	gAct.compareStringAndReport(actValue, "Arch Now")
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine2", 'value')
	gAct.compareStringAndReport(actValue, "1500 Lee Way")
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine3", 'value')
	gAct.compareStringAndReport(actValue, "London")
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine5", 'value')
	gAct.compareStringAndReport(actValue, "England")
	

Rep.nextTestStep("Change text in the 'Address Line 2' field")
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine2", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine2", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine2", contactNew.address.address2)
	gObj.scrollToElementSync(Const.kip4Contacts + "input_addressLine3")
//	WebUI.sendKeys(findTestObject(Const.kip4Contacts + "input_addressLine2"), Keys.chord(Keys.PAGE_DOWN))
	

Rep.nextTestStep("Change text in the 'Address Line 3' field")
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine3", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine3", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine3", contactNew.address.city)


Rep.nextTestStep("Change text in the 'Address Line 4' field")
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine4", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine4", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine4", contactNew.address.state)


Rep.nextTestStep("Change text in the 'Address Line 5' field")
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine5", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine5", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine5", contactNew.address.country)
	gObj.scrollToElementSync(Const.kip4Contacts + "input_addressLine6")
	

Rep.nextTestStep("Change text in the 'Address Line 6' field")
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine6", longText)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_addressLine6", 'value')
	gAct.compareStringAndReport(actValue, longTextTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_addressLine6", contactNew.address.postCode)

	
Rep.nextTestStep("Change text in the 'Telephone' field")
	gObj.setEditSync(Const.kip4Contacts + "input_phone", longNum)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_phone", 'value')
	gAct.compareStringAndReport(actValue, longNumTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_phone", contactNew.telephone)


Rep.nextTestStep("Select the 'Copy from Client' button beside 'Telephone'")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_CopyTeleCustomer")
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_phone", 'value')
	gAct.compareStringAndReport(actValue, "+44 09 34 3435 343")
	

Rep.nextTestStep("Change text in the 'Mobile' field")
	gObj.setEditSync(Const.kip4Contacts + "input_mobile", longNum)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_mobile", 'value')
	gAct.compareStringAndReport(actValue, longNumTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_mobile", contactNew.mobile)


Rep.nextTestStep("Change text in the 'Fax' field")
	gObj.setEditSync(Const.kip4Contacts + "input_fax", longNum)
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_fax", 'value')
	gAct.compareStringAndReport(actValue, longNumTrunc)
	gObj.setEditSync(Const.kip4Contacts + "input_fax", contactNew.fax)
	gObj.scrollToElementSync(Const.kip4Contacts + "btn_CopyFaxCustomer")
	gAct.Wait(1)
	

Rep.nextTestStep("Select the 'Copy from Client' button beside 'Fax'")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_CopyFaxCustomer")
	actValue = gObj.getAttributeSync(Const.kip4Contacts + "input_fax", 'value')
	gAct.compareStringAndReport(actValue, "+44 09 34 3435 343")
	
	
Rep.nextTestStep("Change text in the 'Email' field")
	gObj.setEditSync(Const.kip4Contacts + "input_Email", contactNew.email)

	
Rep.nextTestStep("Click 'Save and Close'")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(3, contactNew.firstName + " " + contactNew.middleName + " " + contactNew.surname)
