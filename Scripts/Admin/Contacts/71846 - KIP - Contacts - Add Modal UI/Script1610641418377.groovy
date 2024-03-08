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
import buildingBlocks.createComponentsLogedIn as component
import models.Contact
import models.GenerateContact

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String contactFirstName = Com.generateRandomText(6)
	String contactSurname = Com.generateRandomText(8)
	int contactTel = Com.generateRandomNumber(999999)
	String altContactTel = Com.generateRandomNumber(9999999).toString()
	
	String[] newContact = [contactFirstName, contactSurname, "PPM Solutions Inc", "Testing", "Tester", contactTel]
	Contact contact = GenerateContact.createContact(newContact)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Contact]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Contacts")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Click 'x' in top right of modal.")
	gObj.buttonClickSync(Const.kip4Contacts + "btn_X_Close")


Rep.nextTestStep("Click 'Close'")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Click 'Close' after edit")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	gObj.setEditSync(Const.kip4Contacts + "input_firstName", contact.firstName)
	gObj.setEditSync(Const.kip4Contacts + "input_lastName", contact.surname)
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click 'X' after edit")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	gObj.setEditSync(Const.kip4Contacts + "input_firstName", contact.firstName)
	gObj.setEditSync(Const.kip4Contacts + "input_lastName", contact.surname)
	gObj.buttonClickSync(Const.kip4Contacts + "btn_X_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")

	