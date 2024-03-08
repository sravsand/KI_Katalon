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
import models.Charge

public class GenerateCharge {

	static def createChargeDD(def newCharge, def row){
		Charge charge = new Charge()

		charge.name = newCharge.getValue("name", row)
		charge.code = newCharge.getValue("code", row)
		charge.rate = newCharge.getValue("rate", row)

		return charge
	}


	static def createCharge(String[] newCharge){
		Charge charge = new Charge()

		charge.name = newCharge[0]
		charge.code = newCharge[1]
		charge.rate = newCharge[2]
		charge.currency = newCharge[3]

		return charge
	}
}
