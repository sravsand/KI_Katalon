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

public class Validate {

	@Keyword
	static def exportAgainstSearchTable(def exportFileData, String tableObject, String tableHeader, String tableRow){
		String xlCellVal, tableCellVal
		String xPath = gObj.getObjectRepositoryIdentifierValue(tableObject, 'xpath')
		int rows_count = WebD.getTableRowCount(xPath)
		int col_count = WebD.getTableColumnCount(xPath)

		//compare header
		for(int intCnt = 1; intCnt <= col_count - 1; intCnt ++){
			xlCellVal = exportFileData.getValue(intCnt, 1)
			tableCellVal = gObj.getEditTextDblSubIntPosSync(tableHeader, 1, "row", intCnt + 1, "col")

			gAct.compareStringAndReport(xlCellVal, tableCellVal)
		}


		//compare Rows
		for(int rowCnt = 1; rowCnt < rows_count; rowCnt ++){

			//export has a blank second column
			for(int intCnt = 1; intCnt <= col_count - 1; intCnt ++){
				xlCellVal = exportFileData.getValue(intCnt, rowCnt + 1)
				tableCellVal = gObj.getEditTextDblSubIntPosSync(tableRow, rowCnt, "row", intCnt + 2, "col")

				if(xlCellVal == ""){
					xlCellVal = " "
				}

				if((intCnt == 2)||(intCnt == 7)){
					xlCellVal = gCom.changeDateFormat(xlCellVal, "m/d/yy", "dd/mm/yyyy")
				}
				gAct.compareStringAndReport(xlCellVal, tableCellVal)
			}
		}
	}


	@Keyword
	static def reportAddFavourite(String reportName){
		String rowProfile
		boolean projectLocated = false
		String pagesCount = gObj.getEditTextSync(Const.searchReport + 'reportCount_Pagination')
		String[] pages = pagesCount.split(" ")
		int itemCnt = pages[5].toInteger()

		int pageNo = pages[5].toInteger()/10
		int res = pages[5].toInteger()%10

		if(res > 0){
			pageNo = pageNo + 1
		}

		for(int intPageCnt = 1; intPageCnt <= pageNo; intPageCnt ++){
			int rowCnt = WebD.getTableRowCountByObject(Const.searchReport + 'SearchReportTable')
			for(int intRowCnt = 1; intRowCnt < rowCnt; intRowCnt ++){
				rowProfile = gObj.getEditTextDblSubIntPosSync(Const.searchReport + 'searchReportsCell', intRowCnt, "row", 3, "col")
				if(rowProfile == reportName){
					gObj.buttonClickSubIntPosSync(Const.searchReport + 'searchReportInline', intRowCnt, "row")
					gObj.buttonClickSubIntPosSync(Const.searchReport + 'a_Favourite', intRowCnt, "row")
					projectLocated = true
					break
				}
			}
			if(projectLocated){
				break
			}

			gObj.buttonClickSync(Const.searchReport + 'a_Next')
			gAct.Wait(1)
		}

		if(!projectLocated){
			Rep.fail("Project " + reportName + " was not located.")
		}
	}


	@Keyword
	static def searchTableAddedValue(int columnNo, String expectedValue){
		boolean tbleExist = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "table_Default"), GVars.midWait, FailureHandling.OPTIONAL)
		int pageFirstViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
		int added = 0
		String actualValue
		String[] tblelgtSplit
		String currPge
		String tbleLgt

		if(pageFirstViewCnt != 1) {

			boolean pagin = gObj.elementPresentSync(Const.columnPicker + "nextPage")

			int pageViewCnt, tblNo
			boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
			if(pageLengthVis){
				def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
				pageViewCnt = pageView.toInteger()
			}
			else{
				pageViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
			}


			boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
			if(pageCountVis){
				tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
				tblelgtSplit = tbleLgt.split()
				tblNo = tblelgtSplit[5].toInteger()
				currPge = tblelgtSplit[1]
			}else{
				tblNo = pageViewCnt
				currPge = "1"
			}


			if(currPge != "1") {
				gObj.buttonClickSync(Const.columnPicker + "a_Search")
			}


			int pgCnt = tblNo / pageViewCnt
			int pgCntRem = tblNo % pageViewCnt

			int bckCnt = 0
			for(int intPg = 0; intPg < pgCnt; intPg ++){
				for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.pass("Item " + expectedValue + " has been added correctly.")
						added ++
						break
					}
				}
				if(added > 0){
					break
				}else{
					if(pgCntRem > 0 || pgCnt > 1){
						gObj.buttonClickSync(Const.columnPicker + "nextPage")
					}else{
						break
					}
				}
			}

			if(pgCntRem > 0 && added == 0){
				bckCnt ++
				for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.pass("Item " + expectedValue + " has been added correctly.")
						added ++
						break
					}
				}
			}

			int backCnt = (pgCnt + bckCnt) - 1

			for(int bk = 0; bk < backCnt; bk ++){
				gObj.buttonClickSync(Const.columnPicker + "previousPage")
			}
		}else {
			actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", 1, "row", columnNo, "col")
			if(actualValue == expectedValue){
				Rep.pass("Item " + expectedValue + " has been added correctly.")
				added ++
			}
		}

		if(added == 0){
			Rep.fail("Item " + expectedValue + " was not added.")
		}
	}


	@Keyword
	static def searchTableAddedValueSpecificTable(int columnNo, String expectedValue, String TableId){
		String currPge, tbleLgt
		boolean pagin = gObj.elementPresentSync(Const.columnPicker + "nextPage")

		int pageViewCnt, tblNo
		boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
		}
		else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='" + TableId + "']") - 1
		}

		String actualValue
		int added = 0

		boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
		if(pageCountVis){
			tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
			currPge = tblelgtSplit[1]
		}else{
			tblNo = pageViewCnt
			currPge = "1"
		}


		if(currPge != "1") {
			gObj.buttonClickSync(Const.columnPicker + "a_Search")
		}


		int pgCnt = tblNo / pageViewCnt
		int pgCntRem = tblNo % pageViewCnt

		int bckCnt = 0
		for(int intPg = 0; intPg < pgCnt; intPg ++){
			for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
				actualValue = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", TableId, "tab", intCnt, "row", columnNo, "col")
				if(actualValue == expectedValue){
					Rep.pass("Item " + expectedValue + " has been added correctly.")
					added ++
					break
				}
			}
			if(added > 0){
				break
			}else{
				if(pgCntRem > 0 || pgCnt > 1){
					gObj.buttonClickSync(Const.columnPicker + "nextPage")
				}else{
					break
				}
			}
		}

		if(pgCntRem > 0 && added == 0){
			bckCnt ++
			for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
				actualValue = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", TableId, "tab", intCt, "row", columnNo, "col")
				if(actualValue == expectedValue){
					Rep.pass("Item " + expectedValue + " has been added correctly.")
					added ++
					break
				}
			}
		}

		int backCnt = (pgCnt + bckCnt) - 1

		for(int bk = 0; bk < backCnt; bk ++){
			gObj.buttonClickSync(Const.columnPicker + "previousPage")
		}

		if(added == 0){
			Rep.fail("Item " + expectedValue + " was not added.")
		}
	}


	@Keyword
	static def searchTableReturnRow(int columnNo, String expectedValue){
		boolean tbleExist = gObj.elementPresentSync(Const.columnPicker + "table_Default")
		int pageFirstViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
		int pageViewCnt, tblNo
		int rowNo
		String actualValue
		int added = 0
		String currPge, tbleLgt

		if(pageFirstViewCnt != 1) {
			boolean pagin = gObj.elementPresentSync(Const.columnPicker + "nextPage")

			boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
			if(pageLengthVis){
				def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
				pageViewCnt = pageView.toInteger()
			}else{
				pageViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
			}


			boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
			if(pageCountVis){
				tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
				String[] tblelgtSplit = tbleLgt.split()
				tblNo = tblelgtSplit[5].toInteger()
				currPge = tblelgtSplit[1]
			}else{
				tblNo = pageViewCnt
				currPge = "1"
			}

			if(currPge != "1") {
				gObj.buttonClickSync(Const.columnPicker + "a_Search")
			}

			int pgCnt = tblNo / pageViewCnt
			int pgCntRem = tblNo % pageViewCnt

			int bckCnt = 0
			for(int intPg = 0; intPg < pgCnt; intPg ++){
				for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")

					if(GVars.browser == "Firefox") {
						actualValue = actualValue.replace("", "")
					}

					if(actualValue == expectedValue){
						rowNo = intCnt
						added ++
						break
					}
				}
				if(added > 0){
					break
				}else{
					if(pgCntRem > 0 || pgCnt > 1){
						gObj.buttonClickSync(Const.columnPicker + "nextPage")
					}else{
						break
					}
				}
			}

			if(pgCntRem > 0 && added == 0){
				bckCnt ++
				for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						rowNo = intCt
						added ++
						break
					}
				}
			}


		}else {
			actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", 1, "row", columnNo, "col")
			if(actualValue == expectedValue){
				rowNo = 1
				added ++
			}

			if(added == 0){
				Rep.fail("Item " + expectedValue + " was not found.")
			}
		}

		return rowNo
	}


	@Keyword
	static def searchTableReturnRow_OpenModal(int columnNo, String expectedValue){
		int pageViewCnt, tblNo
		String currPge, tbleLgt
		boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
		}else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
		}

		int rowNo
		String actualValue
		int added = 0

		boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
		if(pageCountVis){
			tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
			currPge = tblelgtSplit[1]
		}else{
			tblNo = pageViewCnt
			currPge = "1"
		}

		if(currPge != "1") {
			gObj.buttonClickSync(Const.columnPicker + "a_Search")
		}


		int pgCnt = tblNo / pageViewCnt
		int pgCntRem = tblNo % pageViewCnt

		int bckCnt = 0
		for(int intPg = 0; intPg < pgCnt; intPg ++){
			for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
				actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")
				if(actualValue == expectedValue){
					rowNo = intCnt
					added ++
					break
				}
			}
			if(added > 0){
				break
			}else{
				if(pgCntRem > 0 || pgCnt > 1){
					WebD.clickUsingJavaScript("//a[@href = '#' and @onclick = 'javascript: filter_search(this); return false;' and (text() = '»' or . = '»')]")
				}else{
					break
				}
			}
		}

		if(pgCntRem > 0 && added == 0){
			bckCnt ++
			for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
				actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt, "row", columnNo, "col")
				if(actualValue == expectedValue){
					rowNo = intCt
					added ++
					break
				}
			}
		}

		if(added == 0){
			Rep.fail("Item " + expectedValue + " was not found.")
		}

		return rowNo
	}


	@Keyword
	static def searchSpecificTableReturnRow(int columnNo, String expectedValue, String TableId){
		boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
		if(pageLengthVis){
			String textVal = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			if(textVal != "10"){
				gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
				gAct.Wait(2)
			}
		}
		int pageViewCnt, tblNo
		String currPge, tbleLgt

		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
		}else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='" + TableId + "']") - 1
		}

		int rowNo
		String actualValue
		int added = 0

		boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
		if(pageCountVis){
			tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
			currPge = tblelgtSplit[1]
		}else{
			tblNo = pageViewCnt
			currPge = "1"
		}

		if(currPge != "1") {
			gObj.buttonClickSync(Const.columnPicker + "a_Search")
		}

		int pgCnt = tblNo / pageViewCnt
		int pgCntRem = tblNo % pageViewCnt

		int bckCnt = 0
		for(int intPg = 0; intPg < pgCnt; intPg ++){
			for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
				actualValue = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", TableId, "tab", intCnt, "row", columnNo, "col")
				if(actualValue == expectedValue){
					rowNo = intCnt
					added ++
					break
				}
			}
			if(added > 0){
				break
			}else{
				if(pgCntRem > 0 || pgCnt > 1){
					gObj.buttonClickSubSync(Const.columnPicker + "nextPage_specificTbl", TableId, "table")
				}else{
					break
				}
			}
		}

		if(pgCntRem > 0 && added == 0){
			bckCnt ++
			for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
				actualValue =  gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", TableId, "tab", intCt, "row", columnNo, "col")
				if(actualValue == expectedValue){
					rowNo = intCt
					added ++
					break
				}
			}
		}

		if(added == 0){
			Rep.fail("Item " + expectedValue + " was not found.")
		}

		return rowNo
	}


	@Keyword
	static def searchTableDeletedValue(int columnNo, String expectedValue){
		int pageViewCnt, tblNo
		String currPge, tbleLgt
		boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
		}else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
		}

		String actualValue
		int added = 0
		boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
		if(pageCountVis){
			tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
			currPge = tblelgtSplit[1]
		}else{
			tblNo = pageViewCnt
			currPge = "1"
		}

		if(currPge != "1") {
			gObj.buttonClickSync(Const.columnPicker + "a_Search")
		}

		boolean tablePresent = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "searchTable", [('row'): 1, ('col'): 1]), 2, FailureHandling.OPTIONAL)

		if(tablePresent){
			int pgCnt = tblNo / pageViewCnt
			int pgCntRem = tblNo % pageViewCnt

			int bckCnt = 0
			for(int intPg = 0; intPg < pgCnt; intPg ++){
				for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.fail("Item " + expectedValue + " has not been deleted.")
						added ++
						break
					}
				}
				if(added > 0){
					break
				}else{
					if(pgCntRem > 0 || pgCnt > 1){
						gObj.buttonClickSync(Const.columnPicker + "nextPage")
					}else{
						break
					}
				}
			}

			if(pgCntRem > 0 && added == 0){
				bckCnt ++
				for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.fail("Item " + expectedValue + " has not been deleted.")
						added ++
						break
					}
				}
			}

			int backCnt = (pgCnt + bckCnt) - 1

			for(int bk = 0; bk < backCnt; bk ++){
				gObj.buttonClickSync(Const.columnPicker + "previousPage")
			}
		}

		if(added == 0){
			Rep.pass("Item " + expectedValue + " was deleted.")
		}
		gAct.Wait(2)
	}


	@Keyword
	static def searchTableDeletedValueSpecificTable(int columnNo, String expectedValue, String TableId){
		int pageViewCnt, tblNo
		String currPge, tbleLgt
		boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
		}else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='" + TableId + "']") - 1
		}

		String actualValue
		int added = 0
		boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
		if(pageCountVis){
			tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
			currPge = tblelgtSplit[1]
		}else{
			tblNo = pageViewCnt
			currPge = "1"
		}

		if(currPge != "1") {
			gObj.buttonClickSync(Const.columnPicker + "a_Search")
		}

		boolean tablePresent = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "searchSpecifiedTable", [('tab'): TableId, ('row'): 1, ('col'): 1]), 2, FailureHandling.OPTIONAL)

		if(tablePresent){
			int pgCnt = tblNo / pageViewCnt
			int pgCntRem = tblNo % pageViewCnt

			int bckCnt = 0
			for(int intPg = 0; intPg < pgCnt; intPg ++){
				for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
					actualValue = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", TableId, "tab", intCnt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.fail("Item " + expectedValue + " has not been deleted.")
						added ++
						break
					}
				}
				if(added > 0){
					break
				}else{
					if(pgCntRem > 0 || pgCnt > 1){
						gObj.buttonClickSubSync(Const.columnPicker + "nextPage_specificTbl", TableId, "table")
					}else{
						break
					}
				}
			}

			if(pgCntRem > 0 && added == 0){
				bckCnt ++
				for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){

					actualValue = gObj.getEditTextTriSubIntPosSync(Const.columnPicker + "searchSpecifiedTable", TableId, "tab", intCt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.fail("Item " + expectedValue + " has not been deleted.")
						added ++
						break
					}
				}
			}

			int backCnt = (pgCnt + bckCnt) - 1

			for(int bk = 0; bk < backCnt; bk ++){
				gObj.buttonClickSubSync(Const.columnPicker + "previousPage_specificTbl", TableId, "table")
			}
		}

		if(added == 0){
			Rep.pass("Item " + expectedValue + " was deleted.")
		}
		gAct.Wait(2)
	}


	@Keyword
	static def searchTableWithFooterDeletedValue(int columnNo, String expectedValue){
		int pageViewCnt, tblNo
		String currPge, tbleLgt
		boolean pageLengthVis = gObj.elementPresentSync(Const.columnPicker + "pageCount")
		if(pageLengthVis){
			def pageView = gObj.getAttributeSync(Const.columnPicker + "pageCount", 'value')
			pageViewCnt = pageView.toInteger()
		}else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
		}

		String actualValue
		int added = 0
		boolean pageCountVis = gObj.elementPresentSync(Const.columnPicker + "tableLength_pages")
		if(pageCountVis){
			tbleLgt = gObj.getEditTextSync(Const.columnPicker + "tableLength_pages")
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
			currPge = tblelgtSplit[1]
		}else{
			tblNo = pageViewCnt
			currPge = "1"
		}

		if(currPge != "1") {
			gObj.buttonClickSync(Const.columnPicker + "a_Search")
		}

		boolean tablePresent = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "searchTable", [('row'): 1, ('col'): 1]), 5, FailureHandling.OPTIONAL)

		if(tablePresent){
			int pgCnt = tblNo / pageViewCnt
			int pgCntRem = tblNo % pageViewCnt

			int bckCnt = 0
			for(int intPg = 0; intPg < pgCnt; intPg ++){
				for(int intCnt = 1; intCnt < pageViewCnt; intCnt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCnt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.fail("Item " + expectedValue + " has not been deleted.")
						added ++
						break
					}
				}
				if(added > 0){
					break
				}else{
					if(pgCntRem > 0 || pgCnt > 1){
						gObj.buttonClickSync(Const.columnPicker + "nextPage")
					}else{
						break
					}
				}
			}

			if(pgCntRem > 0 && added == 0){
				bckCnt ++
				for(int intCt = 1; intCt < pgCntRem; intCt ++){
					actualValue = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", intCt, "row", columnNo, "col")
					if(actualValue == expectedValue){
						Rep.fail("Item " + expectedValue + " has not been deleted.")
						added ++
						break
					}
				}
			}

			int backCnt = (pgCnt + bckCnt) - 1

			for(int bk = 0; bk < backCnt; bk ++){
				gObj.buttonClickSync(Const.columnPicker + "previousPage")
			}
		}

		if(added == 0){
			Rep.pass("Item " + expectedValue + " was deleted.")
		}
		gAct.Wait(2)
	}
}
