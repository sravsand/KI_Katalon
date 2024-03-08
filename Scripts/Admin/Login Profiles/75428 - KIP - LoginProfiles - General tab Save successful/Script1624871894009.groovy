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

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String loginProfileName = Com.generateRandomText(8)
	String loginProfileCode = loginProfileName.toUpperCase()
	
	String[] newLoginProf = [loginProfileName, loginProfileCode]
	LoginProfile loginProfile = GenerateLoginProfile.createLoginProfile(newLoginProf)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Login Profile]")
	gObj.buttonClickSync(Const.securityMenu + "a_LoginProfiles")


Rep.nextTestStep("Select ADD")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")

	
Rep.nextTestStep("Ensure no more than 70 characters can be entered in the [Name] field")
	String longText = "aqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Name", longText + "nm")
	String actVal1 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal1, longText)
	gObj.setEditSync(Const.kip4Generic + "input_Name",loginProfile.name)
	
	
Rep.nextTestStep("Ensure no more than 20 characters can be entered in the [Code] field")
	String longCodeText = "aqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Code", longCodeText + "nm")
	
	String actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actVal2, longCodeText)
	gObj.setEditSync(Const.kip4Generic + "input_Code",loginProfile.code)

	
Rep.nextTestStep("Check [Active]")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}

	
Rep.nextTestStep("Check [Ability to set System Defaults]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_SetSystemDefaults")
	

Rep.nextTestStep("Check [Ability to set Own Projects Restriction]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_SetOwnProjectRestrictions")

	
Rep.nextTestStep("Check [Ability to set Own Activity Restrictions]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_SetOwnActivityRestrictions")


Rep.nextTestStep("Check [Ability to set Available to All]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_SetAvailableToAll")


Rep.nextTestStep("Check [Ability to use Code Converter]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_UseCodeConverter")


Rep.nextTestStep("Check [Ability to modify System Filters]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_ModifySystemFilters")


Rep.nextTestStep("Check [Ability to modify User Filters")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_ModifyUserFilters")


Rep.nextTestStep("Check [Ability to access Data Load]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_AccessDataLoad")


Rep.nextTestStep("Check [Ability to create and view Posts]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_CreateAndViewPosts")


Rep.nextTestStep("Select [Controlled Custom Field Access] dropdown and choose 'None', 'View', or 'Maintain' option")
	gObj.buttonClickSync(Const.kip4LoginProfile + "dropDown_FieldAccess")
	gObj.buttonClickSubSync(Const.kip4LoginProfile + "select_FieldAccess", "None", "val")
	
	gObj.buttonClickSync(Const.kip4LoginProfile + "dropDown_FieldAccess")
	gObj.buttonClickSubSync(Const.kip4LoginProfile + "select_FieldAccess", "View", "val")
	
	gObj.buttonClickSync(Const.kip4LoginProfile + "dropDown_FieldAccess")
	gObj.buttonClickSubSync(Const.kip4LoginProfile + "select_FieldAccess", "Maintain", "val")
	
	
Rep.nextTestStep("Check [Ability to edit layout and add widgets]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_EditlayoutAndaddwidgets")


Rep.nextTestStep("Check [Ability to Publish Custom Views]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_PublishCustomViews")


Rep.nextTestStep("Check [Ability to create Dashboards]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_CreateDashboards")


Rep.nextTestStep("Check [Ability to Publish Dashboards]")
	gObj.buttonClickSync(Const.kip4LoginProfile + "chk_PublishDashboards")


Rep.nextTestStep("Click [Save] or [Save and Close]")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()

	sVal.searchTableAddedValue(2, loginProfile.code)
	