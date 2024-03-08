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
import models.Risk

public class GenerateRisk {

	static def createNewRisk(String[] newRisk){
		Risk risk = new Risk()

		risk.title = newRisk[0]
		risk.project = newRisk[1]
		risk.owner = newRisk[2]
		risk.status = newRisk[3]
		risk.dateIdentified = newRisk[4]
		risk.impactDate = newRisk[5]
		risk.publishTo = newRisk[6]
		risk.probability = newRisk[7]
		risk.impact = newRisk[8]
		risk.severity = newRisk[9]
		risk.resolutionDate = newRisk[10]
		risk.description = newRisk[11]
		risk.code = newRisk[12]

		return risk
	}
}




