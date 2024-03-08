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
import myProjects.Action as mAct

String bwrSuffix = Act.getBrowserSuffix()

String loginName = "DLEESWF"
String proxy = "DLEES9"
String proxyLogin = "Dave Lees 9"

Rep.nextTestStep("KIP - V6 Stage Login page")

	Random rand = new Random()
	int random_num = rand.nextInt(4)

	gAct.Wait(random_num)
	
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	if(GVars.browser != "Chrome"){
		bwrSuffix = bwrSuffix + "_"
	}
	
	
Rep.nextTestStep("Click on the [Administration] icon (Horizontal lines at the top right hand side)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")


Rep.nextTestStep("Click on [Logins] under the [Security] column")
	gObj.buttonClickSync(Const.securityMenu + 'a_Logins')


Rep.nextTestStep("Select a Login name and click on the [Inline menu] e.g. MKhan")
	mAct.selectLogin(bwrSuffix + proxy, 2)


Rep.nextTestStep("Select the [System Access] tab")
	gObj.buttonClickSync(Const.kip4Login + 'tab_SystemAccess')

	
Rep.nextTestStep("Click on [Add] Button")
	gObj.buttonClickSync(Const.kip4Login + 'button_Add')
	

Rep.nextTestStep("Select your own [username] from the displayed list")
	gObj.selectComboboxValue("key", "key", "Default Login Filter", "Default Login Filter")
	gObj.selectComboboxValue("filterFieldID", "filterFieldID", "Login Code", "Login Code")
	gObj.setEditSync(Const.kip4Login + 'input_Contains_value', bwrSuffix + loginName)
	gObj.buttonClickSync(Const.kip4Login + 'button_Search')
	gObj.buttonClickSync(Const.kip4Login + 'chk_Proxy')
	gObj.buttonClickSync(Const.kip4Login + 'button_Select')
	

Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	gAct.Wait(GVars.shortWait)


//Rep.nextTestStep("Logout and log back in from the application to ensure admin changes are consolidated")
	//Act.KeyedInProjectsPPMLogout()
	//Act.KeyedInProjectsLoginToCurrentBrowserSession(GVars.usrNme, GVars.pwordEncryt)
	//gAct.Wait(GVars.shortWait)
	WebUI.refresh()

	
Rep.nextTestStep("Click on the [User Profile Settings]  (User's name displayed at the top right hand side)")
	gObj.buttonClickSync(Const.newUI + "a_ProfileSettings")


Rep.nextTestStep("Select [Login As]")
	gObj.buttonClickSync(Const.mainToolBar + "a_LoginAs")


Rep.nextTestStep("Verify the recently added [Login] is displayed in the dropdown list")
	gObj.buttonClickSync(Const.proxyLogin + 'loginAsDropDown')


Rep.nextTestStep("Select the [Login User]  i.e MKhan")
	gObj.buttonClickSubSync(Const.proxyLogin + 'select_Login', proxyLogin, "login")

	
Rep.nextTestStep("Click the [Login As] button")
	gObj.buttonClickSync(Const.proxyLogin + 'button_LoginAs')
	gAct.Wait(GVars.midWait)

	
Rep.nextTestStep("Verify that the user is now logged in as ****")
	gObj.buttonClickSync(Const.newUI + "a_ProfileSettings")


Rep.nextTestStep("Verify [Secret White Avatar] is displayed next to the Profile login name")
	gObj.elementPresentSync(Const.proxyLogin + 'loggedInAs')
	String logIn = gObj.getEditTextSync(Const.proxyLogin + 'loggedInAs')
	gAct.findSubstringInStringAndReport(logIn, proxyLogin)

