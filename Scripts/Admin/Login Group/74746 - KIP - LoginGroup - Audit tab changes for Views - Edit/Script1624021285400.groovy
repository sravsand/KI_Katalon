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
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String loginGrpName = Com.generateRandomText(8)
	String loginGrpCode = loginGrpName.toUpperCase()
	
	String[] newLoginGrp = [loginGrpName, loginGrpCode, "Administration", "Activity", "Report", "All Actions", "Project", "8"]
	LoginGroupWithTabs loginGroup = GenerateLoginGroupWithTabs.createLoginGroupWithTabs(newLoginGrp)
	component.createLoginGroupWithFilters(loginGroup)
	def now = new Date()
	String currTime = now.format("hh:mm")
	
	String loginGrpNameNew = Com.generateRandomText(8)
	String loginGrpCodeNew = loginGrpNameNew.toUpperCase()
	
	String[] newLoginGrpNew = [loginGrpNameNew, loginGrpCodeNew, "Administration", "Activity", "", "All Actions", "Project", "8"]
	LoginGroupWithTabs loginGroupNew = GenerateLoginGroupWithTabs.createLoginGroupWithTabs(newLoginGrpNew)
	
	//uncheck enabled combined resources checkbox
	gObj.buttonClickSync(Const.mainToolBar + "settings")
	gObj.buttonClickSync(Const.settingsMenu + "PlanningSettings")
	
	gObj.scrollToElementSync(Const.planningSettings + "chk_EnableCombinedResourcing")
	def enabled = WebD.getCheckBoxState("//input[@id='EnableCombinedResourcing']")

	if(enabled == "true"){
		gObj.checkSync(Const.planningSettings + "chk_EnableCombinedResourcing")
		gObj.buttonClickSync(Const.planningSettings + "button_SaveandClose")
	}else {
		gObj.buttonClickSync(Const.planningSettings + "btn_Close")
	}

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Login Groups]")
	gObj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an loginGroup")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, loginGroup.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		
		
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify the header title read as [Edit Login Group]")
	int cnter = 0
	String headerVal = gObj.getEditTextSync(Const.kip4Generic + "header_ModalTitle")
	while((headerVal == "Loading") & (cnter < 5)) {
		gAct.Wait(1)
		cnter ++
		headerVal = gObj.getEditTextSync(Const.kip4Generic + "header_ModalTitle")
	}
	gAct.compareStringAndReport(headerVal, "Edit Login Group")
	

Rep.nextTestStep("Verify the entity [Name] & [Code] is displyaing accurately under modal header")
	String actVal1 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal1, loginGroup.name)
	String actVal12 = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actVal12, loginGroup.code)
	

Rep.nextTestStep("Verify the [Name], [Date] & [Time] of the previous user successfully saved changes  is visible at the footer of the modal ")
	String actVal3 = gObj.getEditTextSync(Const.kip4LoginGroup + "userDetails")
	gAct.findSubstringInStringAndReport(actVal3, todaysDate)
	gAct.findSubstringInStringAndReport(actVal3, GVars.user)
//	gAct.findSubstringInStringAndReport(actVal3, currTime)
		
	
Rep.nextTestStep("Select/Deselect [MyWork] checkbox")
	gObj.buttonClickSync(Const.kip4LoginGroup + "chk_MyWork")
	gObj.buttonClickSync(Const.kip4LoginGroup + "chk_MyWork")
	

Rep.nextTestStep("Select [Resourcing] checkbox")
	gObj.buttonClickSync(Const.kip4LoginGroup + "chk_Resourcing")
	WebUI.verifyElementNotVisible(findTestObject(Const.kip4LoginGroup + "dropDown_ResourceMode"))
	gObj.buttonClickSync(Const.kip4LoginGroup + "chk_Resourcing")

	
Rep.nextTestStep("Select [Scheduling] from [Resourcing Mode] dropdown")
	gObj.buttonClickSync(Const.kip4LoginGroup + "dropDown_ResourceMode")
	gObj.buttonClickSync(Const.kip4LoginGroup + "select_Scheduling")


Rep.nextTestStep("Click [Save] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Save")
	Act.verifySavePopUpText()
	

Rep.nextTestStep("Click onto [Audit] tab")
	gObj.scrollToElementSync(Const.kip4LoginGroup + "tab_Audit")
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Audit")


Rep.nextTestStep("Notice all changes are being recorded at Data level")
	gObj.buttonClickSync(Const.kip4LoginGroup + "a_Today")
	String auditText = gObj.getEditTextSync(Const.kip4LoginGroup + "auditEntryText")
	gAct.findSubstringInStringAndReport(auditText, loginGroup.code + " inserted")
	gAct.findSubstringInStringAndReport(auditText, "Resourcing changed from Strategic to Scheduling")


	