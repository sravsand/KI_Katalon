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
import models.Company

public class GenerateCompany {
	static def createCompanyDD(def newCompany, def row){
		Company company = new Company()

		company.name = newCompany.getValue("name", row)
		company.code = newCompany.getValue("code", row)
		company.customer = newCompany.getValue("customer", row)
		company.workingTime = newCompany.getValue("workingTime", row)
		company.projectLevel = newCompany.getValue("projectLevel", row)
		company.location = newCompany.getValue("location", row)
		company.department = newCompany.getValue("department", row)
		company.startDate = newCompany.getValue("startDate", row)
		company.endDate = newCompany.getValue("endDate", row)

		return company
	}


	static def createCompany(String[] newCompany){
		Company company = new Company()

		company.name = newCompany[0]
		company.code = newCompany[1]
		company.customer = newCompany[2]
		company.workingTime = newCompany[3]
		company.projectLevel = newCompany[4]
		company.location = newCompany[5]
		company.department = newCompany[6]
		company.startDate = newCompany[7]
		company.endDate = newCompany[8]

		return company
	}
}
