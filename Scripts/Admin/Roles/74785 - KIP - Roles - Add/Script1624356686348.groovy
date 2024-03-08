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

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "MMMM yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String newDate = gAct.increaseDate(todaysDate, pattern, 31, pattern)
	
	String roleTempName = Com.generateRandomText(8)
	String roleTempCode = roleTempName.toUpperCase()
	String[] newTempRole = [roleTempName, roleTempCode, "", "", "", "", "", "", ""]
	Role roleTemp = GenerateRole.createNewRole(newTempRole)
	component.createRoleTemplate(roleTemp)
	
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String roleName = Com.generateRandomText(8)
	String roleCode = roleName.toUpperCase()
	String[] newRole = [roleName, roleCode, "KIP Dev Team 1", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Developer", "Free of Charge", ""]
	Role role = GenerateRole.createNewRole(newRole)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Role]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Roles")
	
	
Rep.nextTestStep("Click over [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")

	
Rep.nextTestStep("Select [Add an empty xRole] radio button")
	gObj.buttonClickSync(Const.kip4Roles + "radio_AddEmpty")


Rep.nextTestStep("Click over [Continue] button")
	gObj.buttonClickSync(Const.kip4Roles + "btn_Continue")

	
Rep.nextTestStep("Fill in [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", role.name)
	

Rep.nextTestStep("Fill in [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", role.code)
	

Rep.nextTestStep("Verify the [Active] checkbox is pre-selected")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}

	
Rep.nextTestStep("Select [Working Time]")
	gObj.buttonClickSync(Const.kip4Roles + "dropDown_WorkingTime")
	gObj.buttonClickSync(Const.kip4Roles + "delete_WorkingTime")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_WorkingTime", role.workingTime, "wt")


Rep.nextTestStep("Click over [Financial] tab")
	gObj.buttonClickSync(Const.kip4Roles + "tab_Financial")
	

Rep.nextTestStep("Select [Charge (Standard)] from dropdown")
	gObj.selectComboboxValue("defaultStandardCost","defaultStandardCost", role.cost, role.cost)

	
Rep.nextTestStep("Select [Cost (Standard)] from dropdown")
	gObj.selectComboboxValue("defaultStandardCost","defaultStandardCost", role.cost, role.cost)


Rep.nextTestStep("Click over [Add Planning Rate] button ")
	gObj.buttonClickSync(Const.kip4Roles + "btn_AddPlanningRate")
	

Rep.nextTestStep("Select [Effective From] from dropdown")
	String num = "1"
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_EffectiveFrom",  num, "pos")
	gObj.setEditSubSync(Const.kip4Roles + "input_EffectiveFrom", num, "pos", todaysDate)
	

Rep.nextTestStep("Choose [Currency]")
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_Currency", num, "pos")
	gObj.setEditSubSync(Const.kip4Roles + "input_Currency", num, "pos", "DEFAULT")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_Currency", "Default Currency", "curr")

	
Rep.nextTestStep("Add [Cost]")
	gObj.setEditSubSync(Const.kip4Roles + "input_localCostRate", num, "pos", "5")
	

Rep.nextTestStep("Add [Charge]")
	gObj.setEditSubSync(Const.kip4Roles + "input_localChargeRate", num, "pos", "2.5")
	

Rep.nextTestStep("Click over [Add Planning Rate] button and add 2nd planning rate")
	gObj.buttonClickSync(Const.kip4Roles + "btn_AddPlanningRate")


Rep.nextTestStep("Select [Effective From] from dropdown")
	num = "2"
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_EffectiveFrom", num, "pos")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_EffectiveFrom", newDate, "mth")


Rep.nextTestStep("Choose [Currency]")
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_Currency", num, "pos")
	gObj.setEditSubSync(Const.kip4Roles + "input_Currency", num, "pos", "DEFAULT")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_Currency", "Default Currency", "curr")


Rep.nextTestStep("Add [Cost]")
	gObj.setEditSubSync(Const.kip4Roles + "input_localCostRate", num, "pos", "3")

	
Rep.nextTestStep("Add [Charge]")
	gObj.setEditSubSync(Const.kip4Roles + "input_localChargeRate", num, "pos", "1.5")


Rep.nextTestStep("Click over [Security Group] tab")
	gObj.buttonClickSync(Const.kip4Roles + "tab_SecurityGroups")
	

Rep.nextTestStep("Select [ This Role is available to all xSecurity Groups] checkbox")
	gObj.buttonClickSync(Const.kip4Roles + "chk_AvailableToAllSecurityGroups")
	

Rep.nextTestStep("Click over [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	
Rep.nextTestStep("Verify the toaster message text as [Saved Successfully]")
	Act.verifySavePopUpText()
	

Rep.nextTestStep("Verify the newly added role is availble for selection in Role table")
	sVal.searchTableAddedValue(2, role.code)

