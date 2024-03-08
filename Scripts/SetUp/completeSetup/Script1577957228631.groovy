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

//String departmentCode = "Dept_HLEZZM"
//String customerCode = "customer_oreert"
//String companyCode = "company_rptaee"


String[] newDepartment = ["department_" + ProjectName, "DEPT_" + ProjectCode, "", ""]
Department department = GenerateDepartment.createDepartment(newDepartment)
def departmentCode = createComponents.department(department)

String[] newCustomer = ["customer_" + ProjectName, "", "", "", "", "", "", "", "", "", "", ""]
Customer customer = GenerateCustomer.createCustomer(newCustomer)
def customerCode = createComponents.customer(customer)

String[] newCompany = ["company_" + ProjectName, "", customerCode, "", "Company", "UK", departmentCode, "", ""]
Company company = GenerateCompany.createCompany(newCompany)
def companyCode = createComponents.company(company)

String[] newProgramme = ["programme_" + ProjectName, "", customerCode, "Programme", "", "", departmentCode, "", ""]
Programme programme = GenerateProgramme.createProgramme(newProgramme)
def programmeCode = createComponents.programme(programme)

String[] newParent = ["parent_" + ProjectName, "", customerCode, "", "", "", "", "", ""]
Parent parent = GenerateParent.createParent(newParent)
def parentCode = createComponents.parent(programme)

String[] newProject = ["project_" + ProjectName, "PPM Solutions Inc", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "Project Management Life Cycle", "Task Management Planning"]
Project project = GenerateProject.createProject(newProject)
def projectCode = createComponents.project(project)
