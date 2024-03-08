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
import models.ExpenseWithCurrency

public class GenerateExpenseWithCurrency {

	static def createExpenseWithCurrency(String[] CurrExpense){
		ExpenseWithCurrency expense = new ExpenseWithCurrency()

		expense.resource = CurrExpense[0]
		expense.date = CurrExpense[1]
		expense.department = CurrExpense[2]
		expense.costCentre = CurrExpense[3]
		expense.project = CurrExpense[4]
		expense.expenseType = CurrExpense[5]
		expense.category = CurrExpense[6]
		expense.reimbursable = CurrExpense[7]
		expense.gross = CurrExpense[8]
		expense.vat = CurrExpense[9]
		expense.net = CurrExpense[10]
		expense.chargeable = CurrExpense[11]
		expense.notes = CurrExpense[12]
		expense.currency = CurrExpense[13]

		return expense
	}
}
