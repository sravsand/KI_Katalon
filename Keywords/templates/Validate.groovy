package templates

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
import global.WebDriverMethods as WebD
import keyedInProjects.Constants as Const
import global.Action as gAct
import global.Report as Rep
import global.Common as Com

public class Validate {

	@Keyword
	static def templateColumnSetToYes(String newTemplate, int nameRow, int valRow){
		String rowText, templateValue
		int rowCnt = WebD.getTableRowCountByObject(Const.templates + 'filterTable')

		for(int intRowCnt = 1; intRowCnt < rowCnt; intRowCnt ++){
			rowText = WebUI.getText(findTestObject(Const.templates + 'filterTableCell', [('row'): intRowCnt, ('col'): nameRow]))

			if(rowText == newTemplate){
				templateValue = WebUI.getText(findTestObject(Const.templates + 'filterTableCell', [('row'): intRowCnt, ('col'): valRow]))
				break
			}
		}
		
		gAct.compareStringAndReport(templateValue, "Yes")
	}
	
	
	@Keyword
	static def projectColumnSetToNo(String projectName, int nameRow, int valRow){
		String rowProfile
		boolean projectLocated = false
		String pagesCount = WebUI.getText(findTestObject(Const.templates + 'displayCount_Pagination'))
		String[] pages = pagesCount.split(" ")
		int itemCnt = pages[5].toInteger()

		int pageNo = pages[5].toInteger()/10
		int res = pages[5].toInteger()%10

		if(res > 0){
			pageNo = pageNo + 1
		}

		for(int intPageCnt = 1; intPageCnt <= pageNo; intPageCnt ++){
			int rowCnt = WebD.getTableRowCountByObject(Const.templates + 'filterTable')
			for(int intRowCnt = 1; intRowCnt < rowCnt; intRowCnt ++){
				rowProfile = WebUI.getText(findTestObject(Const.templates + 'filterTableCell', [('row'): intRowCnt, ('col'): nameRow]))
				
				if(rowProfile == projectName){
					String rowTemplate = WebUI.getText(findTestObject(Const.templates + 'filterTableCell', [('row'): intRowCnt, ('col'): valRow]))
					gAct.findSubstringInStringAndReport(rowTemplate, "No")
					projectLocated = true
					break
				}
			}
			if(projectLocated){
				break
			}

			WebUI.click(findTestObject(Const.loginProfiles + 'a_Next'))
			gAct.Wait(1)
		}

		if(!projectLocated){
			Rep.fail("Project " + projectName + " was not located.")
		}
	}
	
	
}
