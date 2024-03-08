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
import models.Department

public class GenerateDepartment {

	static def createDepartmentDD(def newDepartment, def row){
		Department department = new Department()

		department.name = newDepartment.getValue("name", row)
		department.code = newDepartment.getValue("code", row)
		department.parent = newDepartment.getValue("parent", row)
		department.manager = newDepartment.getValue("manager", row)

		return department
	}
	
	
	static def createDepartment(String [] newDepartment){
		Department department = new Department()

		department.name = newDepartment[0]
		department.code = newDepartment[1]
		department.parent = newDepartment[2]
		department.manager = newDepartment[3]

		return department
	}
}
