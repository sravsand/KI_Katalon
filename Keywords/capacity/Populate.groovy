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

import internal.GlobalVariable
import global.WebDriverMethods as WebD
import global.Action as gAct

public class Populate {

	@Keyword
	static def capacityReportCheckboxArray(){
		WebD.checkOrUncheckBox('#ShowProductiveOnly', false)
		WebD.checkOrUncheckBox('#ShowEmployees', true)
		WebD.checkOrUncheckBox('#ShowContractors', true)
		WebD.checkOrUncheckBox('#IncludeCapacity', true)
		WebD.checkOrUncheckBox('#IncludeDemand', true)
		gAct.Wait(1)
		WebD.checkOrUncheckBox('#IncludeDemandProvisional', true)
		WebD.checkOrUncheckBox('#IncludeDemandConfirmed', true)
		WebD.checkOrUncheckBox('#IncludeSupply', true)
		WebD.checkOrUncheckBox('#IncludeActuals', true)
	}
}
