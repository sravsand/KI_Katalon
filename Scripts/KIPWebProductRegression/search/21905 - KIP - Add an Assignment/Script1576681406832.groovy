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
import models.Assignment
import models.GenerateAssignment
import models.Customer
import models.GenerateCustomer
import models.Company
import models.GenerateCompany
import models.Programme
import models.GenerateProgramme
import models.Parent
import models.GenerateParent
import models.Project
import models.GenerateProject
import models.Department
import models.GenerateDepartment
import buildingBlocks.createComponents

String ProjectName = Com.generateRandomText(6)
String ProjectCode = ProjectName.toUpperCase()
String newDate = Com.todayDate()

String selectedBrowser = DriverFactory.getExecutedBrowser().getName()

def pattern = "dd/MM/yyyy"
Date today = new Date()
String todaysDate = today.format(pattern)

String startDate = Com.increaseWorkingCalendarWithFormat(today, 5, pattern)
String endDate = Com.increaseWorkingCalendarWithFormat(today, 9, pattern)


String[] newAssignment = ["project_asgds", "Development", "A test Assignment", GVars.user, "KIP Dev Team 1", startDate, "8:00", endDate, "16:00", "40"]
Assignment assignment = GenerateAssignment.createAssignment(newAssignment)

String searchItem = "Assignments"
String searchVal = "Assignment"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Assignments] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click On [+ Add] ")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

	
Rep.nextTestStep("Search & Select [Project]")
	gObj.selectComboValueOldModal("dropDown_Project", assignment.project)
	gAct.Wait(2)

	
Rep.nextTestStep("Select [Task] from dropdown list")	
	gObj.selectComboValueOldModal("dropDown_Task", assignment.task)
	gAct.Wait(1)
	
	
Rep.nextTestStep("Search & Select [Resource]")
	gObj.selectComboValueOldModal("dropDown_Resource", assignment.resource)
	gAct.Wait(1)

	
Rep.nextTestStep("Select a [Start Date]")
	gObj.setEditSync(Const.addAssignment + "input_StartDate", assignment.startDate)
	gObj.buttonClickSync(Const.addAssignment + "input_StartTime")
	gAct.Wait(1)
	
		
Rep.nextTestStep("Select a [Finish Date]")
	gObj.setEditSync(Const.addAssignment + "input_EndDate", assignment.endDate)
	gObj.buttonClickSync(Const.addAssignment + "input_EndTime")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Enter [Effort] in textbox")
	WebUI.setText(findTestObject(Const.addAssignment + "input_Effort"), assignment.effort)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.addContract + "button_Save and Close")
	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	
		
Rep.nextTestStep("Verify the recently added Contract details are visible in table")
	sVal.searchTableAddedValue(4, assignment.project)
