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
	
	String[] newSecurityGroupClone = [securityGroupNameNew, securityGroupCodeNew]
	SecurityGroup securityGroupClone = GenerateSecurityGroup.createSecurityGroup(newSecurityGroupClone)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Security Group]")
	gObj.buttonClickSync(Const.securityMenu + "a_SecurityGroups")

	
Rep.nextTestStep("Click onto [Inline menu] against an Location")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, securityGroup.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Ensure the [Name] field is pre-populated with data from the cloned [Security Group] and/or edit data (Required)")
	String actName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actName, securityGroup.name)
	gObj.setEditSync(Const.kip4Generic + "input_Name", securityGroupClone.name)
	

Rep.nextTestStep("Ensure the [Code] field is empty and enter new valid [Code] (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", securityGroupClone.code)


Rep.nextTestStep("Ensure the [Active] checkbox is checked/unchecked from the cloned [Security Group].")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}

	
Rep.nextTestStep("Ensure each tabbed [Security Group]'s details are cloned")


Rep.nextTestStep("Click [save] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, securityGroupClone.code)

