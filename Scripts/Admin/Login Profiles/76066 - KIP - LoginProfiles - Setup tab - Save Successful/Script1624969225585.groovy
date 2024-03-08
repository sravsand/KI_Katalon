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


Rep.nextTestStep("Enter [Name] and [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Name",loginProfile.name)
	gObj.setEditSync(Const.kip4Generic + "input_Code",loginProfile.code)
	
	
Rep.nextTestStep("Select [Setup] tab")
	gObj.scrollToElementSync(Const.kip4LoginProfile + "Tab_Setup")
	gObj.buttonClickSync(Const.kip4LoginProfile + "Tab_Setup")

	gObj.elementVisibleSync(Const.kip4LoginProfile + "label_BusinessEntities")

	
Rep.nextTestStep("Click [Save] or [Save and Close]")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()

	sVal.searchTableAddedValue(2, loginProfile.code)
	
