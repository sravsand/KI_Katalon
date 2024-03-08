package administration

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
import global.WebDriverMethods as WebD
import global.Action as gAct
import global.Report as Rep
import global.Object as gObj


public class Action {

	@Keyword
	static def openLoginProfile(String loginProfile){
		String rowProfile
		boolean profileLocated = false
		String pagesCount = gObj.getEditTextSync(Const.loginProfiles + 'displayCount_Pagination')
		String[] pages = pagesCount.split(" ")
		int itemCnt = pages[5].toInteger()

		int pageNo = pages[5].toInteger()/10
		int res = pages[5].toInteger()%10

		if(res > 0){
			pageNo = pageNo + 1
		}

		for(int intPageCnt = 1; intPageCnt <= pageNo; intPageCnt ++){
			int rowCnt = WebD.getTableRowCountByObject(Const.loginProfiles + 'LoginProfilesTable')
			for(int intRowCnt = 1; intRowCnt < rowCnt; intRowCnt ++){
				rowProfile = gObj.getEditTextDblSubIntPosSync(Const.loginProfiles + 'LoginProfileCell', intRowCnt, "row", 2, "col")

				if(rowProfile == loginProfile){
					gObj.buttonClickSubIntPosSync(Const.loginProfiles + 'LoginProfilesInlineMenu', intRowCnt, "row")
					gObj.selectInlineOption(1)
					gAct.Wait(2)
					profileLocated = true
					break
				}
			}
			if(profileLocated){
				break
			}

			gObj.buttonClickSync(Const.loginProfiles + 'a_Next')
			gAct.Wait(1)
		}

		if(!profileLocated){
			Rep.fail("login profile " + loginProfile + " was not located.")
		}
	}


	@Keyword
	static def verifyMaximumEditLength(String objName, String inputVal, int cellSize){
		String ExpValue = inputVal.substring(0,cellSize)
		gObj.setEditSync(objName, inputVal)

		String inputText = gObj.getAttributeSync(objName, 'value')
		def strLen = inputText.length()
		gAct.compareIntAndReport(strLen, cellSize)
		gAct.compareStringAndReport(inputText, ExpValue)
	}
}
