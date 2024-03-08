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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Activity
import models.GenerateActivity
import excel.Action as eAct
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre
import models.Skill
import models.GenerateSkill

String searchItem = "Resources"
String searchVal = "Resource Skill"

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\KIP_ResourceSkill_DataLoad.xls"
gAct.deleteFile(downloadPath)

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String skillName = Com.generateRandomText(10)
	String skillCode = skillName.toUpperCase()
	
	String[] newSkill = [skillName, skillCode, "1", "5"]
	Skill skill = GenerateSkill.createSkill(newSkill)
	component.createSkill(skill)
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = ["Dave_" + ResourceName, "Employee",  "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", "AutoTest@Workflows.com", "", "100", "CST001", "CST002", "CHG001", "CHG001", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)
	String resourceCode = component.createResource(resource)

	
Rep.nextTestStep("Select [Resources] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click On [Create Skill Data Template]")
	gObj.buttonClickSync(Const.dataLoad + "a_Create Skills Data Template")
	
	
Rep.nextTestStep("Click on [Generate Template Spreadsheet]")
	gObj.buttonClickSync(Const.dataLoad + "button_Generate Template Spreadsheet")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "button_Close")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click [Open]")
	def ExportData = eAct.openAndReadFile(downloadPath, searchVal)
	
	
Rep.nextTestStep("Verify the spreadsheet have all the columns selected at Step 6")
	String colOne = ExportData.getValue(2, 10)
	gAct.compareStringAndReport(colOne, "Resource Code")
	String colTwo = ExportData.getValue(3, 10)
	gAct.compareStringAndReport(colTwo, "Skill Code")
	String colThree = ExportData.getValue(4, 10)
	gAct.compareStringAndReport(colThree, "Level")
	
	
Rep.nextTestStep("Fill the [Mandatory] fields in spreadsheet")
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 1, resourceCode)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 2, skill.name)
	eAct.writeToCellInExcelXLS(downloadPath, searchVal, 10, 3, "1")
	
	
Rep.nextTestStep("Save and Close the spreadsheet")
	Rep.info("Spreadsheet not opened directly")
	WebUI.refresh()
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Import Skill Data]")
	gObj.buttonClickSync(Const.dataLoad + "a_Import Skills Data")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click on [Browse]")
	WebUI.focus(findTestObject(Const.columnPicker + "input_DLoadFile"))
	WebUI.clickOffset(findTestObject(Const.columnPicker + "input_DataLoadFile"), 0, 0)

	
Rep.nextTestStep("Select the file saved at Step 11 and click Open")
	eAct.uploadFile(downloadPath)
	
	
Rep.nextTestStep("Click [Upload]")
	gObj.buttonClickSync(Const.columnPicker + "a_Upload")

	
Rep.nextTestStep("Click [Import]")
	gObj.buttonClickSync(Const.dataLoad + "button_Import")
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.dataLoad + "btn_Close")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Press [F5] on Keyboard")
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click onto an [Inline] menu of any resource used in Skill data load")
	int rowNo = sVal.searchTableReturnRow(2, resourceCode)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", rowNo, "row")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click on [Open]")
	gObj.selectInlineOption(1)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [Skills] tab")
	gObj.buttonClickSync(Const.kip4Resource + "tab_Skills")
	
	
Rep.nextTestStep("Verify the recently upload skills have been added on for a Resource")
	String addedSkill = gObj.getEditTextSync(Const.kip4Resource + "table_AddedSkills")
	gAct.findSubstringInStringAndReport(addedSkill, skillCode)
	gAct.findSubstringInStringAndReport(addedSkill, skillName)
	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

