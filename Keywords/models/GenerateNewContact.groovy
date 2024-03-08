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
import models.NewContact
import models.Address

public class GenerateNewContact {

	static def createNewContact(String[] newContact, Address address){
		NewContact contact = new NewContact()

		contact.address = address
		contact.title = newContact[0]
		contact.firstName = newContact[1]
		contact.middleName = newContact[2]
		contact.surname = newContact[3]
		contact.customer = newContact[4]
		contact.position = newContact[5]
		contact.department = newContact[6]
		contact.telephone = newContact[7]
		contact.mobile = newContact[8]
		contact.fax = newContact[9]
		contact.purpose = newContact[10]
		contact.email = newContact[11]

		return contact
	}
}