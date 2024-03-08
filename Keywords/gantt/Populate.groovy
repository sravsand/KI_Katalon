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

import internal.GlobalVariable
import keyedInProjects.Constants as Const
import global.Action as gAct

public class Populate {

	@Keyword
	static def cell(int subTask, int row, int col, String value){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttCellInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}


	@Keyword
	static def dateCell(int subTask, int row, int col, String value){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttCellDateInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}


	@Keyword
	static def newTask(int row, int col, String value){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttNewRow', [('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttNewTaskInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}


	@Keyword
	static def newAssignment(int row, int col, String value){
		WebUI.click(findTestObject(Const.ganttTable + 'addAssignment'))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttTable + 'assignmentAddResource', [('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'assignmentCellInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'assignmentSuggestion'))
	}


	@Keyword
	static def updateTask(int row, int col, String value){
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttNewTaskInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}

	@Keyword
	static def updateCell(int subTask, int row, int col, String value){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttNewTaskInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}


	@Keyword
	static def updateMainTaskDate(int row, int col, String value){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttTask', [('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttTask', [('row'): row, ('col'): col]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttCellDateInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}


	@Keyword
	static def newTaskBelow(int preRow, int preCol, String value){
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask',  [('row'): preRow, ('col'): preCol]))
		WebUI.clickOffset(findTestObject(Const.ganttTable + 'selectSubTask'), 35, 15)

		WebUI.click(findTestObject(Const.ganttToolbar + "insertRowBelow"))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask', [('row'): preRow + 1, ('col'): preCol]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttNewTaskInput'), value)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}


	@Keyword
	static def updateTaskAssignment(int row, String newValue){
		WebUI.click(findTestObject(Const.ganttAssignmentTable + 'assignmentTask', [('row'): row]))
		WebUI.click(findTestObject(Const.ganttAssignmentTable + 'assignmentTask', [('row'): row]))
		WebUI.setText(findTestObject(Const.ganttTable + 'ganttNewTaskInput'), newValue)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))
	}
}
