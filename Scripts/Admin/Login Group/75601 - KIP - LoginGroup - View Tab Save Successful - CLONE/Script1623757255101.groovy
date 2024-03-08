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
	
	String[] newLoginGrp = [loginGrpName, loginGrpCode, "Administration", "Activity", "", "All Actions", "Project", "8"]
	LoginGroupWithTabs loginGroup = GenerateLoginGroupWithTabs.createLoginGroupWithTabs(newLoginGrp)
	component.createLoginGroupWithFilters(loginGroup)
	
	String loginGrpNameNew = Com.generateRandomText(8)
	String loginGrpCodeNew = loginGrpNameNew.toUpperCase()
	
	String[] newLoginGrpNew = [loginGrpNameNew, loginGrpCodeNew, "Administration", "Activity", "", "All Actions", "Project", "8"]
	LoginGroupWithTabs loginGroupNew = GenerateLoginGroupWithTabs.createLoginGroupWithTabs(newLoginGrpNew)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Login Groups]")
	gObj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an loginGroup")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, loginGroup.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		
		
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Ensure [Name] field is mandatory and prepopulated")
	String nameLab = gObj.getEditTextSync(Const.kip4LoginGroup + "label_Name")
	gAct.findSubstringInStringAndReport(nameLab, "*")
	String actVal = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal, loginGroup.name)
	
	
Rep.nextTestStep("Ensure no more than 70 characters can be entered in the [Name] field")
	String longText = "aqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Name", longText + "nm")
	
	String actVal1 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal1, longText)

	
Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Name] field")
	Rep.info("current Katalon issue")
/*	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Name"), Keys.chord(Keys.CONTROL + "a"))
	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Name"), Keys.chord(Keys.BACK_SPACE))
	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Name"), Keys.chord(Keys.TAB))
	gAct.Wait(1)
	
	WebUI.verifyElementVisible(findTestObject(Const.kip4Skills + "errNameField"))
	String errVal = WebUI.getText(findTestObject(Const.kip4Skills + "errNameField"))
	gAct.compareStringAndReport(errVal, "This field is required")
*/
	
Rep.nextTestStep("Change data in [Name] field")
	gObj.setEditSync(Const.kip4Generic + "input_Name", loginGroupNew.name)
	
		
Rep.nextTestStep("Ensure [Code] field is mandatory and Empty")
	String codeLab = gObj.getEditTextSync(Const.kip4LoginGroup + "label_Code")
	gAct.findSubstringInStringAndReport(codeLab, "*")
	String actVal12 = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actVal12, "")
	
	
Rep.nextTestStep("Ensure no more than 20 characters can be entered in the [Code] field")
	String longCodeText = "aqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Code", longCodeText + "nm")
	
	String actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actVal2, longCodeText)
	
	
Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Code] field")
	Rep.info("current Katalon issue")
/*	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Code"), Keys.chord(Keys.CONTROL + "a"))
	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Code"), Keys.chord(Keys.BACK_SPACE))
	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Code"), Keys.chord(Keys.TAB))
	gAct.Wait(1)
	
	WebUI.verifyElementVisible(findTestObject(Const.kip4LoginGroup + "errCodeField"))
	String errVal2 = WebUI.getText(findTestObject(Const.kip4LoginGroup + "errCodeField"))
	gAct.compareStringAndReport(errVal2, "This field is required")
*/	
	
Rep.nextTestStep("Add data in [Code] field")
	gObj.setEditSync(Const.kip4Generic + "input_Code", loginGroupNew.code)
	
	
//Rep.nextTestStep("Ensure [Default Action View] dropdown is mandatory")


//Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Default Action View] dropdown ")


//Rep.nextTestStep("Select option from [Default Action View] dropdown")
	
	
Rep.nextTestStep("Select Reports/Filters/Action Views/Project Levels/Notifications tabs")
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Reports")
	String act1 = gObj.getEditTextSync(Const.kip4LoginGroup + "tab_CurrentSelectText")
	gAct.findSubstringInStringAndReport(act1, loginGroup.report)
	
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Filters")
	String act2 = gObj.getEditTextSync(Const.kip4LoginGroup + "tab_CurrentSelectText")
	gAct.findSubstringInStringAndReport(act2, loginGroup.filter)
		
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_ActionViews")
	String act3 = gObj.getEditTextSync(Const.kip4LoginGroup + "tab_CurrentSelectText")
	gAct.findSubstringInStringAndReport(act3, loginGroup.actionView)
	
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_ProjectLevels")
	
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Notifications")
	String act4 = gObj.getEditTextSync(Const.kip4LoginGroup + "tab_CurrentSelectText")
	gAct.compareStringAndReport(act4, "A Resource is notified when they are assigned to a To Do Action")
	

Rep.nextTestStep("Select Logins tab")
	gObj.buttonClickSync(Const.kip4LoginGroup + "tab_Logins")
	String act5 = gObj.getEditTextSync(Const.kip4LoginGroup + "tab_CurrentSelectText")
	gAct.findSubstringInStringAndReport(act5, "No data to display")
	

Rep.nextTestStep("Click Save and Close")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	sVal.searchTableAddedValue(2, loginGroupNew.code)

