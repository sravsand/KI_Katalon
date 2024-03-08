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
import models.LoginProfile
import models.GenerateLoginProfile

String searchItem = "Login Profiles"
String searchVal = "Login Profile"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String loginProfileName = Com.generateRandomText(8)
	String loginProfileCode = loginProfileName.toUpperCase()
	
	String newLoginProfileName = Com.generateRandomText(8)
	String newLoginProfileCode = newLoginProfileName.toUpperCase()
	
	String[] newLoginProf = [loginProfileName, loginProfileCode]
	LoginProfile loginProfile = GenerateLoginProfile.createLoginProfile(newLoginProf)
	component.createLoginProfile(loginProfile)

	
Rep.nextTestStep("Select [Login Profiles] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	
Rep.nextTestStep("Click onto [Inline menu] of a Login Profiles")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, loginProfile.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Code convert]")
	gObj.selectInlineOption(3)
	
	
Rep.nextTestStep("Enter code in [New Code] textbox")
	gObj.setEditSync(Const.kip4CodeConverter + "input_newCode", newLoginProfileCode)
	
	
Rep.nextTestStep("Click on [Convert]")
	gObj.buttonClickSync(Const.kip4CodeConverter + "btn_Convert")
	Act.verifyPopUpText("Code Converted successfully")


Rep.nextTestStep("Verify that the changes have been updated in expense type table")
	sVal.searchTableAddedValue(2, newLoginProfileCode)

