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

import internal.GlobalVariable as gVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import global.Action as gAct
import global.Report as Rep
import global.Common as Com

public class Action {

	@Keyword
	static def getCellDateText(int subTask, int row, int col){
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		String cellValue = WebUI.getText(findTestObject(Const.ganttTable + 'ganttItem', [('sub'): subTask, ('row'): row, ('col'): col]))
		WebUI.click(findTestObject(Const.ganttTable + 'ganttMainTask',  [('col'): 3]))

		return cellValue
	}

	@Keyword
	static def handleCheckOutWindow(){
		boolean menuItemExists = WebUI.waitForElementClickable(findTestObject(Const.CheckOutTask + 'button_Open'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		if(menuItemExists){
			gAct.Wait(2)
			Act.checkOutOrViewTask(Const.CheckOutTask + 'button_Open')
			String Url = WebUI.getUrl()
			gAct.findSubstringInStringAndReport(Url, "MVC-PRL1-TASKS?")

			if(gVars.browser == "MicrosoftEdge"){
				gAct.Wait(2)
			}
		}
	}


	@Keyword
	static def handleCurrentlyEditedWindow(String buttonChoice){
		boolean popupExists = WebUI.waitForElementClickable(findTestObject(Const.editPopUp + 'button_Yes'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		if(popupExists){
			String popUpText = WebUI.getText(findTestObject(Const.editPopUp + 'editingWarning'))
			Rep.info(popUpText)
			gAct.Wait(2)

			if(buttonChoice == "Yes"){
				WebUI.click(findTestObject(Const.editPopUp + 'button_Yes'))
			}else{
				WebUI.click(findTestObject(Const.editPopUp + 'button_No'))
			}
			gAct.Wait(2)
		}
	}


	@Keyword
	static def handleSaveErrorWindow(){
		boolean saveError = WebUI.verifyElementPresent(findTestObject(Const.ganttToolbar + 'saveErrorMessage'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		if(saveError){
			String errorText = WebUI.getText(findTestObject(Const.ganttToolbar + 'saveErrorMessage'))
			Rep.info("Save error message : " + errorText)
			WebUI.click(findTestObject(Const.ganttToolbar + 'button_OK'))
			gAct.Wait(gVars.shortWait)
		}
	}

	@Keyword
	static def handleSaveErrorWindow_NotExpected(){
		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.ganttToolbar + 'saveErrorMessage'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		if(!saveError){
			String errorText = WebUI.getText(findTestObject(Const.ganttToolbar + 'saveErrorMessage'))
			Rep.info("Save error message : " + errorText)
			WebUI.click(findTestObject(Const.ganttToolbar + 'button_OK'))
			gAct.Wait(gVars.shortWait)
		}
	}


	@Keyword
	static def increaseDate(String originalDate, int increaseAmt, String format){
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
		SimpleDateFormat newformatter = new SimpleDateFormat(format);

		Date dt = formatter.parse(originalDate);
		String originalDateIncreased = Com.increaseWorkingCalendar(dt, increaseAmt)

		Date newDT = formatter.parse(originalDateIncreased)

		String DateIncreased = newformatter.format(newDT);

		return DateIncreased
	}


	@Keyword
	static def decreaseDate(String originalDate, int decreaseAmt, String format){
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
		SimpleDateFormat newformatter = new SimpleDateFormat(format);

		Date dt = formatter.parse(originalDate);
		String originalDateDecreased = Com.decreaseWorkingCalendar(dt, decreaseAmt)

		Date newDT = formatter.parse(originalDateDecreased)

		String DateDecreased = newformatter.format(newDT);

		return DateDecreased
	}


	@Keyword
	static def indentSubTask(int row, int col){
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttSubTask',  [('row'): row, ('col'): col]))
		WebUI.clickOffset(findTestObject(Const.ganttTable + 'selectSubTask'), 35, 15)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttToolbar + 'indent'))
	}


	@Keyword
	static def outdentSubTask(int sub, int row, int col){
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.ganttTable + 'ganttItem',  [('sub'): sub, ('row'): row, ('col'): col]))
		WebUI.clickOffset(findTestObject(Const.ganttTable + 'selectSubTask'), 35, 15)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.ganttToolbar + 'outdent'))
	}


	@Keyword
	static def checkoutCurrentProject(){
		Com.ieSync()
		WebUI.waitForElementVisible(findTestObject(Const.ganttTable + 'ganttChart'), 15, FailureHandling.OPTIONAL)
		gAct.Wait(2)
		boolean chkOutExist = WebUI.verifyElementPresent(findTestObject(Const.ganttToolbar + 'checkIn'), 5, FailureHandling.OPTIONAL)
		if(!chkOutExist){
			WebUI.click(findTestObject(Const.ganttToolbar + 'checkOut'))
			handleCurrentlyEditedWindow("Yes")
			handleCheckOutWindow()
			WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}


	@Keyword
	static def refresh(){
		WebUI.refresh()
		gAct.Wait(2)
		WebUI.waitForElementNotPresent(findTestObject(Const.gantt + 'Initialising Task Planning'), 10, FailureHandling.CONTINUE_ON_FAILURE)
	}
}
