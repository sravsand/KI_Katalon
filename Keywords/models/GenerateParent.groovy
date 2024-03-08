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
import models.Parent

public class GenerateParent {

	static def createParentDD(def newParent, def row){
		Parent parent = new Parent()

		parent.name = newParent.getValue("name", row)
		parent.code = newParent.getValue("code", row)
		parent.workingTime = newParent.getValue("workingTime", row)
		parent.projectLevel = newParent.getValue("projectLevel", row)
		parent.parent = newParent.getValue("parent", row)
		parent.location = newParent.getValue("location", row)
		parent.department = newParent.getValue("department", row)
		parent.startDate = newParent.getValue("startDate", row)
		parent.endDate = newParent.getValue("endDate", row)

		return parent
	}
	
	
	static def createParent(String newParent){
		Parent parent = new Parent()

		parent.name = newParent[0]
		parent.code = newParent[1]
		parent.workingTime = newParent[2]
		parent.projectLevel = newParent[3]
		parent.parent = newParent[4]
		parent.location = newParent[5]
		parent.department = newParent[6]
		parent.startDate = newParent[7]
		parent.endDate = newParent[8]

		return parent
	}
}
