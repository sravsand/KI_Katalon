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
import models.LoginGroupWithTabs
import models.GenerateLoginGroupWithTabs

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String loginGrpName = Com.generateRandomText(8)
	String loginGrpCode = loginGrpName.toUpperCase()
	
	String[] newLoginGrp = [loginGrpName, loginGrpCode, "Administration", "Activity", "Report", "All Actions", "Project", "18"]
	LoginGroupWithTabs loginGroup = GenerateLoginGroupWithTabs.createLoginGroupWithTabs(newLoginGrp)
	component.createLoginGroupWithFilters(loginGroup)
	
	String loginGrpNameNew = Com.generateRandomText(8)
	String loginGrpCodeNew = loginGrpNameNew.toUpperCase()
	
	String[] newLoginGrpNew = [loginGrpNameNew, loginGrpCodeNew, "Administration", "Activity", "", "All Actions", "Project", "18"]
	LoginGroupWithTabs loginGroupNew = GenerateLoginGroupWithTabs.createLoginGroupWithTabs(newLoginGrpNew)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Login Groups]")
	gObj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
	
	
Rep.nextTestStep("Click Add")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Add [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", loginGroupNew.name)
	
	
Rep.nextTestStep("Add [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", loginGroupNew.code)
	
	
Rep.nextTestStep("Add [ActionViews]")
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_ActionViews")
	gObj.buttonClickSync(Const.kip4GenericFilter + "btn_Add")
	
	gObj.selectComboboxValue("add", "add", loginGroupNew.actionView, loginGroupNew.actionView)

	gObj.buttonClickSync(Const.kip4LoginGroup + "btn_AddActionView")
	
	
Rep.nextTestStep("Click over [Filter] tab")
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Filters")
	
	
Rep.nextTestStep("Click over [Copy From] button")
	gObj.buttonClickSync(Const.kip4LoginGroup + "btn_CopyFrom")

	
Rep.nextTestStep("Click over [Dropdown] option within modal")
	gObj.buttonClickSync(Const.kip4LoginGroup + "dropDownFilter")
	gObj.setEditSync(Const.kip4LoginGroup + "input_Filter", loginGroup.name)


Rep.nextTestStep("Select the login group from the list used in pre-req")
	gObj.buttonClickSubSync(Const.kip4LoginGroup + "selectFilter", loginGroup.name, "filt")
	
	
Rep.nextTestStep("Click over [Copy] button within copy filters from login group modal ")
	gObj.buttonClickSync(Const.kip4LoginGroup + "btn_Copy")
	

Rep.nextTestStep("Click over [Save] button")
	gObj.buttonClickSync(Const.kip4LoginGroup + "btn_Save")


Rep.nextTestStep("Verify the toaster message to read as [Saved Successfully]")
	Act.verifySavePopUpText()
	

Rep.nextTestStep("Click over [Filters] tab")
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Filters")
	

Rep.nextTestStep("Verify the [Filters] are retained within filter table following save")
	String act = gObj.getEditTextSync(Const.kip4LoginGroup + "tab_CurrentSelectText")
	gAct.findSubstringInStringAndReport(act, loginGroup.filter)
