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
import models.LoginPG
import models.GenerateLoginPG
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

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
	
	String code = Com.generateRandomText(10)
	code = code.toUpperCase()
	String firstName = Com.generateRandomText(5)
	String surname = Com.generateRandomText(8)
	String name = firstName + " " + surname
	String userNme = firstName.take(1) + surname + "@workflows.com"
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = [name, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", userNme, "", "100", "", "", "", "", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)
	component.createResource(resource)
	
	String[] newLogin = [name, code, userNme, resource.code, "TEst1234","Administration", loginGroup.name]
	LoginPG login = GenerateLoginPG.createLoginPG(newLogin)
	component.createLoginWithLoginGroup(login, "Administration")
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
		
		
Rep.nextTestStep("Click over [Login Groups]")
	gObj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
		
		
Rep.nextTestStep("Click onto [Inline menu] against an loginGroup")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, loginGroup.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
			
			
Rep.nextTestStep("Click onto [delete]")
	gObj.selectInlineOption(4)
	
	
Rep.nextTestStep("Verify the warning message to read as [XYZ can not be deleted, atleast one login refers to it]")
	boolean modalError = gObj.elementPresentSync(Const.kip4DeleteModal + "text_DeleteMessage")
	if(modalError){
		String errtext = gObj.getEditTextSync(Const.kip4DeleteModal + "text_DeleteMessage")
		gAct.findSubstringInStringAndReport(errtext, loginGroup.name + " cannot be deleted:")
		gAct.findSubstringInStringAndReport(errtext, "At least one Login refers to it")
	}

	
Rep.nextTestStep("Click onto [Cancel] button")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_Cancel")
	
	
Rep.nextTestStep("Verify the [cost] still exists in the table")
	sVal.searchTableAddedValue(3, loginGroup.name)
	