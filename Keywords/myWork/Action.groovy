package myWork

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
import global.Action as gAct
import myWork.Validate as mVal
import global.WebDriverMethods as WebD
import global.Report as Rep
import global.Common as Com

public class Action {

	@Keyword
	static def changeLayoutAndValidate_TSplit(){
		WebUI.click(findTestObject(Const.configureViewMenu + 'a_Configure Current View'))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.layouts + 'button_Layouts'))
		WebUI.click(findTestObject(Const.layouts + 'layout_TSplit'))
		gAct.Wait(1)

		mVal.layout_TSplit()
	}

	@Keyword
	static def selectTimesheetForApproval(String subDate, String resourceName){
		int intCnt
		int rowCnt = WebD.getTableRowCount("//div[@id='c1f61d49-c821-4af8-9410-410962911a4b']/div[6]/table")
		boolean timesheetFound = false
		for(intCnt = 1; intCnt < rowCnt; intCnt ++){
			String submitted = WebUI.getText(findTestObject(Const.myWork + 'approval_Cell', [('row'): intCnt, ('col') : 10]))
			String resource = WebUI.getText(findTestObject(Const.myWork + 'approval_Cell', [('row'): intCnt, ('col') : 6]))

			if((submitted == subDate)&&(resource == resourceName)){
				timesheetFound = true
				break
			}
		}

		if(timesheetFound == false){
			Rep.fail("Correct timesheet for approval not found")
		}

		if(GVars.browser == "Internet explorer"){
			Com.ieSync()
			WebUI.focus(findTestObject(Const.myWork + 'timesheetTotal', [('row'): intCnt]))
			//			WebUI.click(findTestObject(Const.myWork + 'timesheetTotal', [('row'): intCnt]))
		}

		WebUI.click(findTestObject(Const.myWork + 'timesheetTotal', [('row'): intCnt]))

		gAct.Wait(1)
	}
}
