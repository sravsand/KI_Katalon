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
import models.Customer
import models.Address

public class GenerateCustomer {

	static def createCustomerDD(def newCustomer, def row){
		Customer customer = new Customer()

		customer.name = newCustomer.getValue("name", row)
		customer.address.address1 = newCustomer.getValue("address1", row)
		customer.address.address2 = newCustomer.getValue("address2", row)
		customer.address.city = newCustomer.getValue("city", row)
		customer.address.state = newCustomer.getValue("state", row)
		customer.address.country = newCustomer.getValue("country", row)
		customer.address.postCode = newCustomer.getValue("postCode", row)
		customer.telephone = newCustomer.getValue("telephone", row)
		customer.fax = newCustomer.getValue("fax", row)
		customer.website = newCustomer.getValue("website", row)
		customer.email = newCustomer.getValue("email", row)
		customer.customerGroup = newCustomer.getValue("customerGroup", row)

		return customer
	}


	static def createCustomer(String[] newCustomer){
		Customer customer = new Customer()

		customer.name = newCustomer[0]
		customer.address.address1 = newCustomer[1]
		customer.address.address2 = newCustomer[2]
		customer.address.city = newCustomer[3]
		customer.address.state = newCustomer[4]
		customer.address.country = newCustomer[5]
		customer.address.postCode = newCustomer[6]
		customer.telephone = newCustomer[7]
		customer.fax = newCustomer[8]
		customer.website = newCustomer[9]
		customer.email = newCustomer[10]
		customer.customerGroup = newCustomer[11]

		return customer
	}
	
	
	static def createCustomerKIP4(def newCustomer){
		Customer customer = new Customer()

		customer.name = newCustomer[0]
		customer.code = newCustomer[1]
		customer.address = newCustomer[2]
//		customer.address.address2 = newCustomer[3]
//		customer.address.city = newCustomer[4]
//		customer.address.state = newCustomer[5]
//		customer.address.country = newCustomer[6]
//		customer.address.postCode = newCustomer[7]
		customer.telephone = newCustomer[3]
		customer.fax = newCustomer[4]
		customer.website = newCustomer[5]
		customer.email = newCustomer[6]
		customer.customerGroup = newCustomer[7]

		return customer
	}
}
