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
import risks.Validate as rVal
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
	component.createInvoice(invoice)

	
Rep.nextTestStep("Select [Invoices] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of invoice")
	int rowNo = sVal.searchTableReturnRow(6, invoice.reference)
	String key = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 3, "col")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")


Rep.nextTestStep("Click onto [Open]")
	gObj.buttonClickSync(Const.columnPicker + "a_Open_ActionView")
	
	
Rep.nextTestStep("Click on [Authorise] button")
	gObj.buttonClickSync(Const.columnPicker + "btn_AuthoriseInvoice")
	

Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.columnPicker + "btn_CloseInvoiceModal")
	
	
Rep.nextTestStep("Verify the status of the [Invoice] Changed to [Authorized]")
	String ActiveStatus_new = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 7, "col")
	gAct.compareStringAndReport(ActiveStatus_new, "Authorised")

