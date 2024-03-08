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

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
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
	
	String clientGroupName2 = Com.generateRandomText(10)
	String clientGroupCode2 = clientGroupName2.toUpperCase()
	
	String[] newClientGroup2 = [clientGroupName2, clientGroupCode2]
	ClientGroup clientGroup2 = GenerateClientGroup.createClientGroup(newClientGroup2)
	component.createClientGroup(clientGroup2)
	
	String clientName = Com.generateRandomText(10)
	String clientCode = clientName.toUpperCase()
	
	def newClient = [clientName, clientCode, address, contactTel, "", "", "", clientGroup.name]
	Customer client = GenerateCustomer.createCustomerKIP4(newClient)
	component.createClientKIP4(client)
	
	String newClientName = Com.generateRandomText(10)
	String newClientCode = newClientName.toUpperCase()
	
	def newClonedClient = [newClientName, newClientCode, address, contactTel, "", "", "", clientGroup2.name]
	Customer cloneClient = GenerateCustomer.createCustomerKIP4(newClonedClient)

	
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


Rep.nextTestStep("[Name] field is mandatory.")
	String actVal = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal, client.name)

	
Rep.nextTestStep("[Name] field has a character limit of 70 characters.")
	String longText = "aqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Name", longText + "nm")
	
	String actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal2, longText)

	
Rep.nextTestStep("[Name] field can accept entry of any text/numbers/symbols.")
	String altText = "absf125![]'#"
	gObj.setEditSync(Const.kip4Generic + "input_Name", altText)
	
	actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal2, altText)

	
Rep.nextTestStep("Amend [Name] value")
	gObj.setEditSync(Const.kip4Generic + "input_Name", cloneClient.name)

	
Rep.nextTestStep("[Code] field is populated and cannot be edited.")
	String actCode = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actCode, client.code)
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')

	
Rep.nextTestStep("CHECK/UNCHECK [Active] checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(active, "true")
	
	gObj.buttonClickSync(Const.kip4Customer + "chk_Active_edt")
	active = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(active, null)
	
	gObj.checkSync(Const.kip4Customer + "chk_Active_edt")

	
Rep.nextTestStep("CHECK/UNCHECK [Agency] checkbox")
	def agency = WebD.getCheckBoxState("//input[@id='agency']")
	gAct.compareStringAndReport(agency, null)
	
	gObj.buttonClickSync(Const.kip4Customer + "chk_Agency")
	agency = WebD.getCheckBoxState("//input[@id='agency']")
	gAct.compareStringAndReport(agency, "true")
	gObj.checkSync(Const.kip4Customer + "chk_Agency")
	

Rep.nextTestStep("Select [Customer Group] dropdown and choose a [Customer Group] ")
	gObj.selectComboboxValue("clientGroup", "clientGroup", client.customerGroup, client.customerGroup)
	
	
Rep.nextTestStep("Enter/Amend value in [Address Line 1] field ")
	gObj.scrollToElementSync(Const.kip4Customer + "input_addressLine1")
	gObj.setEditSync(Const.kip4Customer + "input_addressLine1", cloneClient.address.address1)

	
Rep.nextTestStep("Enter/Amend value in [Address Line 2] field ")
	gObj.setEditSync(Const.kip4Customer + "input_addressLine1", cloneClient.address.address2)

	
Rep.nextTestStep("Enter/Amend value in [City] field ")
	gObj.setEditSync(Const.kip4Customer + "input_addressLine3", cloneClient.address.city)
	
	
Rep.nextTestStep("Enter/Amend value in [State] field ")
	gObj.setEditSync(Const.kip4Customer + "input_addressLine4", cloneClient.address.state)

	
Rep.nextTestStep("Enter/Amend value in [Country] field ")
	gObj.setEditSync(Const.kip4Customer + "input_addressLine5", cloneClient.address.country)


Rep.nextTestStep("Enter/Amend value in [Zip Code] field ")
	gObj.setEditSync(Const.kip4Customer + "input_addressLine6", cloneClient.address.postCode)

	
Rep.nextTestStep("Enter/Amend value in [Telephone] field ")
	gObj.scrollToElementSync(Const.kip4Customer + "input_phone")
	gObj.setEditSync(Const.kip4Customer + "input_phone", cloneClient.telephone)

	
Rep.nextTestStep("Enter/Amend value in [Fax] field ")
	gObj.setEditSync(Const.kip4Customer + "input_fax", cloneClient.fax)
	

Rep.nextTestStep("Enter/Amend value in [Website] field ")
	gObj.setEditSync(Const.kip4Customer + "input_website", cloneClient.website)


Rep.nextTestStep("Enter/Amend value in [Email] field ")
	gObj.setEditSync(Const.kip4Customer + "input_email", cloneClient.email)


Rep.nextTestStep("Data entry in other tabs is optional")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Notes")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Contacts")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Account")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Projects")
	gObj.buttonClickSync(Const.kip4Customer + "tab_SecurityGroups")
	gObj.buttonClickSync(Const.kip4Customer + "tab_Audit")
	

Rep.nextTestStep("NB. Modal MAY contain [Status Fields]")


Rep.nextTestStep("Click Save and Close")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	

Rep.nextTestStep("Verify the changes are updated accurately on the Client Group table")
	sVal.searchTableAddedValue(3, cloneClient.name)


