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
import models.Activity
import models.GenerateActivity

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
	String newActivityName = Com.generateRandomText(10)
	
	
	String clonedActivityName = Com.generateRandomText(10)
	String clonedActivityCode = clonedActivityName.toUpperCase()
	
	String [] newClonedActivity = [clonedActivityName, clonedActivityCode]
	Activity clonedActivity = GenerateActivity.createActivity(newClonedActivity)

	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Activities]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Activities")
	
	
Rep.nextTestStep("Click onto [Inline menu] against an Activity")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, activity.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Clone]")
	gObj.selectInlineOption(2)
	
	
Rep.nextTestStep("Verify the [Name] is pre-populated")
	String actName = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actName, activity.name)
	gObj.setEditSync(Const.kip4Generic + "input_Name", clonedActivity.name)
	
	
Rep.nextTestStep("Verify User is able to add [Code] (Code not auto generated)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", clonedActivity.code)
	
	
Rep.nextTestStep("Verify the [Active Checkbox] is pre-selected as per the cloned Activity")
	def activeRem = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(activeRem, "true")
		
	
Rep.nextTestStep("Save Cloned Activity")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the activity table")
	sVal.searchTableAddedValue(2, clonedActivity.code)
	
	