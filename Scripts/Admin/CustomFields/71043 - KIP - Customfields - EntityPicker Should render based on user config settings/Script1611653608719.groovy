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
import models.CostCentreCustomField
import models.GenerateCostCentreCustomField
import models.CostCentre
import models.GenerateCostCentre
import customfields.Create as createCF
import customfields.Common as ComCF
import models.Activity
import models.GenerateActivity
import models.Project
import models.GenerateProject

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
			
	String activityName = Com.generateRandomText(10)
	String activityCode = activityName.toUpperCase()
	
	String[] newActivity = [activityName, activityCode]
	Activity activity = GenerateActivity.createActivity(newActivity)
	component.createActivity(activity)
	
	String tableName = "63f30b25-1931-4053-98a5-eec594abfc22"

	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	String allowedName = Com.generateRandomText(7)
	
	String[] newCostCentreCF = [costCentreCFName, "Activity", "Normal", "", ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)
	createCF.costCentreCustomField_EntityPicker(tabName, costCentreCustomField, true)
		
	String costCentreName = Com.generateRandomText(10)
	String costCentreCode = costCentreName.toUpperCase()
	
	String[] newCostCentre = [costCentreName, costCentreCode, "We have some notes"]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	component.costCentreWithEntityPicker(costCentre, tabName, activity.name)
	gAct.Wait(1)

	gObj.buttonClickSync(Const.mainToolBar + "settings")
	gObj.buttonClickSync(Const.settingsMenu + "DisplaySettings")
	gObj.selectComboByLabelSync(Const.DisplaySettings + "select_AutoSearch", "Show Name & Code")
	gObj.buttonClickSync(Const.DisplaySettings + "btn_SaveAndClose")
	Act.verifySavePopUpText()
	WebUI.refresh()
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	

Rep.nextTestStep("Click onto [Inline menu] against an Cost centre")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, costCentre.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")

	
Rep.nextTestStep("Select EntityPicker dropdown")
	gObj.buttonClickSync(Const.kip4CostCentre + "dropDown_CFKeywordExisitngValue")
	gObj.buttonClickSync(Const.kip4CostCentre + "clearEntityPicker")
	gObj.setEditSync(Const.kip4CostCentre + "input_CustomfieldExistingVal", activity.name)
	gObj.elementPresentSubSync(Const.kip4CostCentre + "select_EntityPicker",  activity.name + " | " + activity.code, "value")
	
	
Rep.nextTestStep("Close modal")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	
	
Rep.nextTestStep("Navigate to Config->Display Settings")
	gObj.buttonClickSync(Const.mainToolBar + "settings")
	gObj.buttonClickSync(Const.settingsMenu + "DisplaySettings")
	
	
Rep.nextTestStep("Change Data Entry Forms Auto-Search to 'Show Names Only' and Save and Close")
	gAct.Wait(1)
	gObj.selectComboByLabelSync(Const.DisplaySettings + "select_AutoSearch", "Show Names Only")
	gObj.buttonClickSync(Const.DisplaySettings + "btn_SaveAndClose")
	Act.verifySavePopUpText()
	gObj.refreshUI()
	
	
//Rep.nextTestStep("Logout and log back in")
	//Act.KeyedInProjectsPPMLogout()
	//WebUI.closeBrowser()
	//Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Navigate to [Cost Centre] via the Search option/Admin menu")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Select New ADD")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")

	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	

Rep.nextTestStep("Select EntityPicker dropdown")
	gObj.buttonClickSync(Const.kip4CostCentre + "dropDown_CFKeyword")
	gObj.setEditSync(Const.kip4CostCentre + "input_CustomfieldExistingVal", activity.name)
	gObj.elementPresentSubSync(Const.kip4CostCentre + "select_EntityPicker", activity.name, "value")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
			
Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)
