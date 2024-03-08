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
import risks.Validate as rVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Risk
import models.GenerateRisk
import models.Project
import models.GenerateProject
import java.text.SimpleDateFormat

String searchItem = "Risks"
String searchVal = "Risks"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String ProjectName = Com.generateRandomText(10)
	String newDate = Com.workingDateBack()
	String[] newFirstProject = [ProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project project = GenerateProject.createProject(newFirstProject)
	component.createProject(project)
	
	String secondProjectName = Com.generateRandomText(10)
	
	String[] newSecondProject = [secondProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning", ""]
	Project newproject = GenerateProject.createProject(newSecondProject)
	component.createProject(newproject)
	
	
	def pattern = "dd/MM/yyyy"
	String todaysDate = Com.todayDate()
	Date today = new SimpleDateFormat(pattern).parse(todaysDate)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	String riskTitle = Com.generateRandomText(10)
	
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	String[] newRisk = [riskTitle, project.name, GVars.user, "Open", todaysDate, todaysDate, "Project", "High", "Medium", "High", endDate, "", ""]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	component.createRisk(risk)

	
Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [Copy]")
	gObj.buttonClickSync(Const.columnPicker + "a_Copy")
	

Rep.nextTestStep("Select the [Source Project]")
	gObj.setEditSync(Const.columnPicker + "input_SourceProject", project.name)
	

Rep.nextTestStep("Select the [Destination Project]")
	gObj.setEditSync(Const.columnPicker + "input_DestinationProject", newproject.name)
	gObj.buttonClickSync(Const.columnPicker + "input_SourceProject")
	

Rep.nextTestStep("Tick the checkbox for [Include Mitigation Plan Items]")
	gObj.checkSync(Const.columnPicker + "chk_IncludeMitigationPlanItems")


Rep.nextTestStep("Select a [Date]")
	gObj.setEditSync(Const.columnPicker + "input_CopyRiskDate", newDate)


Rep.nextTestStep("Click [Copy]")
	gObj.buttonClickSync(Const.columnPicker + "button_Copy")
	gAct.Wait(GVars.shortWait)

	WebD.clickElement("//th[@class='text-align-center sorting_disabled resizing_disabled editableTableButtonsHeader']")
	gObj.buttonClickSync(Const.columnPicker + "a_Reset to Default")
	
	
Rep.nextTestStep("Refresh the [Risks] search page then verify that new [Risks] from the [Source Project] have been added to the results list")
	int rowNo = sVal.searchTableReturnRow(2, project.name)
	String newStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 2, "col")
	gAct.compareStringAndReport(newStatus, project.name)

	int rowNum = sVal.searchTableReturnRow(2, newproject.name)
	String newStatus2 = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 2, "col")
	gAct.compareStringAndReport(newStatus2, newproject.name)
	