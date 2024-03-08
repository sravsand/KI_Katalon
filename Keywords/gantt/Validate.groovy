package gantt

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
import java.text.SimpleDateFormat

import internal.GlobalVariable

import global.Common as Com
import global.Action as gAct
import gantt.Action as GanttAct
import keyedInProjects.Constants as Const


public class Validate {

	@Keyword
	static def dateIncrease(String originalDate, String newDate, int increaseAmt){
		String originalDateIncreased = GanttAct.increaseDate(originalDate, increaseAmt, "EEE, dd MMM yyyy") //as start date included
		gAct.compareStringAndReport(originalDateIncreased, newDate)
	}


	@Keyword
	static def assignmentAdded(int row, int col, String expValue){
		def cellValue = WebUI.getText(findTestObject(Const.ganttTable + 'assignmentAddResource', [('row'): row, ('col'): col]))
		gAct.compareStringAndReport(cellValue, expValue)
	}


	@Keyword
	static def subTaskValue(int sub, int row, int col, String expValue){
		String actValue = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): sub, ('row'): row, ('col'): col]))
		gAct.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def subItemValue(int task, int sub, int row, int col, String expValue){
		String actValue = WebUI.getText(findTestObject(Const.ganttTable + 'ganttSubItem', [('task'): task, ('sub'): sub, ('row'): row, ('col'): col]))
		gAct.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def taskValue(int row, int col, String expValue){
		String actValue = WebUI.getText(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): row, ('col'): col]))
		gAct.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def topLevelTaskValue(int row, int col, String expValue){
		String actValue = WebUI.getText(findTestObject(Const.ganttTable + 'ganttTask', [('row'): row, ('col'): col]))
		gAct.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def oneParamObjectValue(String objectValue, String param, int value, String expValue){
		String actValue = WebUI.getText(findTestObject(objectValue, [(param): value]))
		gAct.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def twoParamObjectValue(String objectValue, String param1, int value1, String param2, int value2, String expValue){
		String actValue = WebUI.getText(findTestObject(objectValue, [(param1): value1, (param2): value2]))
		gAct.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def taskAssignmentValue(int row, int col, int assignmentRow, String expValue){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask',  [('row'): row, ('col'): col]))

		String accValue = WebUI.getText(findTestObject(Const.ganttAssignmentTable + 'assignmentTask', [('row'): assignmentRow]))
		gAct.compareStringAndReport(accValue, expValue)
	}
}
