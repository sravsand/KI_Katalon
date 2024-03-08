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
	
	String clientName = Com.generateRandomText(10)
	String clientCode = clientName.toUpperCase()
	
	def newClient = [clientName, clientCode, address, contactTel, "", "", "", clientGroup.name]
	Customer client = GenerateCustomer.createCustomerKIP4(newClient)
	component.createClientKIP4(client)
	
	String newClientName = Com.generateRandomText(10)
	String newClientCode = newClientName.toUpperCase()
	
	def newClonedClient = [newClientName, newClientCode, address, contactTel, "", "", "", clientGroup.name]
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
	
	
Rep.nextTestStep("Click onto [Clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Amend [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", cloneClient.name)
	
	
Rep.nextTestStep("Verify User is able to add [Code] (Code not auto generated)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", cloneClient.code)
	
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	
Rep.nextTestStep("Verify the Toaster message text to read as [Saved Successfully]")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify recently added [Client] is available for selection in Client table")
	sVal.searchTableAddedValue(3, cloneClient.name)
	