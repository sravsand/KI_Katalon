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
import models.Hours

public class GenerateHours {

	static def createHours(List<String> newHours){
		Hours hours = new Hours()

		hours.mon = newHours[0]
		hours.monNotes = newHours[1]
		hours.tues = newHours[2]
		hours.tuesNotes = newHours[3]
		hours.weds = newHours[4]
		hours.wedsNotes = newHours[5]
		hours.thurs = newHours[6]
		hours.thursNotes = newHours[7]
		hours.fri = newHours[8]
		hours.friNotes = newHours[9]
		hours.sat = newHours[10]
		hours.satNotes = newHours[11]
		hours.sun = newHours[12]
		hours.sunNotes = newHours[13]

		return hours
	}
}
