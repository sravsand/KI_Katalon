package global

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
import global.Action as Act
import global.Common as Com
import global.Report as Rep
import global.Object as Obj

public class Validate {

	@Keyword
	static def objectAttributeValue(String objectName, String attrValue, String expValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.shortWait)
		String actValue = Obj.getAttributeSync(objectName, attrValue)
		Act.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def objectText(String objectName, String expValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.midWait)
		String actValue = Obj.getEditTextSync(objectName)
		Act.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def objectTextEdge(String objectName, String expValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.shortWait)
		String actValue = Obj.getEditTextSync(objectName)
		actValue = actValue.replaceAll(" \n", "\n")
		String text = actValue[-1..-1]

		if(text == " "){
			actValue = Com.removeLastChar(actValue)
		}
		Act.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def objectTextwithParameter(String objectName, String paramName, int paramVal, String expValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.shortWait)
		String actValue = WebUI.getText(findTestObject(objectName, [(paramName) : paramVal]))
		Act.compareStringAndReport(actValue, expValue)
	}

	@Keyword
	static def objectTextwithTwoParameters(String objectName, String paramName, int paramVal, String paramName2, int paramVal2, String expValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.shortWait)
		String actValue = WebUI.getText(findTestObject(objectName, [(paramName) : paramVal, (paramName2) : paramVal2]))
		Act.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def objectContainsText(String objectName, String expValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.shortWait)
		String actValue = WebUI.getText(findTestObject(objectName))
		Act.findSubstringInStringAndReport(actValue, expValue)
	}


	@Keyword
	static boolean matchingReverseArrays(def arrToReverse, def arrToCompare){
		def reversed = arrToReverse.reverse()
		int len = reversed.size()
		int counter = 0
		for(int intCnt = 0; intCnt < len; intCnt ++){
			if(reversed[intCnt] != arrToCompare[intCnt]){
				counter ++
			}
			Rep.info(reversed[intCnt] + " : " + arrToCompare[intCnt])
		}

		if(counter == 0){
			return true
		}else{
			return false
		}
	}
}
