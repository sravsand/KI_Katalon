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
import com.kms.katalon.core.webui.driver.DriverFactory
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
import search.PopulateLogedIn as pVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Assignment
import models.GenerateAssignment
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre
import java.text.SimpleDateFormat

//change from default login
GVars.configFileName = 	"katalonConfigAuto3.xml"
Act.changeUserCredentials()

String searchItem = "Assignments"
String searchVal = "Assignment"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String newCostCentre = pVal.newCostCentre()
	Resource resource = pVal.newUserNoLogin(newCostCentre)
	Resource secondResource = pVal.newUserNoLogin(newCostCentre)
	
	String ProjectName = Com.generateRandomText(6)
	String ProjectCode = ProjectName.toUpperCase()
	String ProjectDescription = Com.generateRandomText(20)
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	def pattern = "dd/MM/yyyy"
	String workingDt = Com.workingDateBack()
	Date today = new SimpleDateFormat(pattern).parse(workingDt)
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	
	String[] newAssignment = ["project_asgds", "completion", ProjectDescription, resource.name, "KIP Dev Team 1", todaysDate, "8:00", endDate, "16:00", "5"]
	Assignment assignment = GenerateAssignment.createAssignment(newAssignment)
	component.createAssignment(assignment)


Rep.nextTestStep("Select [Assignments] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Select the [Assignment] user want to Assign a Resource through checkbox")
	gObj.buttonClickSync(Const.columnPicker + 'a_Default Assignment Filter')
	int rowNo = sVal.searchTableReturnRow(5, assignment.description)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'chk_selectAssignment', rowNo, "row")
	
	String reso = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 10, "col")
	gAct.compareStringAndReport(reso, resource.name)
	
	
Rep.nextTestStep("Click onto [Reassign Assignments to Resource] button")
	gObj.buttonClickSync(Const.columnPicker + 'a_Reassign')


Rep.nextTestStep("Select a [Resource] from dropdown list")
	String newRece = secondResource.name
	gObj.selectComboValueOldModal('dropDown_Resource', newRece)
	

Rep.nextTestStep("Click [Submit]")
	gObj.buttonClickSync(Const.columnPicker + 'btn_SubmitReassign')


Rep.nextTestStep("Click [Ok]")
	gObj.buttonClickSync(Const.columnPicker + 'btn_OK_Revert')

	boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)
	if (elementVisible){
		WebUI.waitForElementNotPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
	}

	
Rep.nextTestStep("Click [Submit]")
	rowNo = sVal.searchTableReturnRow(5, assignment.description)
	String newRes = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 10, "col")
	gAct.compareStringAndReport(newRes, secondResource.name)

