import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Validate as gVal
import global.Object as gObj
import global.WebDriverMethods as WebD
	

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click on [Search] from the side bar ")
	Nav.selectSearchFilter("Action Types")


Rep.nextTestStep("Click the blue [Search] button to display the results")
	gObj.buttonClickSync(Const.searchFilters + 'a_Search')
	String SearchedItemValue = gObj.getAttributeSync(Const.searchFilters + 'input_SearchFilterName', 'value')	
	gAct.compareStringAndReport(SearchedItemValue, "Default Action Type Filter")
	
Rep.nextTestStep("Select a different item from the [Drop-down] list within filter section i.e Action View")	
	Nav.selectSearchFilter("Action Views")

	gObj.buttonClickSync(Const.searchFilters + 'a_Search')
	SearchedItemValue = gObj.getAttributeSync(Const.searchFilters + 'input_SearchFilterName', 'value')
	gAct.compareStringAndReport(SearchedItemValue, "Default Action View Filter")


