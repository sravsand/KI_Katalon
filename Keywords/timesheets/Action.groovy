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
import global.WebDriverMethods as webD
import global.Object as gObj

public class Action {

	@Keyword
	static def timesheetSave(){
		WebUI.click(findTestObject(Const.timesheet + 'button_Save'))

		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)
		if (elementVisible){
			WebUI.waitForElementNotPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
		}
	}

	@Keyword
	static def setTimesheetComboBox(String cell, String textSelector, String textValue, String option){
		WebUI.click(findTestObject(cell))
		WebUI.setText(findTestObject(textSelector), textValue)
		Act.Wait(1)
		WebUI.click(findTestObject(option))
	}


	@Keyword
	static def setTimesheetComboBoxAdditionalRow(String cell, String row, String textSelector, String textValue, String option){
		gObj.buttonClickSubSync(cell, row, "row")
		//get the child object
		def childs = webD.listObjectChildObjects("//div[@id='select2-drop']")
		String childId = childs[1].getAttribute("id")
		def childIdCell = childId.split("-")
		def cellNo = childIdCell[childIdCell.size()-1]

		gObj.setEditSubSync(textSelector, cellNo, "cell", textValue)
		Act.Wait(1)
		gObj.buttonClickSync(option)
	}


	@Keyword
	static def setTimesheetHoursWithNotes(String cell, String cellClicked, String value, String noteBoxObj, String Notes){
		if(value != ""){
			WebUI.click(findTestObject(cell))
			Act.Wait(1)
			WebUI.setText(findTestObject(cellClicked), value)
			WebUI.setText(findTestObject(noteBoxObj), Notes)
			WebUI.click(findTestObject(Const.timesheet + 'headerTotal'))
		}
	}

	@Keyword
	static def setTimesheetHoursWithNotesAdditionalRow(String cell, String row, String day, String cellClicked, String value, String noteBoxObj, String Notes){
		if(value != ""){
			WebUI.click(findTestObject(cell, [('row'): row, ('day'): day]))
			Act.Wait(1)
			WebUI.setText(findTestObject(cellClicked), value)
			WebUI.setText(findTestObject(noteBoxObj), Notes)
			WebUI.click(findTestObject(Const.timesheet + 'headerTotal'))
		}
	}
}
