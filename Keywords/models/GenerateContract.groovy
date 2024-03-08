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
import models.Contract

public class GenerateContract {

	static def createContract(String[] newContract){
		Contract contract = new Contract()

		contract.status = newContract[0]
		contract.project = newContract[1]
		contract.name = newContract[2]
		contract.custRef = newContract[3]
		contract.date = newContract[4]
		contract.useBilling = newContract[5]
		contract.contractMan = newContract[6]
		contract.billTimeIn = newContract[7]
		contract.dayLength = newContract[8]
		contract.notes = newContract[9]
		
		
		return contract
	}
}
