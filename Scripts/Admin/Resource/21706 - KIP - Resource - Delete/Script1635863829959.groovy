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
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

String searchItem = "Resources"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String resourceNotes = Com.generateRandomText(17)
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = ["Dave_" + ResourceName, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", "AutoTest@Workflows.com", "", "100", "", "", "", "", "", resourceNotes]
	Resource resource = GenerateResource.createNewResource(newResource)
	component.createResource(resource)


Rep.nextTestStep("Select [Resources] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of an Resources")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gObj.buttonClickSync(Const.columnPicker + "previousPage")
	int rowNo = sVal.searchTableReturnRow(3, resource.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

	
Rep.nextTestStep("Click onto [Delete]")
	gObj.selectInlineOption(4)
	
	
Rep.nextTestStep("Click OK")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK")

	Act.verifyDeletePopUpText()

	
Rep.nextTestStep("Verify that the deleted expense type is not available for selection")
	sVal.searchTableDeletedValue(3, resource.name)

