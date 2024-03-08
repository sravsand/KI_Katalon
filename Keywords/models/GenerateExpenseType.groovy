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
import models.ExpenseType

public class GenerateExpenseType {

	static def createExpenseType(String[] newExpenseType){
		ExpenseType expenseType = new ExpenseType()

		expenseType.name = newExpenseType[0]
		expenseType.code = newExpenseType[1]
		expenseType.currency = newExpenseType[2]
		expenseType.multiplyExpChg = newExpenseType[3]
		expenseType.Receipt = newExpenseType[4]
		expenseType.rate = newExpenseType[5]

		return expenseType
	}
}