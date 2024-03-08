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
import models.Address

public class GenerateAddress {

	static def createAddresseDD(def newAddress, def row){
		Address address = new Address()

		address.address1 = newAddress.getValue("address1", row)
		address.address2 = newAddress.getValue("address2", row)
		address.city = newAddress.getValue("city", row)
		address.state = newAddress.getValue("state", row)
		address.country = newAddress.getValue("country", row)
		address.postCode = newAddress.getValue("postCode", row)

		return address
	}

	static def createAddress(String[] newAddress){
		Address address = new Address()

		address.address1 = newAddress[0]
		address.address2 = newAddress[1]
		address.city = newAddress[2]
		address.state = newAddress[3]
		address.country = newAddress[4]
		address.postCode = newAddress[5]

		return address
	}
}
