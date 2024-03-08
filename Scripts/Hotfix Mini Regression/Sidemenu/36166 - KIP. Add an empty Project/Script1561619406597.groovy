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
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct

import models.Project
import models.GenerateProject


String newDate = Com.todayDate()
String[] newProject = ["New Test Project", "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "TestProject_Auto", "", ""]
Project project = GenerateProject.createProject(newProject)

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	gAct.Wait(1)


Rep.nextTestStep("Hover over the mouse to the [+] sign from the side bar menu")
	WebUI.mouseOver(findTestObject(Const.plusMenu + 'plusSideMenu'))
	gObj.buttonClickSync(Const.plusMenu + 'menu_AddNew')
	
	
Rep.nextTestStep("Click on [Project] from the list ")
	gObj.buttonClickSync(Const.plusMenu + 'menu_Project')


Rep.nextTestStep("Click next without amending any data ")
	gObj.buttonClickSync(Const.projectWizard + 'radioEmptyProject')
	gObj.buttonClickSync(Const.projectWizard + 'button_Next')
	gAct.Wait(2)
	boolean error = WebUI.verifyElementVisible(findTestObject(Const.projectWizard + 'wizardError'))
	gVal.objectContainsText(Const.projectWizard + 'wizardError', "No Project Type selected")


Rep.nextTestStep("Select a Project Type from the [Project Type] dropdown field ")
	gObj.buttonClickSync(Const.projectWizard + 'projectTypeDropDown')
	gObj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectType, "type")


Rep.nextTestStep("Click Next")
	gObj.buttonClickSync(Const.projectWizard + 'button_Next')
	gAct.Wait(1)
	
		
Rep.nextTestStep("Click Next without adding any data ")
	String errText
	errText = "×\nField is required : General : Name\nField is required : General : Customer\nField is required : General : Working Time\nField is required : General : Parent Project"

	gObj.buttonClickSync(Const.projectWizard + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)
	error = WebUI.verifyElementVisible(findTestObject(Const.projectWizard + 'addProjectError'))
	
	if(GVars.browser == "MicrosoftEdge"){
		gVal.objectTextEdge(Const.projectWizard + 'addProjectError', errText)
	}else{
		gVal.objectText(Const.projectWizard + 'addProjectError', errText)
	}
	

Rep.nextTestStep("Enter data to all fields")
	Pop.addProject_Wizard_page2(project)
	Com.edgeSync()

	
Rep.nextTestStep("Click Save or Save and close ")
	gObj.buttonClickSync(Const.projectWizard + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)

	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")

	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', project.name)
