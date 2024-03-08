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
import models.Skill
import models.GenerateSkill


//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String skillName = Com.generateRandomText(10)
	String skillCode = skillName.toUpperCase()
	
	String[] newSkill = [skillName, skillCode, "1", "8"]
	Skill skill = GenerateSkill.createSkill(newSkill)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Skills]")
	gObj.buttonClickSync(Const.skillsMenu + "a_Skills")
	
	
Rep.nextTestStep("Click add button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Add [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", skill.name)


Rep.nextTestStep("Add a [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", skill.code)
	

Rep.nextTestStep("Add [Minimum Level]")
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", skill.minLevel)

	
Rep.nextTestStep("Add [Maximum Level] (has to be a bigger no than minimum level)")
	gObj.setEditSync(Const.kip4Skills + "input_maxLevel", skill.maxLevel)
	

Rep.nextTestStep("Click over [Resource] tab")
	gObj.buttonClickSync(Const.kip4Skills + "tab_Resource")
	

Rep.nextTestStep("Click over [Add Resource] button")
	gObj.buttonClickSync(Const.kip4Skills + "btn_AddResources")
	

Rep.nextTestStep("Select a [Resource] via checkbox")
	gObj.buttonClickSync(Const.kip4Skills + "chk_Resource")

	
Rep.nextTestStep("Click onto [Select] button")
	gObj.buttonClickSync(Const.kip4Skills + "button_Select")
	gObj.buttonClickSync(Const.kip4Skills + "dropDown_Level")
	gObj.buttonClickSubIntPosSync(Const.kip4Skills + "select_Level", 2, "val")


Rep.nextTestStep("Click over [Project] tab")
	gObj.buttonClickSync(Const.kip4Skills + "tab_Project")
	

Rep.nextTestStep("Click over [Add Project] button")
	gObj.buttonClickSync(Const.kip4Skills + "btn_AddProjects")
	

Rep.nextTestStep("Select a [Project] via checkbox")
	gObj.buttonClickSync(Const.kip4Skills + "chk_Project")


Rep.nextTestStep("Click onto [Select] button")
	gObj.buttonClickSync(Const.kip4Skills + "button_Select")
	gObj.buttonClickSync(Const.kip4Skills + "dropDown_Level")
	gObj.buttonClickSubIntPosSync(Const.kip4Skills + "select_Level", 3, "val")
	

Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")


Rep.nextTestStep("Verify the message to read as [Saved Successfully]")
	Act.verifySavePopUpText()

	
Rep.nextTestStep("Verify the newly added skill have been added onto skill table")
	sVal.searchTableAddedValue(2, skill.code)
	