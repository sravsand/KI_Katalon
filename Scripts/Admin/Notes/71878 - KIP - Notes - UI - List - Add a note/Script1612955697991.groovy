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
	String surname = Com.generateRandomText(7)
	String[] newContact = ["Mr", firstName, "", surname, "PPM Solutions Inc", "", "", "", "", "", "", ""]
	NewContact contact = GenerateNewContact.createNewContact(newContact, address)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [ClientGroups]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	

Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	

Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSync(Const.kip4Contacts + "tab_Notes")
	
	
Rep.nextTestStep("Ensure correct table column heading order")
	String headerVal = gObj.getEditTextSync(Const.kip4Contacts + "contacts_Header")
	gAct.findSubstringInStringAndReport(headerVal, "Title\nNotes\nName\nDate")
	
	
Rep.nextTestStep("Click the Add Note button")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_AddNote")

	
Rep.nextTestStep("Add a Title")
	gObj.setEditSync(Const.kip4Notes + "input_title", notes.title)

	
Rep.nextTestStep("Add a Note in the Detail textBox")

	if(GVars.browser == "Firefox") {
		WebD.clickElement("//div[@id='main-container']/div/formly-form/formly-field/formly-field-tabset/formly-tabs-tabset/div/formly-field[2]/formly-field-tab/formly-tabs-tab/formly-field/notes-table/div/app-add-note/div/formly-form/formly-field/formly-field-tabset/formly-tabs-tabset/div/formly-field/formly-field-tab/formly-tabs-tab/formly-field[4]/ng-component/div/div/formatted-text/div")
		WebD.setEditText("//div[@class='note-editable panel-body']", notes.details)
	}else {
		gObj.buttonClickSync(Const.kip4Notes + "notes_Details")
		gObj.setEditSync(Const.kip4Notes + "notes_Details", notes.details)
	}
	
	
Rep.nextTestStep("Format the Note using various Summer Notes format buttons")
	Rep.info("No need to test third party products")
/*	if(GVars.browser == "Firefox") {
		WebD.dblClickElement('//p[contains(text(),"' + notes.details + '")]')
	}else {
		WebUI.sendKeys(findTestObject(Const.kip4Notes + "notes_Details"), Keys.chord(Keys.CONTROL + "a"))
	}
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.kip4Notes + "btn_italic"))
	WebUI.click(findTestObject(Const.kip4Notes + "btn_underline"))
	gAct.Wait(1)
*/	

Rep.nextTestStep("Click Add")
	gObj.buttonClickSync(Const.kip4Notes + "button_Add")
	

Rep.nextTestStep("Ensure newly added Note is displayed in Notes table")
	String headerValNew = gObj.getEditTextSync(Const.kip4Contacts + "contactNotes_BodyEdit")

	
Rep.nextTestStep("Ensure Title, Notes, User, and Date fields reflect correct data")
	gAct.findSubstringInStringAndReport(headerValNew, notes.title + "\n" + notes.details + "\n" + GVars.user + "\n" + todaysDate)

	
Rep.nextTestStep("Click the EDIT button on the newly added record")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_EditNote")
	

Rep.nextTestStep("Close EDIT Note modal")
	gObj.buttonClickSync(Const.kip4Notes + "button_Cancel")
	

Rep.nextTestStep("Click DELETE button on newly added Note")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_DeleteNote")
	

Rep.nextTestStep("Click OK")
	gObj.buttonClickSync(Const.kip4DelNotes + "button_OK")
	
	Act.verifyDeletePopUpText()
	
	String headerValNewAlt = gObj.getEditTextSync(Const.kip4Contacts + "contactNotes_BodyNoData")
	gAct.findSubstringInStringAndReport(headerValNewAlt, "No data to display")
