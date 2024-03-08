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

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String tableName = "63f30b25-1931-4053-98a5-eec594abfc22"	
	String costCentreName = Com.generateRandomText(10)
	String costCentreCode = costCentreName.toUpperCase()
	
	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String[] newCostCentre = [costCentreName, costCentreCode, "Some notes"]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	
	String[] newCostCentreCF = [costCentreCFName, "Boolean", "Normal", "", ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)

	createCF.costCentreCustomField_CheckBox(tabName, costCentreCustomField, false)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	
	
Rep.nextTestStep("Verify checkbox status")
	gObj.elementPresentSubSync(Const.kip4CostCentre + "chkBox_Customfield_InTab", costCentreCustomField.name, "chk")
	def active = WebD.getCheckBoxState("//input[@id='cf116']")
	gAct.compareStringAndReport(active, null)
	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Navigate to Config->Custom Fields and select [Cost Centre] from the Type dropdown upper left")
	ComCF.createCustomField("Cost Centre")
	int rowNo = sVal.searchSpecificTableReturnRow(6, costCentreCFName, tableName)
	gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_specTable', tableName, "table", rowNo, "row")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Amend the 'Name' and check/uncheck 'Default Value', then Save and Close")
	gObj.buttonClickSync(Const.CustCostCentre + "chk_DefaultValue_boolean")
	gObj.buttonClickSync(Const.CustCostCentre + "button_SaveAndClose")
	
	Act.verifySavePopUpText()
	WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Navigate to [Cost Centre] via Search.Admin menu and select New ADD")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	
	
Rep.nextTestStep("Ensure Default Values reflect the changes previously made")
	gObj.elementPresentSubSync(Const.kip4CostCentre + "chkBox_Customfield_InTab", costCentreCustomField.name, "chk")
	def newActive = WebD.getCheckBoxState("//input[@id='cf116']")
	gAct.compareStringAndReport(newActive, "true")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	

Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)
	