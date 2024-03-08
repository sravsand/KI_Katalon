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
	
	String[] newSkill = [skillName, skillCode, "1", "5"]
	Skill skill = GenerateSkill.createSkill(newSkill)
	component.createSkill(skill)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
		
		
Rep.nextTestStep("Click over [Skills]")
	gObj.buttonClickSync(Const.skillsMenu + "a_Skills")
		
	
Rep.nextTestStep("Click onto [Inline menu] against a skill")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, skill.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Ensure [Name] field is mandatory and populated")
	String actVal = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal, skill.name)

	

Rep.nextTestStep("Ensure no more than 70 characters can be entered in the [Name] field")
	String longText = "aqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Name", longText + "nm")
	
	String actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal2, longText)
	

Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Name] field")
	Rep.info("current Katalon issue")
/*	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Name"), Keys.chord(Keys.CONTROL + "a"))
	gAct.Wait(1)
	WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Name"), Keys.chord(Keys.BACK_SPACE))
	gAct.Wait(1)
	  WebUI.sendKeys(findTestObject(Const.kip4Generic + "input_Name"), Keys.chord(Keys.TAB))
	gAct.Wait(1)
	
	WebUI.verifyElementVisible(findTestObject(Const.kip4Skills + "errNameField"))
	String errVal = WebUI.getText(findTestObject(Const.kip4Skills + "errNameField"))
	gAct.compareStringAndReport(errVal, "This field is required")
*/
	gObj.setEditSync(Const.kip4Generic + "input_Name", skill.name)
	

Rep.nextTestStep("Ensure [Code] field is populated and cannot be amended")
	String actCode = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')
	

Rep.nextTestStep("Ensure [Minimum Level] is populated and editable")
	String actMin = gObj.getAttributeSync(Const.kip4Skills + "input_minLevel", 'value')
	gAct.compareStringAndReport(actMin, skill.minLevel)
	

Rep.nextTestStep("Ensure no more than 1 characters can be entered in the [Minimum Level] field")
	String errMinAmt = "10"
	String minLen = gObj.getAttributeSync(Const.kip4Skills + "input_minLevel", 'maxlength')
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", errMinAmt)

	String actValMin = gObj.getAttributeSync(Const.kip4Skills + "input_minLevel", 'value')
	gAct.compareStringAndReport(actValMin, skill.minLevel)


Rep.nextTestStep("Ensure [Maximum Level] is populated and editable")
	String actMax = gObj.getAttributeSync(Const.kip4Skills + "input_maxLevel", 'value')
	gAct.compareStringAndReport(actMax, skill.maxLevel)


Rep.nextTestStep("Ensure no more than 2 characters can be entered in the [Maximum Level] field")
	String errMaxAmt = "100"
	String maxLen = gObj.getAttributeSync(Const.kip4Skills + "input_maxLevel", 'maxlength')
	gObj.setEditSync(Const.kip4Skills + "input_maxLevel", errMaxAmt)

	String actMax2 = gObj.getAttributeSync(Const.kip4Skills + "input_maxLevel", 'value')
	gAct.compareStringAndReport(actMax2, "10")
	

Rep.nextTestStep("Ensure that only whole numeric values can be entered in [Minimum/Maximum Level] fields")
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", "AB")
	
	gObj.setEditSync(Const.kip4Skills + "input_maxLevel", "CD")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton", 'disabled')
	

Rep.nextTestStep("[Minimum Level] must be from 0 to 9 (inclusive)")
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", "10")
	String errVal3 = gObj.getAttributeSync(Const.kip4Skills + "input_minLevel", 'value')
	gAct.compareStringAndReport(errVal3, skill.minLevel)
	
	
Rep.nextTestStep("[Minimum Level] cannot be greater than or equal to [Maximum Level]")
	String eqLevel = "7"
	String errHigh = "9"
	gObj.setEditSync(Const.kip4Skills + "input_maxLevel", eqLevel)
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", eqLevel)
	
	WebUI.verifyElementVisible(findTestObject(Const.kip4Skills + "errMinVal"))
	String errVal4 = gObj.getEditTextSync(Const.kip4Skills + "errMinVal")
	gAct.compareStringAndReport(errVal4, 'Min must be less than Max')
	
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", errHigh)
	gObj.elementVisibleSync(Const.kip4Skills + "errMinVal")
	String errVal5 = gObj.getEditTextSync(Const.kip4Skills + "errMinVal")
	gAct.compareStringAndReport(errVal5, 'Min must be less than Max')
	
	gObj.setEditSync(Const.kip4Skills + "input_minLevel", skill.minLevel)
	gObj.setEditSync(Const.kip4Skills + "input_maxLevel", skill.maxLevel)
	

Rep.nextTestStep("Select [Resource] tab.")
	gObj.buttonClickSync(Const.kip4Skills + "tab_Resource")


Rep.nextTestStep("Add some [Resources] and click ADD  OR  Select 'Copy From' to copy [Resources] from another [Skill]")
	gObj.buttonClickSync(Const.kip4Skills + "btn_AddResources")
	gObj.buttonClickSync(Const.kip4Skills + "chk_Resource")
	gObj.buttonClickSync(Const.kip4Skills + "button_Select")
	gObj.buttonClickSync(Const.kip4Skills + "dropDown_Level")
	gObj.buttonClickSubIntPosSync(Const.kip4Skills + "select_Level", 2, "val")
	

Rep.nextTestStep("Repeat and add [Projects] via [Project] tab")
	gObj.buttonClickSync(Const.kip4Skills + "tab_Project")
	gObj.buttonClickSync(Const.kip4Skills + "btn_AddProjects")
	gObj.buttonClickSync(Const.kip4Skills + "chk_Project")
	gObj.buttonClickSync(Const.kip4Skills + "button_Select")
	gObj.buttonClickSync(Const.kip4Skills + "dropDown_Level")
	gObj.buttonClickSubIntPosSync(Const.kip4Skills + "select_Level", 3, "val")
	
	
Rep.nextTestStep("Click Save/Save and Close")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()

	