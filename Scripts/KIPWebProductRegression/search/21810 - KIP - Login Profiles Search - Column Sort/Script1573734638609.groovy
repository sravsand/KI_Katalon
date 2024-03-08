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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
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
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Action as sAct

//change from default login
GVars.configFileName = 	"katalonConfigAuto20.xml"
Act.changeUserCredentials()

String searchItem = "Login Profiles"
String searchVal = "Login Profile"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Login Profiles] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gAct.Wait(1)
	
	boolean pageLengthVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "pageCount"), GVars.midWait, FailureHandling.OPTIONAL)
	
	if(pageLengthVis){
		gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	}

	gObj.buttonClickSync(Const.columnPicker + "header_Code")
	
	
Rep.nextTestStep("Click on [Name] from menu bar")
	WebD.clickElement("//th[@class='sorting'][contains(text(),'Name')]")
	def preSort = sAct.searchTableGetColumnValues_Alt(4)
	WebD.clickElement("//th[@class='sorting_asc']")

	def postSort = sAct.searchTableGetColumnValues_Alt(4)
	boolean match = gVal.matchingReverseArrays(preSort, postSort)
	
	if(match){
		Rep.pass("Name has been sorted correctly.")
	}else{
		Rep.fail("Name has not been sorted correcly.")
	}
	
	
Rep.nextTestStep("Click on [Code] from menu bar")
	gObj.buttonClickSync(Const.columnPicker + "header_Code")
	def preSortCode = sAct.searchTableGetColumnValues_Alt(2)
	gObj.buttonClickSync(Const.columnPicker + "header_Code")
	def postSortCode = sAct.searchTableGetColumnValues_Alt(2)
	boolean matchCode = gVal.matchingReverseArrays(preSortCode, postSortCode)
	
	if(matchCode){
		Rep.pass("Code has been sorted correctly.")
	}else{
		Rep.fail("Code has not been sorted correcly.")
	}

