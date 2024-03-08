package search

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
import global.WebDriverMethods as WebD
import global.Object as gObj
import global.Action as gAct
import global.Common as gCom
import global.Report as Rep
import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const

public class Action {

	@Keyword
	static def searchTableGetColumnValues(int columnNo){
		def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
		int pageViewCnt = pageView.toInteger()
		String tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
		String[] tblelgtSplit = tbleLgt.split()
		int tblNo = tblelgtSplit[5].toInteger()
		int pgCnt = tblNo / pageViewCnt
		int pgCntRem = tblNo % pageViewCnt

		String[] DescriptionArray = new String[tblNo]
		int arrCnt = 0
		int bckCnt = 0
		for(int intPg = 0; intPg < pgCnt; intPg ++){
			for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
				DescriptionArray[arrCnt] = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")
				arrCnt ++
			}
			gObj.buttonClickSync(Const.columnPicker + "nextPage")
		}

		if(pgCntRem > 0){
			bckCnt ++
			for(int intCt = 0; intCt < pgCntRem; intCt ++){
				DescriptionArray[arrCnt] = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt + 1, "row", columnNo, "col")
				arrCnt ++
			}
		}

		int backCnt = (pgCnt + bckCnt) - 1

		for(int bk = 0; bk < backCnt; bk ++){
			gObj.buttonClickSync(Const.columnPicker + "previousPage")
		}

		return DescriptionArray
	}



	@Keyword
	static def searchTableGetColumnValues_Alt(int columnNo){
		int pageViewCnt, tblNo
		boolean pageLengthVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "pageCount"), GVars.midWait, FailureHandling.OPTIONAL)
		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
			String tbleLgt = WebD.getElementText("//div[@class='col-xs-5 text-align-left padding-top-10']")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
		}else{
			tblNo = WebD.getTableRowCount("//div[@id='searchResults']//tbody")
			pageViewCnt = tblNo
		}

		int pgCnt = tblNo / pageViewCnt
		int pgCntRem = tblNo % pageViewCnt

		String[] DescriptionArray = new String[tblNo]
		int arrCnt = 0
		int bckCnt = 0
		for(int intPg = 0; intPg < pgCnt; intPg ++){
			for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
				DescriptionArray[arrCnt] = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")
				arrCnt ++
			}

			if(pgCntRem > 0){
				gObj.buttonClickSync(Const.columnPicker + "nextPage")
			}
		}

		if(pgCntRem > 0){
			bckCnt ++
			for(int intCt = 0; intCt < pgCntRem; intCt ++){
				DescriptionArray[arrCnt] = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt + 1, "row", columnNo, "col")
				arrCnt ++
			}
		}

		int backCnt = (pgCnt + bckCnt) - 1

		for(int bk = 0; bk < backCnt; bk ++){
			gObj.buttonClickSync(Const.columnPicker + "previousPage")
		}

		return DescriptionArray
	}
}
