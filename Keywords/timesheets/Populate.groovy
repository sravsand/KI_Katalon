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
import timesheets.Action as Act
import global.Action as GAct
import models.Timesheet

public class Populate {

	@Keyword
	static def CreateNewRow(String projectName, String activityName, String taskName){
		Act.setTimesheetComboBox(Const.timesheet + 'selectProject', Const.timesheet + 'input_Project', projectName, Const.timesheet + 'projectChoice')
		Act.setTimesheetComboBox(Const.timesheet + 'selectActivity', Const.timesheet + 'input_Activity', activityName, Const.timesheet + 'activityChoice')
		Act.setTimesheetComboBox(Const.timesheet + 'selectTask', Const.timesheet + 'input_Task', taskName, Const.timesheet + 'taskChoice')
	}


	@Keyword
	static def StandardHours(String [] hours, String [] notes){
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_Day1', Const.timesheet + 'input_DayTextPopInput', hours[0], Const.timesheet + 'textarea_Notes', notes[0])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_Day2', Const.timesheet + 'input_DayTextPopInput', hours[1], Const.timesheet + 'textarea_Notes', notes[1])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_Day3', Const.timesheet + 'input_DayTextPopInput', hours[2], Const.timesheet + 'textarea_Notes', notes[2])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_Day4', Const.timesheet + 'input_DayTextPopInput', hours[3], Const.timesheet + 'textarea_Notes', notes[3])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_Day5', Const.timesheet + 'input_DayTextPopInput', hours[4], Const.timesheet + 'textarea_Notes', notes[4])
	}


	@Keyword
	static def VacationHours(String [] hours, String [] notes){
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay1', Const.timesheet + 'input_DayTextPopInput', hours[0], Const.timesheet + 'textarea_Notes', notes[0])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay2', Const.timesheet + 'input_DayTextPopInput', hours[1], Const.timesheet + 'textarea_Notes', notes[1])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay3', Const.timesheet + 'input_DayTextPopInput', hours[2], Const.timesheet + 'textarea_Notes', notes[2])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay4', Const.timesheet + 'input_DayTextPopInput', hours[3], Const.timesheet + 'textarea_Notes', notes[3])
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay4', Const.timesheet + 'input_DayTextPopInput', hours[4], Const.timesheet + 'textarea_Notes', notes[4])
	}


	@Keyword
	static def CreateAdditionalRow(String row, String projectName, String activityName, String taskName){
		Act.setTimesheetComboBoxAdditionalRow(Const.timesheet + 'selectProjectCell', row, Const.timesheet + 'tsheetDropdownCell', projectName, Const.timesheet + 'projectChoice')
		Act.setTimesheetComboBoxAdditionalRow(Const.timesheet + 'selectActivityCell', row, Const.timesheet + 'tsheetDropdownCell', activityName, Const.timesheet + 'activityChoice')

		if(taskName != ""){
			Act.setTimesheetComboBoxAdditionalRow(Const.timesheet + 'selectTaskCell', row, Const.timesheet + 'tsheetDropdownCell', taskName, Const.timesheet + 'taskChoice')
		}
	}


	@Keyword
	static def StandardHoursAdditionalRow(String row, String [] hours, String [] notes){
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "0", Const.timesheet + 'input_DayTextPopInput', hours[0], Const.timesheet + 'textarea_Notes', notes[0])
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "1", Const.timesheet + 'input_DayTextPopInput', hours[1], Const.timesheet + 'textarea_Notes', notes[1])
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "2", Const.timesheet + 'input_DayTextPopInput', hours[2], Const.timesheet + 'textarea_Notes', notes[2])
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "3", Const.timesheet + 'input_DayTextPopInput', hours[3], Const.timesheet + 'textarea_Notes', notes[3])
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "4", Const.timesheet + 'input_DayTextPopInput', hours[4], Const.timesheet + 'textarea_Notes', notes[4])
	}


	@Keyword
	static def StandardHoursAdditionalRowModel(Timesheet timesheet, String row, int modelRow){
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "0", Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.mon, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.monNotes)
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "1", Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.tues, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.tuesNotes)
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "2", Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.weds, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.wedsNotes)
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "3", Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.thurs, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.thursNotes)
		Act.setTimesheetHoursWithNotesAdditionalRow(Const.timesheet + 'input_DayCell', row, "4", Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.fri, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.friNotes)
	}


	@Keyword
	static def VacationHoursModel(Timesheet timesheet, int modelRow){
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay1', Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.mon, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.monNotes)
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay2', Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.tues, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.tuesNotes)
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay3', Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.weds, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.wedsNotes)
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay4', Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.thurs, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.thursNotes)
		Act.setTimesheetHoursWithNotes(Const.timesheet + 'input_VacationDay4', Const.timesheet + 'input_DayTextPopInput', timesheet.row[modelRow].hours.fri, Const.timesheet + 'textarea_Notes', timesheet.row[modelRow].hours.friNotes)
	}
}
