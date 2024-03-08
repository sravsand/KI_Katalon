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
	component.createMultiProjectSnapshot(projectSnapshot, 3)
	
	String snapshotName2 = Com.generateRandomText(10)
	String[] newProjectSnapshot2 = ["Project Innovation_CHROME_DRIVER", snapshotName2, "Baseline"]
	ProjectSnapshot projectSnapshot2 = GenerateProjectSnapshot.createProjectSnapshot(newProjectSnapshot2)
	component.createMultiProjectSnapshot(projectSnapshot2, 3)
	
	
Rep.nextTestStep("Select [Projects Snapshots] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	boolean pageLengthVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "pageCount"), GVars.midWait, FailureHandling.OPTIONAL)
	
	if(pageLengthVis){
		gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	}
	
	gAct.Wait(1)
	gObj.buttonClickSync(Const.columnPicker + "header_Name")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Click on [Type] from menu bar")
	WebD.clickElement("//th[@class='sorting'][contains(text(),'Type')]")
	gAct.Wait(1)
	def preSort = sAct.searchTableGetColumnValues_Alt(4)
	gAct.Wait(2)
	WebD.clickElement("//th[@class='sorting_asc']")
	
	 gAct.Wait(1)
	def postSort = sAct.searchTableGetColumnValues_Alt(4)
	boolean match = gVal.matchingReverseArrays(preSort, postSort)
	
	if(match){
		Rep.pass("Type has been sorted correctly.")
	}else{
		Rep.fail("Type has not been sorted correcly.")
	}
	
	
Rep.nextTestStep("Click on [Name] from menu bar")
	gObj.buttonClickSync(Const.columnPicker + "header_Name")
	gAct.Wait(1)
	def preSortCode = sAct.searchTableGetColumnValues_Alt(3)
	gObj.buttonClickSync(Const.columnPicker + "header_Name")
	gAct.Wait(1)
	def postSortCode = sAct.searchTableGetColumnValues_Alt(3)
	boolean matchCode = gVal.matchingReverseArrays(preSortCode, postSortCode)
	
	if(matchCode){
		Rep.pass("Name has been sorted correctly.")
	}else{
		Rep.fail("Name has not been sorted correcly.")
	}
