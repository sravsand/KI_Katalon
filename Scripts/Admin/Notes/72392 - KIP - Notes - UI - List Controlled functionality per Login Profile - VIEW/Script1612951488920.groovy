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
import models.LoginProfile
import models.GenerateLoginProfile
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre
import models.NewContact
import models.GenerateNewContact
import models.Notes
import models.GenerateNotes
import models.Address
import models.GenerateAddress

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	component.editControlledNotesInLoginProfile("KIPAdmin", "Maintain", "true", "true")
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String noteTitle = 	Com.generateRandomText(15)
	String noteDetail = Com.generateRandomText(5) + " " + Com.generateRandomText(15) + " " + Com.generateRandomText(10)
	String[] newNotes = [noteTitle, noteDetail]
	Notes notes = GenerateNotes.createNotes(newNotes, true)
	
	String[] newAddress = ["", "", "", "", "", ""]
	Address address = GenerateAddress.createAddress(newAddress)
	
	String firstName = Com.generateRandomText(10)
	String surname = Com.generateRandomText(6)
	
	String[] newContact = ["Mr", firstName, "", surname, "PPM Solutions Inc", "", "", "", "", "", "", ""]
	NewContact contact = GenerateNewContact.createNewContact(newContact, address)
	component.createContactWithNotes(contact, notes)
	
	component.editControlledNotesInLoginProfile("KIPAdmin", "None", null, null)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [ClientGroups]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	
	
Rep.nextTestStep("Select 'New Add' located below Search query")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	

Rep.nextTestStep("Select the 'Notes' tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", "Notes", "tab")

	
Rep.nextTestStep("Click Add Note")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_AddNote")


Rep.nextTestStep("Ensure Controlled checkbox is not available or displayed")
	WebUI.verifyElementNotVisible(findTestObject(Const.kip4Notes + 'chk_Controlled'))
	gObj.buttonClickSync(Const.kip4Notes + 'button_Cancel')
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

	
Rep.nextTestStep("Ensure the 'prerequisite' added Controlled Note is NOT displayed in the table")
	int rowNo = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	gObj.selectInlineOption(1)
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", "Notes", "tab")
	
	String bodyVal = gObj.getEditTextSync(Const.kip4Contacts + "contactNotes_BodyNoData")
	gAct.compareStringAndReport(bodyVal, "No data to display")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	

Rep.nextTestStep("Return to Login Profile->CRM tab, CHECK Ability to View and Edit Controlled notes")
	component.editControlledNotesInLoginProfile("KIPAdmin", "Maintain", "true", "true")

	
Rep.nextTestStep("Return to the previously viewed Contact->Notes tab")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")


Rep.nextTestStep("Select the 'Notes' tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", "Notes", "tab")


Rep.nextTestStep("Click Add Note")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_AddNote")


Rep.nextTestStep("Ensure Controlled checkbox IS now available or displayed")
	gObj.elementVisibleSync(Const.kip4Notes + 'chk_Controlled')
	gObj.buttonClickSync(Const.kip4Notes + 'button_Cancel')
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")


Rep.nextTestStep("Ensure the 'prerequisite' added Controlled Note IS now displayed in the table")
	int rowNum = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	
	gObj.selectInlineOption(1)
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", "Notes", "tab")
	
	bodyVal = gObj.getEditTextSync(Const.kip4Contacts + "contactNotes_BodyEdit")
	gAct.findSubstringInStringAndReport(bodyVal, noteTitle + "\n" + noteDetail + "\n" + GVars.user + "\n" + todaysDate)
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Clean Up")	
	component.editControlledNotesInLoginProfile("KIPAdmin", "Maintain", "true", "true")
