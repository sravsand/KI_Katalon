package models

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import models.Project

public class GenerateProject {

	static def createProjectDD(def newProject, def row){
		Project project = new Project()

		project.name = newProject.getValue("name", row)
		project.customer = newProject.getValue("customer", row)
		project.workingTime = newProject.getValue("workingTime", row)
		project.projectLevel = newProject.getValue("projectLevel", row)
		project.parent = newProject.getValue("parent", row)
		project.location = newProject.getValue("location", row)
		project.department = newProject.getValue("department", row)
		project.projectManager = newProject.getValue("projectManager", row)
		project.startDate = newProject.getValue("startDate", row)
		project.endDate = newProject.getValue("endDate", row)

		return project
	}


	static def createProject(String[] newProject){
		Project project = new Project()

		project.name = newProject[0]
		project.customer = newProject[1]
		project.workingTime = newProject[2]
		project.projectLevel = newProject[3]
		project.parent = newProject[4]
		project.location = newProject[5]
		project.department = newProject[6]
		project.projectManager = newProject[7]
		project.startDate = newProject[8]
		project.endDate = newProject[9]
		project.projectType = newProject[10]
		project.template = newProject[11]
		project.code = newProject[12]
		
		return project
	}
}
