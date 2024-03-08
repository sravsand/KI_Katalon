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
import buildingBlocks.createComponentsLogedIn as component
import models.ProjectSnapshot
import models.GenerateProjectSnapshot

String searchItem = "Project Snapshots"
String searchVal = "Project Snapshots"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String snapshotName = Com.generateRandomText(10)
	String[] newProjectSnapshot = ["Project Innovation_CHROME_DRIVER", snapshotName, "Interim"]
	ProjectSnapshot projectSnapshot = GenerateProjectSnapshot.createProjectSnapshot(newProjectSnapshot)
	
	
Rep.nextTestStep("Select [Projects Snapshots] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	
Rep.nextTestStep("Select [Add]")
	gObj.buttonClickSync(Const.addProjectSnapshot + "a_AddSnapshot")

	
Rep.nextTestStep("Select a [Project]")
	gObj.buttonClickSync(Const.addProjectSnapshot + 'dropDown_ProjSnapshot')
	gObj.setEditSync(Const.addProjectSnapshot + 'input_dropdownSnap',  projectSnapshot.project)	
	gObj.buttonClickSubSync(Const.addProjectSnapshot + 'select_Project_Snapshot', projectSnapshot.project, "proj")


Rep.nextTestStep("Add [Name]")
	gObj.setEditSync(Const.addProjectSnapshot + 'input__Description', projectSnapshot.name)
	

Rep.nextTestStep("Select [Type]")
	gObj.selectComboByLabelSync(Const.addProjectSnapshot + 'select_Type', projectSnapshot.type)


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.addProjectSnapshot + 'button_Save and Close')
	

Rep.nextTestStep("Verify the recently added snapshot is available in snapshot table")
	sVal.searchTableAddedValue(3, projectSnapshot.name)

