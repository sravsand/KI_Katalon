package capacity

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
import global.Validate as gVal
import global.Object as gObj
import global.WebDriverMethods as WebD


public class Validate {

	@Keyword
	static def capacityReportTable(String today, int colVal, int periodCount){

		gVal.objectTextwithParameter(Const.capacity + 'capacityResultsTableHeader', 'col', colVal, today)

		String tableXpath = gObj.getObjectRepositoryIdentifierValue(Const.capacity + 'capacityResultsTable', 'xpath')
		int colCnt = WebD.getTableColumnCount(tableXpath)
		gAct.compareIntAndReport(colCnt, periodCount + 2)

		gVal.objectTextwithTwoParameters(Const.capacity + 'capacityResultsTableBody', 'row', 1, 'col', 1, "Capacity")
		gVal.objectTextwithTwoParameters(Const.capacity + 'capacityResultsTableBody', 'row', 2, 'col', 1, "Demand")
		gVal.objectTextwithTwoParameters(Const.capacity + 'capacityResultsTableBody', 'row', 3, 'col', 1, "Demand - Confirmed")
		gVal.objectTextwithTwoParameters(Const.capacity + 'capacityResultsTableBody', 'row', 4, 'col', 1, "Demand - Provisional")
		gVal.objectTextwithTwoParameters(Const.capacity + 'capacityResultsTableBody', 'row', 5, 'col', 1, "Supply")
		gVal.objectTextwithTwoParameters(Const.capacity + 'capacityResultsTableBody', 'row', 6, 'col', 1, "Actuals")
	}
}
