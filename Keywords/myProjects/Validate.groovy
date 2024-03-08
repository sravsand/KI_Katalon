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
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testdata.reader.ExcelFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By

import global.WebDriverMethods as WebD
import global.Object as gObj
import global.Action as gAct
import global.Common as Com
import internal.GlobalVariable
import keyedInProjects.Constants as Const

public class Validate {

	@Keyword
	static def exportAgainstTable(def exportFileData, String tableObject, String tableHeader, String tableRow){
		String xlCellVal, tableCellVal
		String xPath = gObj.getObjectRepositoryIdentifierValue(tableObject, 'xpath')
		int rows_count = WebD.getTableRowCount(xPath)
		int col_count = WebD.getTableColumnCount(xPath)

		//compare header
		xlCellVal = exportFileData.getValue(1, 1)
		tableCellVal = WebUI.getText(findTestObject(tableHeader, [('row'): 1, ('col'): 1]))
		gAct.compareStringAndReport(xlCellVal, tableCellVal)

		//export has a blank second column
		for(int intCnt = 2; intCnt <= col_count; intCnt ++){
			xlCellVal = exportFileData.getValue(intCnt + 1, 1)
			tableCellVal = WebUI.getText(findTestObject(tableHeader, [('row'): 1, ('col'): intCnt]))

			gAct.compareStringAndReport(xlCellVal, tableCellVal)
		}


		//compare Rows rowCnt changed from 1 to 2
		for(int rowCnt = 1; rowCnt < rows_count; rowCnt ++){

			if(rowCnt == 1){
				xlCellVal = exportFileData.getValue(1, rowCnt + 1)
			}else{
				xlCellVal = exportFileData.getValue(2, rowCnt)
			}

			tableCellVal = WebUI.getText(findTestObject(tableRow, [('row'): rowCnt, ('col'): 1]))
			gAct.compareStringAndReport(xlCellVal, tableCellVal)

			if(rowCnt != 1){
				//export has a blank second column
				for(int intCnt = 2; intCnt <= col_count; intCnt ++){
					xlCellVal = exportFileData.getValue(intCnt + 1, rowCnt)// + 1)
					tableCellVal = WebUI.getText(findTestObject(tableRow, [('row'): rowCnt, ('col'): intCnt]))

					gAct.compareStringAndReport(xlCellVal, tableCellVal)
				}
			}
		}
	}


	@Keyword
	static def exportAgainstTable_Resource(def exportFileData, String tableObject, String tableHeader, String tableRow){
		String xlCellVal, tableCellVal
		String xPath = gObj.getObjectRepositoryIdentifierValue(tableObject, 'xpath')
		int rows_count = WebD.getTableRowCount(xPath)
		int col_count = WebD.getTableColumnCount(xPath)

		//compare header
		for(int intCnt = 1; intCnt <= col_count; intCnt ++){
			xlCellVal = exportFileData.getValue(intCnt, 1)
			if(xlCellVal == ""){
				xlCellVal = " "
			}
			tableCellVal = WebUI.getText(findTestObject(tableHeader, [('row'): 1, ('col'): intCnt]))

			gAct.compareStringAndReport(xlCellVal, tableCellVal)
		}

		int rowSplitCnt = 0
		int rowLoop = 1
		int exportLoop = 0
		int extraHiddenRowCnt = 0
		int hiddenRow = 0

		//compare Rows - LH side of table
		for(int rowCnt = 1; rowCnt < rows_count - 3 ; rowCnt ++){
			//		for(int rowCnt = 1; rowCnt < rows_count - 2 ; rowCnt ++){
			if(rowSplitCnt == 4){
				exportLoop ++
				rowSplitCnt = 0

				//if a resource is present there are 4 hidden rows on screen and 2 or 3 on export, these need adding to the count when getting cell value
				if(hiddenRow == 1){
					extraHiddenRowCnt = extraHiddenRowCnt + 3
					exportLoop = exportLoop - 2
					hiddenRow = 0
				}
			}
			if(rowCnt == rowLoop){
				for(int intCnt = 1; intCnt <= 3; intCnt ++){
					xlCellVal = exportFileData.getValue(intCnt, rowCnt + 1)
					tableCellVal = WebUI.getText(findTestObject(tableRow, [('row'): rowLoop + extraHiddenRowCnt, ('col'): intCnt]))

					if(intCnt == 3){
						if(tableCellVal != ""){
							hiddenRow ++
						}
					}

					gAct.compareStringAndReport(xlCellVal, tableCellVal)
				}
				rowLoop = rowLoop + 4
				//				rowLoop = rowLoop + 5
			}else{

				for(int intCnt = 4; intCnt <= col_count; intCnt ++){
					xlCellVal = exportFileData.getValue(intCnt, rowCnt - exportLoop)

					if(xlCellVal == "0"){
						xlCellVal = "0.00"
					}

					tableCellVal = WebUI.getText(findTestObject(tableRow, [('row'): rowCnt + extraHiddenRowCnt, ('col'): intCnt - 3]))

					gAct.compareStringAndReport(xlCellVal, tableCellVal)
				}
			}
			rowSplitCnt ++
		}
	}


	@Keyword
	static def projectEditWindowTabNavigation(){
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'lifecycleTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'planningTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'ChargesTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'restrictionsTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'interestedResourcesTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'attachmentsTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'informationTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'investmentInfoTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.projectsMenu + 'generalTab', "class", "tabSelectedPopup")
	}


	@Keyword
	static def getTabStatus(int position){
		def childNodes = WebD.listObjectChildObjects("//div[@class='editpage popup tabbable']//ul[@class='nav nav-tabs tabs-flat']")
		def checkActive = childNodes[position].getAttribute("className")
		return checkActive
	}


	@Keyword
	static def tabActive(String tab, int position){
		def checkActive = getTabStatus(position)
		gAct.compareStringAndReport(checkActive, "")
		WebUI.click(findTestObject(Const.projectsMenu + tab))
		checkActive = getTabStatus(position)
		gAct.compareStringAndReport(checkActive, "active")
	}


	@Keyword
	static def projectEditWindowTabNavigation_WorkflowsOn(){
		def checkActive = getTabStatus(0)
		gAct.compareStringAndReport(checkActive, "active")

		tabActive('a_Information', 1)
		tabActive('a_InvestmentInformation', 2)
		tabActive('a_Attachments', 3)
		tabActive('a_Collaboration', 4)
		tabActive('a_Audit', 5)
	}


	@Keyword
	static def billingAmendWindowTabNavigation(){
		gObj.clickAndValidateAttribute(Const.billingContract + 'DefaultsTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingContract + 'ContractLinesTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingContract + 'BillingRulesTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingContract + 'AccountInformationTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingContract + 'SupportingDocumentsTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingContract + 'AuditTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingContract + 'GeneralTab', "class", "tabSelectedPopup")
	}


	@Keyword
	static def billingAmendInvoiceWindowTabNavigation(){
		gObj.clickAndValidateAttribute(Const.billingInvoice + 'InvoiceLinesTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingInvoice + 'AccountInformationTab', "class", "tabSelectedPopup")
		gObj.clickAndValidateAttribute(Const.billingInvoice + 'GeneralTab', "class", "tabSelectedPopup")
	}


	@Keyword
	static def edgeCellValue(String objectName, String expValue){
		String actValue = WebUI.getText(findTestObject(objectName))
		actValue = actValue.replaceAll("\n", "")
		String text = actValue[-1..-1]

		if(text == " "){
			actValue = Com.removeLastChar(actValue)
		}

		gAct.compareStringAndReport(actValue, expValue)
	}
}
