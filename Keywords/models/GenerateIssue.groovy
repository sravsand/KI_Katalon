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
import models.Issue

public class GenerateIssue {

	static def createNewIssue(String[] newIssue){
		Issue issue = new Issue()

		issue.title = newIssue[0]
		issue.project = newIssue[1]
		issue.owner = newIssue[2]
		issue.status = newIssue[3]
		issue.dateCreated = newIssue[4]
		issue.assocRisk = newIssue[5]
		issue.publishTo = newIssue[6]
		issue.impact = newIssue[7]
		issue.resolutionProgress = newIssue[8]
		issue.description = newIssue[9]
		issue.code = newIssue[10]

		return issue
	}
}

