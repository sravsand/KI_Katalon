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
import com.kms.katalon.core.webui.driver.DriverFactory

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
import models.Contract
import models.GenerateContract
import models.Invoice
import models.GenerateInvoice

String searchItem = "Invoices"
String searchVal = "Invoice"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String contractName = Com.generateRandomText(10)
	String reference = Com.generateRandomText(10)
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String[] newContract = ["Draft", "Project Innovation_" + GVars.selectedBrowser, contractName, "", todaysDate, false, GVars.user, "Days", "8.00", ""]
	Contract contract = GenerateContract.createContract(newContract)
	component.createContract(contract)
	
	String[] newInvoice = [todaysDate, "Project Innovation_" + GVars.selectedBrowser, "Some random notes", reference]
	Invoice invoice = GenerateInvoice.createInvoice(newInvoice)


Rep.nextTestStep("Select [Invoices] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.columnPicker + "a_Add")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Select a [Date] in [Invoice Date] section")
	WebD.switchToiFrameAndEditObject("//div[@id='ui-id-1']/iframe", "//input[@id='ctl00_phFormContent_ucInvoiceDate_txtDate']", invoice.date)
	gAct.Wait(1)
	WebUI.switchToDefaultContent()
	

Rep.nextTestStep("Search and Select [Project]")
	gObj.setEditSync(Const.columnPicker + "input_Project", invoice.project)

	
Rep.nextTestStep("Add some text in [Notes]")
	gObj.buttonClickSync(Const.addContract + 'textarea_Notes')
	gObj.setEditSync(Const.addContract + 'textarea_Notes', invoice.notes)	
	gObj.buttonClickSync(Const.columnPicker + "tab_AccountInfo")
	gObj.setEditSync(Const.columnPicker + "input_Reference", invoice.reference)
	

Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAmpClose")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Verify the recently added Invoice details are visible in table")
	sVal.searchTableAddedValue(6, invoice.reference)
	
