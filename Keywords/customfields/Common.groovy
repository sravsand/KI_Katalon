package customfields

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import global.Action as gAct
import global.Object as gObj
import search.Validate as sVal
import keyedInProjects.Action as Act
import global.WebDriverMethods as WebD

public class Common {

	@Keyword
	static def createCustomField(String entity){
		gObj.buttonClickSync(Const.mainToolBar + "settings")
		gObj.buttonClickSync(Const.settingsMenu + "CustomFields")

		gObj.selectComboByLabelSync(Const.CustomFieldScreen + "select_CustomFieldType", entity)
		gAct.Wait(1)
	}


	@Keyword
	static def deleteAllCustomField(String tableName, String entity){
		createCustomField(entity)
		gAct.Wait(1)
		int pageViewCnt = WebD.getTableRowCount("//div[@id='" + tableName + "']") - 1

		for(int rowCnt = 1; rowCnt <= pageViewCnt; rowCnt ++){
			gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_specTable', tableName, "table", 1, "row")
			gObj.selectInlineOption(3)
			gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK_OldModal")
			gAct.Wait(1)
		}

		gAct.Wait(GVars.shortWait)
		gObj.buttonClickSync(Const.dashBoard + "img_Logo")
		gAct.Wait(GVars.shortWait)
	}
	
	
	@Keyword
	static def cleanUpCostCentreCustomfields(String costCentreCFName, String tableName) {
//		gAct.Wait(GVars.shortWait)
		createCustomField("Cost Centre")
		int rowNum = sVal.searchSpecificTableReturnRow(6, costCentreCFName, tableName)
		gObj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_specTable', tableName, "table", rowNum, "row")
		gObj.selectInlineOption(3)
		gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK_OldModal")
	}
}
