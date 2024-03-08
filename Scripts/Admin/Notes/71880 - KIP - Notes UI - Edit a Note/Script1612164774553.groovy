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
import models.Notes
import models.GenerateNotes
import models.Address
import models.GenerateAddress

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String noteTitle = 	Com.generateRandomText(15)
	String noteDetail = Com.generateRandomText(5) + " " + Com.generateRandomText(15) + " " + Com.generateRandomText(10)
	String[] newNotes = [noteTitle, noteDetail]
	Notes notes = GenerateNotes.createNotes(newNotes, false)
	
	String[] newAddress = ["", "", "", "", "", ""]
	Address address = GenerateAddress.createAddress(newAddress)
	
	String firstName = Com.generateRandomText(10)
	String surname = Com.generateRandomText(6)
	
	String[] newContact = ["Mr", firstName, "", surname, "PPM Solutions Inc", "", "", "", "", "", "", ""]
	NewContact contact = GenerateNewContact.createNewContact(newContact, address)
	component.createContactWithNotes(contact, notes)

	String newNoteTitle = Com.generateRandomText(15)
	String newNoteDetail = Com.generateRandomText(5) + " " + Com.generateRandomText(15) + " " + Com.generateRandomText(10)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [ClientGroups]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an NominalCodes")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	
	int rowNo = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	

Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSync(Const.kip4Contacts + "tab_Notes")

	
Rep.nextTestStep("Ensure correct table column heading order")
	String headerVal = gObj.getEditTextSync(Const.kip4Contacts + "contacts_HeaderEdit")
	gAct.findSubstringInStringAndReport(headerVal, "Title\nNotes\nName\nDate")
	
	
Rep.nextTestStep("Ensure each note listed has correct EDIT and DELETE buttons on right side of EVERY note")
	gObj.elementVisibleSync(Const.kip4Contacts + "btn_EditNote")
	gObj.elementVisibleSync(Const.kip4Contacts + "btn_DeleteNote")


Rep.nextTestStep("Click the EDIT button, second from right, on the Note record of choice")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_EditNote")


Rep.nextTestStep("EDIT Title")
	gObj.setEditSync(Const.kip4Notes + "input_title", newNoteTitle)


Rep.nextTestStep("EDIT Text in Detail Summer Notes TextBox")

	if(GVars.browser == "Firefox") {
		WebD.clickElement("//div[@class='note-editable panel-body']")
		WebD.setEditText("//div[@class='note-editable panel-body']", newNoteDetail)
		WebD.dblClickElement('//p[contains(text(),"' + notes.details + newNoteDetail + '")]')
	}else {	
		gObj.buttonClickSync(Const.kip4Notes + "input_EditNotesDetails")
		gObj.clearSetEditSync(Const.kip4Notes + "input_DetailsAlt", newNoteDetail)
	}


Rep.nextTestStep("Click Save")
	gObj.buttonClickSync(Const.kip4Notes + "btn_Save")


Rep.nextTestStep("Ensure date in Date column for edited note reflects DateTime of most recent change")
	String bodyVal = gObj.getEditTextSync(Const.kip4Contacts + "contactNotes_BodyEdit")
	gAct.findSubstringInStringAndReport(bodyVal, newNoteTitle + "\n" + newNoteDetail + "\n" + GVars.user + "\n" + todaysDate)


Rep.nextTestStep("Click Save and Close")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	

Rep.nextTestStep("Open most recently edited Note")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	
	int rowNum = sVal.searchTableReturnRow(3, contact.firstName + " " + contact.surname)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gObj.selectInlineOption(1)
	

Rep.nextTestStep("Ensure most recently edited note remains")
	gObj.buttonClickSync(Const.kip4Contacts + "tab_Notes")
	String bodyValNew = gObj.getEditTextSync(Const.kip4Contacts + "contactNotes_BodyEdit")
	gAct.findSubstringInStringAndReport(bodyValNew, newNoteTitle + "\n" + newNoteDetail + "\n" + GVars.user + "\n" + todaysDate)
