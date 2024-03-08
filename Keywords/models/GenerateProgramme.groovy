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
import models.Programme

public class GenerateProgramme {

	static def createProgrammeDD(def newProgramme, def row){
		Programme programme = new Programme()

		programme.name = newProgramme.getValue("name", row)
		programme.code = newProgramme.getValue("code", row)
		programme.workingTime = newProgramme.getValue("workingTime", row)
		programme.projectLevel = newProgramme.getValue("projectLevel", row)
		programme.parent = newProgramme.getValue("parent", row)
		programme.location = newProgramme.getValue("location", row)
		programme.department = newProgramme.getValue("department", row)
		programme.startDate = newProgramme.getValue("startDate", row)
		programme.endDate = newProgramme.getValue("endDate", row)
		return programme
	}
	
	
	static def createProgramme(String[] newProgramme){
		Programme programme = new Programme()

		programme.name = newProgramme[0]
		programme.code = newProgramme[1]
		programme.workingTime = newProgramme[2]
		programme.projectLevel = newProgramme[3]
		programme.parent = newProgramme[4]
		programme.location = newProgramme[5]
		programme.department = newProgramme[6]
		programme.startDate = newProgramme[7]
		programme.endDate = newProgramme[8]
		
		return programme
	}
}
