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
import global.Action as Acts
import global.Report as Rep
import global.Validate as gVal
import keyedInProjects.Constants as Const

public class Validate {
	@Keyword
	static def timesheetCellReadOnly(String cellObject){
		boolean readOnly = WebUI.getAttribute(findTestObject(cellObject), 'readonly')

		if(readOnly){
			Rep.pass("TimeSheet cell : " + cellObject + " is readonly as excpected.")
		}else{
			Rep.fail("TimeSheet cell : " + cellObject + " is not readonly.")
		}
	}


	@Keyword
	static def timesheetCellReadOnlyGeneric(String cellObject, String row, String day){
		boolean readOnly = WebUI.getAttribute(findTestObject(cellObject, [('row'): row, ('day'): day]), 'readonly')

		if(readOnly){
			Rep.pass("TimeSheet cell : row - " + row + ", day - " + day + " is readonly as expected.")
		}else{
			Rep.fail("TimeSheet cell : row - " + row + ", day - " + day + " is not readonly.")
		}
	}


	@Keyword
	static def timesheetCellEditable(String cellObject){
		boolean readOnly = WebUI.getAttribute(findTestObject(cellObject), 'readonly')

		if(!readOnly){
			Rep.pass("TimeSheet cell : " + cellObject + " is editable as expected.")
		}else{
			Rep.fail("TimeSheet cell : " + cellObject + " is not readonly.")
		}
	}


	@Keyword
	static def timesheetHours(float expected){
		String standardHours = WebUI.getText(findTestObject(Const.timesheet + 'timeHours'))
		standardHours = standardHours.replace(":", ".")
		float stdHours = standardHours.toFloat()
		String vacation = WebUI.getText(findTestObject(Const.timesheet + 'timeOffVacation'))
		vacation = vacation.replace(":", ".")
		float vac = vacation.toFloat()
		String ill = WebUI.getText(findTestObject(Const.timesheet + 'timeOffIll'))
		ill = ill.replace(":", ".")
		float illness = ill.toFloat()
		String maternity = WebUI.getText(findTestObject(Const.timesheet + 'timeOffMaternity'))
		maternity = maternity.replace(":", ".")
		float matnty = maternity.toFloat()

		float actTotal = stdHours + vac + illness + matnty

		String expectedHours = WebUI.getText(findTestObject(Const.timesheet + 'timeTotalExpected'))
		expectedHours = expectedHours.replace(":", ".")
		float expHours = expectedHours.toFloat()

		if(actTotal == expected){
			Rep.pass("Timesheet hours are : " + actTotal + " as expected.")
		}else{
			Rep.fail("Actual timesheet hours : " + actTotal + " do not match expected : " + expected)
		}
	}


	@Keyword
	static def timesheetCellHours(String cell, String expected){
		String hours = WebUI.getAttribute(findTestObject(cell), 'value')

		if(hours == expected){
			Rep.pass("Timesheet cell " + cell  + " hours are : " + hours + " as expected.")
		}else{
			Rep.fail("Actual timesheet cell " + cell  + " hours : " + hours + " do not match expected : " + expected)
		}
	}


	@Keyword
	static def timesheetPinnedRow(String row, String project, String activity, String task){
		String projectActual = WebUI.getText(findTestObject(Const.timesheet + 'selectProjectCell', [('row'): row]))
		Acts.compareStringAndReport(projectActual, project)
		String activityActual = WebUI.getText(findTestObject(Const.timesheet + 'selectActivityCell', [('row'): row]))
		Acts.compareStringAndReport(activityActual, activity)
		String taskActual = WebUI.getText(findTestObject(Const.timesheet + 'selectTaskCell', [('row'): row]))
		Acts.compareStringAndReport(taskActual, task)
	}
	
	
	@Keyword
	static def timesheetPinnedRowEdge(String row, String project, String activity, String task){
		String projectActual = WebUI.getText(findTestObject(Const.timesheet + 'selectProjectCell', [('row'): row]))
		Acts.findSubstringInStringAndReport(projectActual, project)
		String activityActual = WebUI.getText(findTestObject(Const.timesheet + 'selectActivityCell', [('row'): row]))
		Acts.findSubstringInStringAndReport(activityActual, activity)
		String taskActual = WebUI.getText(findTestObject(Const.timesheet + 'selectTaskCell', [('row'): row]))
		Acts.findSubstringInStringAndReport(taskActual, task)
	}
	
	
	@Keyword
	static def cellPopupValues(String row, String day, String expNotes, String expStatus){
		WebUI.click(findTestObject(Const.timesheet + 'input_DayCell', [('row'): row, ('day'): day]))
		gVal.objectAttributeValue(Const.timesheet + 'textarea_Notes', 'value', expNotes)		
		gVal.objectText(Const.timesheet + 'notes_SubmittedStatus', expStatus)		
//		gVal.objectAttributeValue(Const.timesheet + 'notes_SubmittedStatus', 'outerText', expStatus)	
		WebUI.click(findTestObject(Const.timesheet + 'close_NotesPopup'))
	}
	
}
