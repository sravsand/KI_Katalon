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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Assignment
import models.GenerateAssignment

//change from default login
GVars.configFileName = 	"katalonConfigAuto3.xml"
Act.changeUserCredentials()

String searchItem = "Assignments"
String searchVal = "Assignment"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String ProjectName = Com.generateRandomText(6)
	String ProjectCode = ProjectName.toUpperCase()
	String newDate = Com.todayDate()
	String ProjectDescription = Com.generateRandomText(20)
	
	String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String endDate = Com.increaseWorkingCalendarWithFormat(today, 4, pattern)
	
	String[] newAssignment = ["project_asgds", "Planning Meeting", ProjectDescription, GVars.user, "KIP Dev Team 1", todaysDate, "8:00", endDate, "16:00", "100"]
	Assignment assignment = GenerateAssignment.createAssignment(newAssignment)
	component.createAssignment(assignment)
	
	String clonedProjectDescription = Com.generateRandomText(20)
	String clonedStartDate = Com.increaseWorkingCalendarWithFormat(today, 8, pattern)
	String clonedEndDate = Com.increaseWorkingCalendarWithFormat(today, 12, pattern)
	String[] newClonedAssignment = ["project_asgds", "Planning Meeting", clonedProjectDescription, GVars.user, "KIP Dev Team 1", clonedStartDate, "8:00", clonedEndDate, "16:00", "100"]
	Assignment clonedAssignment = GenerateAssignment.createAssignment(newClonedAssignment)

	
Rep.nextTestStep("Select [Assignments] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Contract")
	gObj.buttonClickSync(Const.columnPicker + 'a_Default Assignment Filter')
	int rowNo = sVal.searchTableReturnRow(5, assignment.description)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(3)
		

Rep.nextTestStep("Amend text in [description] field")
	gObj.setEditSync(Const.addAssignment + "input_Description", clonedAssignment.description)
	

Rep.nextTestStep("Amend [Start Date]")
	gObj.setEditSync(Const.addAssignment + "input_StartDate", clonedAssignment.startDate)
	
	
Rep.nextTestStep("Amend [End Date]")
	gObj.setEditSync(Const.addAssignment + "input_EndDate", clonedAssignment.endDate)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	WebUI.refresh()
	gAct.Wait(3)
	sVal.searchTableAddedValue(5, clonedAssignment.description)

