import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.junit.After
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
import keyedInProjects.Object as Obj
import templates.Validate as tVal

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import administration.Action as aAct
import models.GenerateProject
import models.Project

String browser = Com.getBrowserType()
String newProject = "36825 Test Case Project " + browser
String existingTemplate = "TestAuto_Proj"
String projectLevel = "TestProject_Auto"

String[] newProj = [newProject, "PPM Solutions Inc", "Full Time Calendar (My Region)", projectLevel, "PSA Solutions Inc", "", "", "", "", "", "", "", ""] 
Project project = GenerateProject.createProject(newProj)

//change from default login
GVars.configFileName = 	"katalonConfigAuto10.xml"
Act.changeUserCredentials()


Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Hover over the mouse to the [+] sign from the side bar menu")
	WebUI.mouseOver(findTestObject(Const.plusMenu + 'plusSideMenu'))
	gObj.buttonClickSync(Const.plusMenu + 'menu_AddNew')
	

Rep.nextTestStep("Click on [Project] from the list ")
	gObj.buttonClickSync(Const.plusMenu + 'menu_Project')


Rep.nextTestStep("Click next without amending any data ")
	gObj.buttonClickSync(Const.projectWizard + 'radioEmptyProject')
	gObj.buttonClickSync(Const.projectAdd + 'button_Next')
	gAct.Wait(2)
	WebUI.verifyElementPresent(findTestObject(Const.projectAdd + 'error_Msg'), 10, FailureHandling.CONTINUE_ON_FAILURE)


Rep.nextTestStep("Select a Project Type from the [Project Type] dropdown field ")
	gObj.buttonClickSync(Const.projectAdd + 'projectTypeDropDown')
	gObj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectLevel, "type")

	
Rep.nextTestStep("Select [Create Project from Template] Radio button")
	gObj.buttonClickSync(Const.projectAdd + 'radio_CreateProjectFromTemplate')


Rep.nextTestStep("Select [Template] from the dropdown")
	gObj.buttonClickSync(Const.projectWizard + 'a_Select')
	gObj.buttonClickSubSync(Const.projectWizard + 'selectTemplate', existingTemplate, "template")


Rep.nextTestStep("Click [Next] ")
	gObj.buttonClickSync(Const.projectAdd + 'button_Next')
	gObj.buttonClickSync(Const.projectAdd + 'button_Next')


Rep.nextTestStep("Enter data to all fields")
	gObj.setEditSync(Const.projectAdd + 'input_Description', project.name)
	
	gObj.buttonClickSync(Const.projectAdd + 'customerDropdown')
	gObj.buttonClickSubSync(Const.projectAdd + 'selectCustomer', project.customer, "cust")

	gObj.buttonClickSync(Const.projectAdd + 'workingTimeDropDown')
	gObj.buttonClickSubSync(Const.projectAdd + 'selectWorkingTime', project.workingTime, "wt")
	
	gObj.buttonClickSync(Const.projectAdd + 'parentDropDown')
	gObj.buttonClickSubSync(Const.projectAdd + 'selectParent',  project.parent, "parent")
	

Rep.nextTestStep("Click [Save] or [Save and close]")
	gObj.buttonClickSync(Const.projectAdd + 'button_SaveAndClose')

	
Rep.nextTestStep("On The Search screen Click onto [Add ]")
	Nav.selectSearchFilter("Projects")
	
	gObj.buttonClickSync(Const.searchFilters + 'a_Project Templates Filter')
	gAct.Wait(1)
	WebD.clickElement("//div[@id='editableViewButtons']//a[1]")
	

Rep.nextTestStep("Select [Project Type] that the recently project was added into")
	gObj.buttonClickSync(Const.templates + 'ProjectType_DropDown')
	gObj.buttonClickSubSync(Const.templates + 'select_ProjectType', project.projectLevel, "value")

	
Rep.nextTestStep("Select [Create Project from Template] radio button")
	gObj.buttonClickSync(Const.templates + 'radio_CreateProjectFromTemplate')


Rep.nextTestStep("Click onto [Template] dropdown ")
	gObj.buttonClickSync(Const.templates + 'template_DropDown')


Rep.nextTestStep("Verify the recently added project is NOT present in the template dropdown")
	WebUI.verifyElementNotPresent(findTestObject(Const.templates + 'SelectTemplate', [('template'): project.name]), 10)
	gObj.buttonClickSubSync(Const.templates + 'SelectTemplate',existingTemplate, "template")
	

Rep.nextTestStep("[close]")
	gObj.buttonClickSync(Const.templates + 'button_Close')
	gAct.Wait(GVars.shortWait)


Rep.nextTestStep("GO to Search screen and verify the [Template] column for the recently added template is populated as [NO]")
	gObj.buttonClickSync(Const.searchFilters + 'a_Active Projects')
	Com.edgeSync()
	tVal.projectColumnSetToNo(project.name, 4, 9)

	