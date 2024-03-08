package models

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List

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
import models.TimesheetRow
import models.Timesheet
import models.Hours

public class GenerateTimesheetRow {

	static def createTimesheetRow(String[] newTimesheet, String[] newHours){
		TimesheetRow timesheetRow = new TimesheetRow()
	
		timesheetRow.project = newTimesheet[0]
		timesheetRow.activity = newTimesheet[1]
		timesheetRow.task = newTimesheet[2]

		timesheetRow.hours.mon = newHours[0]
		timesheetRow.hours.monNotes = newHours[1]
		timesheetRow.hours.tues = newHours[2]
		timesheetRow.hours.tuesNotes = newHours[3]
		timesheetRow.hours.weds = newHours[4]
		timesheetRow.hours.wedsNotes = newHours[5]
		timesheetRow.hours.thurs = newHours[6]
		timesheetRow.hours.thursNotes = newHours[7]
		timesheetRow.hours.fri = newHours[8]
		timesheetRow.hours.friNotes = newHours[9]
		timesheetRow.hours.sat = newHours[8]
		timesheetRow.hours.satNotes = newHours[9]
		timesheetRow.hours.sun = newHours[10]
		timesheetRow.hours.sunNotes = newHours[11]

		return timesheetRow
	}
}
