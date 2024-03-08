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
import models.Project
import models.GenerateProject

String searchItem = "Projects"
String searchVal = "Projects"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String ProjectName = Com.generateRandomText(10)
	String newDate = Com.workingDate()
	String[] newProject = [ProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project project = GenerateProject.createProject(newProject)
	component.createProject(project)
	
	String changedProjectType = "Parent"

	
Rep.nextTestStep("Select [Projects] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	
Rep.nextTestStep("Select a [Project] via checkbox for which the project type need changing")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(4, project.name)
	String projectType = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 10, "col")
	gAct.compareStringAndReport(projectType, project.projectType)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'chk_SelectProject', rowNo, "row")

	
Rep.nextTestStep("Click onto [Change Project Type] button")
	gObj.buttonClickSync(Const.columnPicker + "a_Change Project Type")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Verify the count of [Total no selected project] is accurate")
	String noProjects = gObj.getEditTextSync(Const.columnPicker + "numberOfProject")
	gAct.compareStringAndReport(noProjects, "1")

	
Rep.nextTestStep("Select a [New Project Type] from dropdown")
	gObj.buttonClickSync(Const.columnPicker + "projectType_dropdown")
	gObj.buttonClickSubSync(Const.columnPicker + "selectProjectType", changedProjectType, "type")

	
Rep.nextTestStep("Select [New Workflow] mapping againt the [Current Workflow]")
	gObj.buttonClickSync(Const.columnPicker + "Initiation_drop")
	gObj.buttonClickSync(Const.columnPicker + "Select_Default")
	
	gObj.buttonClickSync(Const.columnPicker + "Planning_drop")
	gObj.buttonClickSync(Const.columnPicker + "Select_Default")
	
	gObj.buttonClickSync(Const.columnPicker + "Execution_drop")
	gObj.buttonClickSync(Const.columnPicker + "Select_Default")
	
	gObj.buttonClickSync(Const.columnPicker + "Closure_drop")
	gObj.buttonClickSync(Const.columnPicker + "Select_Default")
	

Rep.nextTestStep("Click onto [Change Project Type]")
	gObj.buttonClickSync(Const.columnPicker + "button_ChangeProjectType")
	gAct.Wait(1)
	WebUI.refresh()
	gAct.Wait(GVars.shortWait)

	
Rep.nextTestStep("Verify the [Project Type] is changed for the selected project")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)
	int rowNum = sVal.searchTableReturnRow(4, project.name)
	String newProjectType = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 10, "col")
	gAct.compareStringAndReport(newProjectType, changedProjectType)
	