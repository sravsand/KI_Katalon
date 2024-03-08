package risks

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
import models.Risk
import keyedInProjects.Constants as Const
import global.Action as gAct
import global.Validate as gVal
import global.Object as Obj
import global.WebDriverMethods as WebD
import global.Report as Rep
import search.Validate as sVal

public class Validate {

	@Keyword
	static def riskDetails(Risk risk){
		gAct.Wait(2)
		boolean riskVisible = false

		int rowNo = sVal.searchSpecificTableReturnRow(3, risk.title, "28ad15b0-4e62-48d0-9eea-db6963ba6900")
		//int rowNo = sVal.searchTableReturnRow(3, risk.title)
		WebUI.click(findTestObject(Const.risks + 'a_riskLink', [('row'): rowNo, ('col'): 3]))
		riskVisible = true


		//int rowCnt = WebD.getTableRowCountByObject(Const.risks + 'riskTable')

		//for(int intCnt = 1; intCnt < rowCnt; intCnt ++){
		//	String text = WebUI.getText(findTestObject(Const.risks + 'riskCell', [('row'): intCnt, ('col'): 3]))

		//	if(text == risk.title){
		//		WebUI.click(findTestObject(Const.risks + 'a_riskLink', [('row'): intCnt, ('col'): 3]))
		//		riskVisible = true
		//		break
		//	}
		//}

		if(riskVisible){
			//		WebUI.click(findTestObject(Const.risks + 'riskTitle', [('risk') : risk.title]))
			gAct.Wait(1)
			gVal.objectAttributeValue(Const.risks + 'input_Title', 'value', risk.title)
			gVal.objectAttributeValue(Const.risks + 'input_Project', 'value', risk.project)
			gVal.objectAttributeValue(Const.risks + 'input_Owner', 'value', risk.owner)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Status', risk.status, 'xpath', 1)
			gVal.objectAttributeValue(Const.risks + 'input_DateIdentified', 'value', risk.dateIdentified)
			gVal.objectAttributeValue(Const.risks + 'input_ImpactDate', 'value', risk.impactDate)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_PublishTo', risk.publishTo, "xpath", 1)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Probability', risk.probability, "xpath", 1)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Impact', risk.impact, "xpath", 1)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Severity', risk.severity, "xpath", 1)
			gVal.objectAttributeValue(Const.risks + 'input_ResolutionDate', 'value', risk.resolutionDate)

			if(GVars.browser != "MicrosoftEdge"){
				gVal.objectText(Const.risks + 'input_Description', risk.description)
			}

		}else{
			Rep.fail("Risk " + risk.title + " is not visible.")
		}
	}


	@Keyword
	static def searchRiskDetails(Risk risk, int columnNo){
		boolean riskVisible = false
		int pageViewCnt, tblNo
		boolean pageLengthVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "pageCount"), 5, FailureHandling.OPTIONAL)

		if(pageLengthVis){
			def pageView = WebUI.getAttribute(findTestObject(Const.columnPicker + "pageCount"), 'value')
			pageViewCnt = pageView.toInteger()
		}
		else{
			pageViewCnt = WebD.getTableRowCount("//div[@id='searchResults']") - 1
		}

		String actualValue
		int added = 0

		boolean pageCountVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "tableLength_pages"), 5, FailureHandling.OPTIONAL)
		if(pageCountVis){
			String tbleLgt = WebUI.getText(findTestObject(Const.columnPicker + "tableLength_pages"))
			String[] tblelgtSplit = tbleLgt.split()
			tblNo = tblelgtSplit[5].toInteger()
		}else{
			tblNo = pageViewCnt
		}

		int pgCnt = tblNo / pageViewCnt
		int pgCntRem = tblNo % pageViewCnt

		int bckCnt = 0
		for(int intPg = 0; intPg < pgCnt; intPg ++){
			for(int intCnt = 1; intCnt < pageViewCnt + 1; intCnt ++){
				actualValue = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): intCnt, ('col'): columnNo]))
				if(actualValue == risk.title){
					WebUI.click(findTestObject(Const.columnPicker + 'a_riskLink', [('row'): intCnt, ('col'): 3]))
					riskVisible = true
					added ++
					break
				}
			}
			if(added > 0){
				break
			}else{
				WebUI.click(findTestObject(Const.columnPicker + "nextPage"))
			}
		}

		if(pgCntRem > 0 && added == 0){
			bckCnt ++
			for(int intCt = 1; intCt < pgCntRem + 1; intCt ++){
				actualValue = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): intCt, ('col'): columnNo]))
				if(actualValue == risk.title){
					WebUI.click(findTestObject(Const.columnPicker + 'a_riskLink', [('row'): intCt, ('col'): 3]))
					riskVisible = true
					gAct.Wait(1)
					added ++
					break
				}
			}
		}

		if(riskVisible){
			//		WebUI.click(findTestObject(Const.risks + 'riskTitle', [('risk') : risk.title]))
			gAct.Wait(1)
			gVal.objectAttributeValue(Const.risks + 'input_Title', 'value', risk.title)
			gVal.objectAttributeValue(Const.risks + 'input_Project', 'value', risk.project)
			gVal.objectAttributeValue(Const.risks + 'input_Owner', 'value', risk.owner)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Status', risk.status, 'xpath', 1)
			gVal.objectAttributeValue(Const.risks + 'input_DateIdentified', 'value', risk.dateIdentified)
			gVal.objectAttributeValue(Const.risks + 'input_ImpactDate', 'value', risk.impactDate)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_PublishTo', risk.publishTo, "xpath", 1)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Probability', risk.probability, "xpath", 1)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Impact', risk.impact, "xpath", 1)
			Obj.verifySelectedComboBoxValueInFrame(Const.risks + 'select_Severity', risk.severity, "xpath", 1)
			gVal.objectAttributeValue(Const.risks + 'input_ResolutionDate', 'value', risk.resolutionDate)

			if(GVars.browser != "MicrosoftEdge"){
				gVal.objectText(Const.risks + 'input_Description', risk.description)
			}

		}else{
			Rep.fail("Risk " + risk.title + " is not visible.")
		}

	}
}
