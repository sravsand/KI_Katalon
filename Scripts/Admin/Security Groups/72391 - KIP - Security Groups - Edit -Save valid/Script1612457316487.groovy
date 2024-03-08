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
import models.Role
import models.GenerateRole
import models.SecurityGroup
import models.GenerateSecurityGroup

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String securityGroupName = Com.generateRandomText(8)
	String securityGroupCode = securityGroupName.toUpperCase()
	
	String[] newSecurityGroup = [securityGroupName, securityGroupCode]
	SecurityGroup securityGroup = GenerateSecurityGroup.createSecurityGroup(newSecurityGroup)
	component.createSecurityGroup(securityGroup)
	
	String securityGroupNameNew = Com.generateRandomText(8)
	String securityGroupCodeNew = securityGroupNameNew.toUpperCase()
	
	String[] newSecurityGroupEdit = [securityGroupNameNew, securityGroupCodeNew]
	SecurityGroup securityGroupEdit = GenerateSecurityGroup.createSecurityGroup(newSecurityGroupEdit)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Security Group]")
	gObj.buttonClickSync(Const.securityMenu + "a_SecurityGroups")

	
Rep.nextTestStep("Click onto [Inline menu] against an Location")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, securityGroup.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Edit]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Click [Resources/Projects/Clients/Logins] tabs to ensure functionality")
	gObj.buttonClickSync(Const.kip4SecGroup + "div_Resources")
	gObj.buttonClickSync(Const.kip4SecGroup + "div_Projects")
	gObj.buttonClickSync(Const.kip4SecGroup + "div_Customers")
	gObj.buttonClickSync(Const.kip4SecGroup + "div_Logins")
	

Rep.nextTestStep("Ensure [Login] tab is available and unpopulated on FIRST edit.")
//	gObj.elementPresentSync(Const.kip4SecGroup + "body_Login")
	String loginText = gObj.getEditTextSync(Const.kip4SecGroup + "body_Login")
	gAct.compareStringAndReport(loginText, "No data to display")
	

Rep.nextTestStep("Attempt to change [Code] (Required)")
	gObj.buttonClickSync(Const.kip4SecGroup + "div_General")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')

	
Rep.nextTestStep("Change data in any of the fields")
	gObj.setEditSync(Const.kip4Generic + "input_Name", securityGroupEdit.name)
	

Rep.nextTestStep("Click [save] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()


Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(3, securityGroupEdit.name)

