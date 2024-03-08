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
import models.Supply

public class GenerateSupply {
	
	static def createSupply(String[] newSupply){
		Supply supply = new Supply()

		supply.project = newSupply[0]
		supply.resource = newSupply[1]
		supply.department = newSupply[2]
		supply.role = newSupply[3]
		supply.supplywk1 = newSupply[4]
		supply.supplywk2 = newSupply[5]
		supply.supplywk3 = newSupply[6]
		supply.supplywk4 = newSupply[7]
		
		return supply
	}
	
}
