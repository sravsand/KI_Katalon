import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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

import global.Report as Rep
import global.Action as gAct
import global.WebDriverMethods as WebD
import global.DataSheet

import excel.Action as xlAct

import keyedInProjects.Action as Act
import keyedInProjects.Constants as Const
import keyedInProjects.Populate as Pop

import buildingBlocks.createComponents

def dataDriverFile = "C:\\Users\\dlees\\Katalon Studio\\KeyedInProjects\\Data Files\\keyedInBuildingBlockDriver.xlsx"
def myData = findTestData("buildBlockDriver")

//Test Step
Rep.nextTestStep("Create Department")
	myData.changeSheet("department")
	def departmentCode = createComponents.departmentDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 1, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 2, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "company", 1, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "programme", 1, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "parent", 1, "department", departmentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "project", 1, "department", departmentCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Create cost centre")
	myData.changeSheet("costCentre")
	def costCentreCode = createComponents.costCentreDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 1, "costCentre", costCentreCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "costCentre", costCentreCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 2, "costCentre", costCentreCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "costCentre", costCentreCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Create cost")
	myData.changeSheet("cost")
	def costCodeStd = createComponents.costDD(myData, 1)
	def costCodeOvr = createComponents.costDD(myData, 2)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "cost", 1, "code", costCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "cost", 2, "code", costCodeOvr, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 1, "cost", costCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 2, "cost", costCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "cost_std", costCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "cost_std", costCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "cost_ovr", costCodeOvr, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "cost_ovr", costCodeOvr, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Charge")
	myData.changeSheet("charge")
	def chargeCodeStd = createComponents.chargeDD(myData, 1)
	def chargeCodeOvr = createComponents.chargeDD(myData, 2)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "charge", 1, "code", chargeCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "charge", 2, "code", chargeCodeOvr, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 1, "charge", chargeCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "role", 2, "charge", chargeCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "charge_std", chargeCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "charge_std", chargeCodeStd, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "charge_ovr", chargeCodeOvr, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "charge_ovr", chargeCodeOvr, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Create role")
	myData.changeSheet("role")
	def roleCode = createComponents.roleDD(myData, 1)
	def roleCode2 = createComponents.roleDD(myData, 2)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 1, "role", roleCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "resource", 2, "role", roleCode2, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Create Resource")
	myData.changeSheet("resource")
	createComponents.resourceDD(myData, 1)
	createComponents.resourceDD(myData, 2)

	
//Test Step
Rep.nextTestStep("Create Customer")
	myData.changeSheet("customer")
	def customerCode = createComponents.customerDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "project", 1, "customer", customerCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "company", 1, "customer", customerCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Create company")
	myData.changeSheet("company")
	def companyCode = createComponents.companyDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "company", 1, "code", companyCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "programme", 1, "parent", companyCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
	
//Test Step
Rep.nextTestStep("Create programme")
	myData.changeSheet("programme")
	def programmeCode = createComponents.programmeDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "programme", 1, "code", programmeCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "parent", 1, "parent", programmeCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")


//Test Step
Rep.nextTestStep("Create parent")
	myData.changeSheet("parent")
	def parentCode = createComponents.parentDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "parent", 1, "code", parentCode, "buildBlockDriver")
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "project", 1, "parent", parentCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

	
//Test Step
Rep.nextTestStep("Create project")
	myData.changeSheet("project")
	def projectCode = createComponents.projectDD(myData, 1)
	DataSheet.writeToCellAndRefresh(myData, dataDriverFile, "project", 1, "code", projectCode, "buildBlockDriver")
	myData = findTestData("buildBlockDriver")

