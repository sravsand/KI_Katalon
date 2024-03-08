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

import models.Role
import internal.GlobalVariable

public class GenerateRole {

	static def createNewRole(String[] newRole){
		Role role = new Role()

		role.name = newRole[0]
		role.code = newRole[1]
		role.department = newRole[2]
		role.location = newRole[3]
		role.workingTime = newRole[4]
		role.costCentre = newRole[5]
		role.cost = newRole[6]
		role.charge = newRole[7]
		role.notes = newRole[8]

		return role
	}
	
	
	static def createNewRoleDD(def newRole, def row){
		Role role = new Role()

		role.name = newRole.getValue("name", row)
		role.code = newRole.getValue("code", row)
		role.department = newRole.getValue("department", row)
		role.location = newRole.getValue("location", row)
		role.workingTime = newRole.getValue("workingTime", row)
		role.costCentre = newRole.getValue("costCentre", row)
		role.cost = newRole.getValue("cost", row)
		role.charge = newRole.getValue("charge", row)
		role.notes = newRole.getValue("notes", row)

		return role
	}
}
