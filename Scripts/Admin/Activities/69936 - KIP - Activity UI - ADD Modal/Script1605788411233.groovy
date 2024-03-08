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

String activityName = Com.generateRandomText(10)
String actCode = activityName.toUpperCase()
String activityCode = actCode.take(3)

String[] newActivity = [activityName, activityCode]
Activity activity = GenerateActivity.createActivity(newActivity)


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Activities]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Activities")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

	
Rep.nextTestStep("Accept alert and check activity not created")
	sVal.searchTableDeletedValue(2, activity.code)
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", activity.name)
	
	
Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", activity.code)
	

Rep.nextTestStep("Click over [Restriction] tab")
	gObj.buttonClickSync(Const.kip4Activities + "tab_Restrictions")
	
	
Rep.nextTestStep("Select [All_Resources] checkbox")
	def chkRes = WebD.getCheckBoxState("//input[@id='noResourceRestriction']")

	if(chkRes == null){
		gObj.checkSync(Const.kip4Activities + "chk_AllResources")
	}
	
	
Rep.nextTestStep("Click over [Restriction_Project] tab")
	gObj.buttonClickSync(Const.kip4Activities + "tab_ResProject")
	
	
Rep.nextTestStep("Select [All_Projects] checkbox")
	def chkPro = WebD.getCheckBoxState("//input[@id='noProjectRestriction']")

	if(chkPro == null){
		gObj.checkSync(Const.kip4Activities + "chk_AllProjects")
	}
	

Rep.nextTestStep("Click onto [Save] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_savebtn-primary")
	Act.verifySavePopUpText()

	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
		
Rep.nextTestStep("Verify the changes are updated accurately on the activity table")
	sVal.searchTableAddedValue(2, activity.code)
		