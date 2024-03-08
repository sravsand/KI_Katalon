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
import models.LoginGroupWithTabs

public class GenerateLoginGroupWithTabs {

	static def createLoginGroupWithTabs(String[] newLoginGroupWithTabs){
		LoginGroupWithTabs loginGroupWithTabs = new LoginGroupWithTabs()

		loginGroupWithTabs.name = newLoginGroupWithTabs[0]
		loginGroupWithTabs.code = newLoginGroupWithTabs[1]	
		loginGroupWithTabs.login = newLoginGroupWithTabs[2]
		loginGroupWithTabs.report = newLoginGroupWithTabs[3]
		loginGroupWithTabs.filter = newLoginGroupWithTabs[4]
		loginGroupWithTabs.actionView = newLoginGroupWithTabs[5]
		loginGroupWithTabs.projectLevels = newLoginGroupWithTabs[6]
		loginGroupWithTabs.notification = newLoginGroupWithTabs[7]
		
		return loginGroupWithTabs
	}
}
