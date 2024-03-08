import java.lang.invoke.ConstantCallSite

import java.lang.invoke.ConstantCallSite

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
import search.Action as sAct
import search.Validate as sVal
import models.Project
import models.GenerateProject

String ProjectName = Com.generateRandomText(10)
String newDate = Com.workingDate()
String[] newProject = [ProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
Project project = GenerateProject.createProject(newProject)

String searchItem = "Projects"
String searchVal = "Projects"


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Projects] from search filter")
	Nav.selectSearchFilter(searchItem)

		
Rep.nextTestStep("Click On [+ Add] ")
	WebD.clickElement("//a[@class='tooltip-primary btn btn-default shiny btn-sm viewaction partial']")

	
Rep.nextTestStep("Select [Create project from a template]")
	gObj.buttonClickSync(Const.columnPicker + 'template_Radio')
	
	
Rep.nextTestStep("Select a Project Type from the [Project Type] dropdown field ")
	gObj.buttonClickSync(Const.projectWizard + 'projectTypeDropDown')
	gObj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectType, "type")
	
	
Rep.nextTestStep("Click on [Template] dropdown menu")
	gObj.buttonClickSync(Const.projectWizard + 'a_Select')
	gObj.buttonClickSubSync(Const.projectWizard + 'selectTemplate', project.template, "template")
	
	gObj.buttonClickSync(Const.projectWizard + 'chk_CopyDeliverables_Wizard')
	gObj.buttonClickSync(Const.projectWizard + 'chk_CopyForecast_Wizard')
	gObj.buttonClickSync(Const.projectWizard + 'chk_CopyRisks_Wizard')
	gObj.buttonClickSync(Const.projectWizard + 'chk_CopyTasks_Wizard')
	
	
Rep.nextTestStep("Click Next")
	gObj.buttonClickSync(Const.projectWizard + 'button_Next')
	
	
Rep.nextTestStep("Populate wizard")
	Pop.addProject_Wizard_page2(project)

	
Rep.nextTestStep("Click on [Save] or [Save & Close] button")
	gObj.buttonClickSync(Const.projectWizard + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)


Rep.nextTestStep("Verify recently added risk within [Project] table")
	sVal.searchTableAddedValue(4, project.name)
