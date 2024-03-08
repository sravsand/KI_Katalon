package gantt

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

import internal.GlobalVariable
import global.Report as Rep

public class Object {

	@Keyword
	static def verifyCellNotPresentAndReport(String objectPath, int row, int col){
		boolean elementNotPresent = WebUI.verifyElementNotPresent(findTestObject(objectPath, [('row'): row, ('col'): col]), 5)
		if(elementNotPresent){
			Rep.pass("Object : " + objectPath + " not present as expected.")
		}else{
			Rep.fail("Object : " + objectPath + " present.")
		}
	}
}
