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
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	
	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String[] newCostCentreCF = [costCentreCFName, "Float", "Normal", "", ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)

	createCF.costCentreCustomField_float(tabName, costCentreCustomField)
	
	String newCostCentreCFName = Com.generateRandomText(10)
	String[] newCostCentreCF_Int = [newCostCentreCFName, "Integer", "Normal", "", ""]
	CostCentreCustomField costCentreCustomFieldInt = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF_Int)
	createCF.costCentreCustomFieldExistingTab_Int(tabName, costCentreCustomFieldInt)
	
	String costCentreName = Com.generateRandomText(10)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, "We have some notes"]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	component.costCentreWithCustomField(costCentre, tabName, "")
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an Cost centre")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, costCentre.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	
	
Rep.nextTestStep("Click onto [Edit]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	
	
Rep.nextTestStep("Attempt to enter alphanumeric characters")
	String floatErrInput = 'abcdef1234'
	gObj.setEditSync(Const.kip4CostCentre + "input_Customfield", floatErrInput)
	gObj.buttonClickSync(Const.kip4CostCentre + "input_Customfield2")
	boolean floatVis = gObj.elementVisibleSync(Const.kip4CostCentre + "errorFloat_CF")
	if(floatVis){
		String floatErr = gObj.getEditTextSync(Const.kip4CostCentre + "errorFloat_CF")
		gAct.compareStringAndReport(floatErr, '"' + floatErrInput + '"' + " Is Invalid")
	}
	
	
Rep.nextTestStep("If [Custom Field] is of DataType Integer, attempt to enter a decimal value")
	String intErrInput = '112.55'
	gObj.setEditSync(Const.kip4CostCentre + "input_Customfield2", intErrInput)	
	gObj.buttonClickSync(Const.kip4CostCentre + "input_Customfield")
	boolean intVis = gObj.elementVisibleSync(Const.kip4CostCentre + "errorIntegerCF")
	if(intVis){
		String intErr = gObj.getEditTextSync(Const.kip4CostCentre + "errorIntegerCF")
		gAct.compareStringAndReport(intErr, '"' + intErrInput + '"' + " Is Invalid")
	}
	
	gObj.setEditSync(Const.kip4CostCentre + "input_Customfield", "199.99")
	gObj.setEditSync(Const.kip4CostCentre + "input_Customfield2", "199")
	
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	
	
Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)

	ComCF.cleanUpCostCentreCustomfields(newCostCentreCFName, tableName)

	