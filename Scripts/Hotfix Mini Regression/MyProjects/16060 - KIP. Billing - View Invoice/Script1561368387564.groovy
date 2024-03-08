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


Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Project Innovation')
	

Rep.nextTestStep("Click onto [Billing]")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Billing", "MVC-PRL1-BILLING?")
	
	
Rep.nextTestStep("Within the [Invoices] section , click [Inline menu] button and select [Open]")
	gObj.buttonClickSync(Const.billing + 'a_InlineMenu')
	gObj.buttonClickSync(Const.billing + 'a_OpenInvoice')
	gAct.Wait(1)
	WebUI.acceptAlert(FailureHandling.OPTIONAL)

Rep.nextTestStep("Click through every tab within [Amend Invoice] window")
	mVal.billingAmendInvoiceWindowTabNavigation()


Rep.nextTestStep("Click [Close] or [Save and Close] Button ")
	gObj.buttonClickSync(Const.billingInvoice + 'button_Close')
	
	