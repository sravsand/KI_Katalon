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
import models.LoginPG

public class GenerateLoginPG {

	static def createLoginPG(String[] newLoginPG){
		LoginPG loginpg = new LoginPG()

		loginpg.name = newLoginPG[0]
		loginpg.code = newLoginPG[1]
		loginpg.userId = newLoginPG[2]
		loginpg.ownResource = newLoginPG[3]
		loginpg.password = newLoginPG[4]
		loginpg.profile = newLoginPG[5]
		loginpg.group = newLoginPG[6]

		return loginpg
	}
}
