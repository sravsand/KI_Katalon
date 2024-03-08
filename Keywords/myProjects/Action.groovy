package myProjects

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
import global.Action as gAct
import global.Report as Rep
import global.Object as Obj
import global.WebDriverMethods as WebD
import global.Common as Com
import keyedInProjects.Constants as Const
import static groovy.io.FileType.*

public class Action {

	@Keyword
	static def getFileNameInFolder(String downloadPath){
		gAct.Wait(2)
		File f = new File(downloadPath);
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		if(names.size == 1){
			Rep.pass("File : " + names[0] + " has been downloaded.")
		}else{
			Rep.fail("File has not been downloaded.")
		}

		return names[0]
	}


	@Keyword
	static def getAllFilesNamesInFolder(String filePath){
		File f = new File(filePath);
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		return names
	}


	@Keyword
	static def getAllFileOfFileTypeInFolder(String filePath, String fileType){
		def startDir = new File(filePath)
		def list = [] as ArrayList

		startDir.eachFile(FILES) {
			if (it.name.endsWith('.' + fileType)) {
				list.add(it.name)
			}
		}

		return list
	}


	@Keyword
	static def resourceUpdateResultsAndValidate(int column, int columnIncrease){
		//check values pre update
		String xPath = Obj.getObjectRepositoryIdentifierValue(Const.resource + 'ResourceTable', 'xpath')
		int rows_count = WebD.getTableRowCount(xPath)
		int col_count = WebD.getTableColumnCount(xPath)

		def week = Com.weekDates()

		String weekStart = WebUI.getText(findTestObject(Const.resource + "tableHeader", [('col'): column]))

		if((weekStart.substring(0,1)) == "0"){
			weekStart = weekStart.substring(1, weekStart.length())
		}

		gAct.findSubstringInStringAndReport(week, weekStart)

		WebUI.click(findTestObject(Const.resource + "a_Update Results"))
		gAct.Wait(2)

		//validate table updated
		int col_countNew = WebD.getTableColumnCount(xPath)
		String weekStartNew = WebUI.getText(findTestObject(Const.resource + "tableHeader", [('col'): column]))

		def lastWeek = Com.weekDatesDifferentFormat(1, "back", "dd MMM")

		if(weekStartNew.substring(0,1) == "0"){
			lastWeek = "0" + lastWeek
		}


		gAct.findSubstringInStringAndReport(lastWeek, weekStartNew)

		gAct.compareIntAndReport(col_countNew, col_count + columnIncrease)
	}


	@Keyword
	static def selectContextReportAndValidate(String buttonName, String ExpTitle){
		Com.ieSync()
		WebUI.click(findTestObject(Const.context + 'button_ContextReports'))
		WebUI.click(findTestObject(Const.context + buttonName))
		gAct.Wait(2)
		Com.ieSync()
		WebUI.switchToWindowIndex(1)
		Com.ieSync(2)

		if(GVars.browser == "Internet explorer"){
			WebUI.focus(findTestObject(Const.context + 'ReportTitle'))
		}

		String repTitle = WebUI.getText(findTestObject(Const.context + 'ReportTitle'))

		gAct.compareStringAndReport(repTitle, ExpTitle)
		WebUI.closeWindowIndex(1)
		Com.edgeSync()
		Com.ieSync(2)
		WebUI.switchToDefaultContent()
	}


	@Keyword
	static def selectLogin(String loginName, int nameRow){
		String rowProfile
		boolean projectLocated = false
		String pagesCount = WebUI.getText(findTestObject(Const.logins + 'displayCount_Pagination'))
		String[] pages = pagesCount.split(" ")
		int itemCnt = pages[5].toInteger()

		int pageNo = pages[5].toInteger()/10
		int res = pages[5].toInteger()%10

		if(res > 0){
			pageNo = pageNo + 1
		}

		for(int intPageCnt = 1; intPageCnt <= pageNo; intPageCnt ++){
			int rowCnt = WebD.getTableRowCountByObject(Const.logins + 'loginsTable')
			for(int intRowCnt = 1; intRowCnt < rowCnt; intRowCnt ++){
				rowProfile = WebUI.getText(findTestObject(Const.logins + 'loginsTableCell', [('row'): intRowCnt, ('col'): nameRow]))

				if(GVars.browser == "MicrosoftEdge"){
					rowProfile = rowProfile.replace(" ", "")
				}

				if(rowProfile == loginName){
					WebUI.click(findTestObject(Const.logins + 'cell_InlineMenu', [('row'): intRowCnt]))
					Obj.selectInlineOption(1)
//					WebUI.click(findTestObject(Const.logins + 'a_Open', [('row'): intRowCnt]))
					projectLocated = true
					break
				}
			}
			if(projectLocated){
				break
			}

			WebUI.click(findTestObject(Const.logins + 'a_Next'))
			gAct.Wait(1)
		}

		if(!projectLocated){
			Rep.fail("Project " + loginName + " was not located.")
		}

	}
}
