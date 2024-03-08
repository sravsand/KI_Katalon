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
import models.SkillWithProjRes
import models.GenerateSkillWithProjRes

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String skillName = Com.generateRandomText(10)
	String skillCode = skillName.toUpperCase()
	
	String[] newSkill = [skillName, skillCode, "1", "5", "Dave Lees", "2", "Project Innovation_CHROME_DRIVER","3"]
	SkillWithProjRes skill = GenerateSkillWithProjRes.createSkillwithProjRes(newSkill)
	component.createSkillWithProjectAndResource(skill)
	
	String skillNameNew = Com.generateRandomText(10)
	String skillCodeNew = skillNameNew.toUpperCase()
	
	String[] newSkillNew = [skillNameNew, skillCodeNew, "1", "7", "Dave Lees", "2", "Project Innovation_CHROME_DRIVER","3"]
	SkillWithProjRes skillNew = GenerateSkillWithProjRes.createSkillwithProjRes(newSkillNew)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
		
		
Rep.nextTestStep("Click over [Skills]")
	gObj.buttonClickSync(Const.skillsMenu + "a_Skills")
		
	
Rep.nextTestStep("Click onto [Inline menu] against a skill")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, skill.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click over [Clone]")
	gObj.selectInlineOption(2)

	
Rep.nextTestStep("Verify the [Name] is pre-populated with existing name")
	String actVal = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal, skill.name)

	
Rep.nextTestStep("Amend [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", skillNew.name)
	

Rep.nextTestStep("Verify  the [Code] field is blank")
	String actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actVal2, "")

	
Rep.nextTestStep("Add a [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", skillNew.code)
	

Rep.nextTestStep("Verify the [Min] & [Max] limit is pre-populated with existing level used in pre-req skill")
	String actVal3 = gObj.getAttributeSync(Const.kip4Skills + "input_minLevel", 'value')
	gAct.compareStringAndReport(actVal3, skill.minLevel)
	String actVal4 = gObj.getAttributeSync(Const.kip4Skills + "input_maxLevel", 'value')
	gAct.compareStringAndReport(actVal4, skill.maxLevel)

	
Rep.nextTestStep("Click over [Resource] tab")
	gObj.buttonClickSync(Const.kip4Skills + "tab_Resource")

	
Rep.nextTestStep("Verify the table is pre-populated with same resource restriction used in pre-req skill (if they have been added )")
	String actVal5 = gObj.getEditTextSync(Const.kip4Skills + "selected_Resource")
	gAct.compareStringAndReport(actVal5, skill.resource)
	String actVal6 = gObj.getEditTextSync(Const.kip4Skills + "selected_ResLevel")
	gAct.compareStringAndReport(actVal6, skill.resourceLevel)
	

Rep.nextTestStep("Click over [Project] tab")
	gObj.buttonClickSync(Const.kip4Skills + "tab_Project")

	
Rep.nextTestStep("Verify the table is pre-populated with same proejct restriction used in pre-req skill (if they have been added )")
	String actVal7 = gObj.getEditTextSync(Const.kip4Skills + "selected_Project")
	gAct.compareStringAndReport(actVal7, skill.project)
	String actVal8 = gObj.getEditTextSync(Const.kip4Skills + "selected_ProjLevel")
	gAct.compareStringAndReport(actVal8, skill.projectLevel)


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")


Rep.nextTestStep("Verify the message to read as [Saved Successfully]")
	Act.verifySavePopUpText()

	
Rep.nextTestStep("Verify the newly added skill have been added onto skill table")
	sVal.searchTableAddedValue(2, skillNew.code)

