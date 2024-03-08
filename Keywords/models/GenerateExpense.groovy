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
import models.Expense

public class GenerateExpense {

	static def createExpense(String[] newExpense){
		Expense expense = new Expense()

		expense.resource = newExpense[0]
		expense.date = newExpense[1]
		expense.department = newExpense[2]
		expense.costCentre = newExpense[3]
		expense.project = newExpense[4]
		expense.expenseType = newExpense[5]
		expense.category = newExpense[6]
		expense.reimbursable = newExpense[7]
		expense.gross = newExpense[8]
		expense.vat = newExpense[9]
		expense.net = newExpense[10]
		expense.chargeable = newExpense[11]
		expense.notes = newExpense[12]

		return expense
	}
}
