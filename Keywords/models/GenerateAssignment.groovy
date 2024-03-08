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
import models.Assignment

public class GenerateAssignment {
	static def createAssignment(String[] newAssignment){
		Assignment assignment = new Assignment()

		assignment.project = newAssignment[0]
		assignment.task = newAssignment[1]
		assignment.description = newAssignment[2]
		assignment.resource = newAssignment[3]
		assignment.department = newAssignment[4]
		assignment.startDate = newAssignment[5]
		assignment.startTime = newAssignment[6]
		assignment.endDate = newAssignment[7]
		assignment.endTime = newAssignment[8]
		assignment.effort = newAssignment[9]
		
		return assignment
	}
}
