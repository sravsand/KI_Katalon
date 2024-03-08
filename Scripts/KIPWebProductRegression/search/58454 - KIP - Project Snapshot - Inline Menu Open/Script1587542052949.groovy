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
import models.ProjectSnapshot
import models.GenerateProjectSnapshot

String searchItem = "Project Snapshots"
String searchVal = "Project Snapshots"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String snapshotName = Com.generateRandomText(10)
	String[] newProjectSnapshot = ["Project Innovation_CHROME_DRIVER", snapshotName, "Interim"]
	ProjectSnapshot projectSnapshot = GenerateProjectSnapshot.createProjectSnapshot(newProjectSnapshot)
	component.createProjectSnapshot(projectSnapshot)
	
	
Rep.nextTestStep("Select [Projects Snapshots] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu]")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, projectSnapshot.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)
	gAct.Wait(2)
	

Rep.nextTestStep("Verify all the fields are pre-populated accurately")
	String Projectnme = WebD.getElementText('id("ProjectSnapshotGeneral")/div[@class="form-horizontal container-fluid edit-page-tab"]/div[1]/div[@class="form-group  row"]')
	gAct.findSubstringInStringAndReport(Projectnme, projectSnapshot.project)
	
	String name = gObj.getAttributeSync(Const.addProjectSnapshot + 'input__Description', 'value')
	gAct.compareStringAndReport(name, projectSnapshot.name)
	String type = gObj.getAttributeSync(Const.addProjectSnapshot + 'select_Type', 'value')
	gAct.compareStringAndReport(type, "0")
	
	gObj.buttonClickSync(Const.addProjectSnapshot + 'button_Close')

	