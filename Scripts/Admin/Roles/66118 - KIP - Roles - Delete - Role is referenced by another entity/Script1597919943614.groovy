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
import models.CostCentre
import models.GenerateCostCentre
import models.Resource
import models.GenerateResource

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String roleName = Com.generateRandomText(8)
	String roleCode = roleName.toUpperCase()
	String[] newRole = [roleName, roleCode, "KIP Dev Team 1", "Cleckheaton", "Full Time Calendar (My Region)", costCentre.name, "Developer", "Free of Charge", ""]
	Role role = GenerateRole.createNewRole(newRole)
	component.createRole(role)
	
	String resourceNotes = Com.generateRandomText(17)
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = ["Dave_" + ResourceName, "Employee", "KIP Dev Team 1", role.name, "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", "AutoTest@Workflows.com", "", "100", "", "", "", "", "", resourceNotes]
	Resource resource = GenerateResource.createNewResource(newResource)
	component.createResource(resource)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Role]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Roles")
	

Rep.nextTestStep("Click onto [Inline menu] against a role")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, role.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Delete]")
	gObj.selectInlineOption(4)
	
	
Rep.nextTestStep("Click [OK]")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK")
	
	
Rep.nextTestStep("Verify error")
	boolean modalError = gObj.elementPresentSync(Const.kip4DeleteModal + "text_DeleteMessage")
	if(modalError){
		String errtext = gObj.getEditTextSync(Const.kip4DeleteModal + "text_DeleteMessage")
		gAct.findSubstringInStringAndReport(errtext, "At least one Resource Role refers to it")
	}
	
	
Rep.nextTestStep("Click onto [Cancel] button")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_Cancel")
	
	
Rep.nextTestStep("Verify the [cost] still exists in the table")
	sVal.searchTableAddedValue(3, role.name)
	