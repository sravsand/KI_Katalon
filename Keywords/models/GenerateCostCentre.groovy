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
import models.CostCentre

public class GenerateCostCentre {

	static def createCostCentreDD(def newCostCentre, def row){
		CostCentre costCentre = new CostCentre()

		costCentre.name = newCostCentre.getValue("name", row)
		costCentre.code = newCostCentre.getValue("code", row)
		costCentre.notes = newCostCentre.getValue("notes", row)

		return costCentre
	}


	static def createCostCentre(String[] newCostCentre){
		CostCentre costCentre = new CostCentre()

		costCentre.name = newCostCentre[0]
		costCentre.code = newCostCentre[1]
		costCentre.notes = newCostCentre[2]

		return costCentre
	}
}
