package timesheets

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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import global.Action as Act
import global.Common as Com


public class Navigate {
	static def timesheetPreviousWeekValidateDate(){
		WebUI.click(findTestObject(Const.timesheet + 'timesheetNavigateBack'))
		Act.Wait(1)
		String timesheetLastWeek = WebUI.getText(findTestObject(Const.timesheet + 'timesheetWeek'))

		def lastWeek = Com.weekDatesDifferentFormat(1, "back", "d MMM")
		Act.findSubstringInStringAndReport(timesheetLastWeek, lastWeek)
	}


	static def timesheetNextWeekValidateDate(){
		WebUI.click(findTestObject(Const.timesheet + 'timesheetNavigateForward'))
		Act.Wait(1)
		String timesheetNextWeek = WebUI.getText(findTestObject(Const.timesheet + 'timesheetWeek'))

		def nextWeek = Com.weekDatesDifferentFormat(1, "forward", "d MMM")
		Act.findSubstringInStringAndReport(timesheetNextWeek, nextWeek)
	}
}
